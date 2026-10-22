# Web crawler high-level design

Archived chat transcript. Messages are preserved as they appeared in the session.

Session: `fde2e56b-d19c-4c2b-a002-06dc359b5a3e`

## User

Assume you are making as webcrawler, I want to know the high level design before you jump into complex things , make realiable and fail safe system. even if doesn;t do tons of things, , exaplin the write and query pattern, assum I give you URl and you need to read the page and extract the links and then open those links and read those page and so on, you need to do this till depth D which would be input , walk me through the basic system, then we would scale, may be just MVp

## Assistant

# Web Crawler MVP: High-Level Design

## Requirements (MVP)
- Input: seed URL + max depth D
- Fetch page → extract links → recursively fetch to depth D
- Reliable: survive crashes and restarts, no lost work, no infinite loops, polite to hosts
- Non-goals for now: JS rendering, distributed scale, ranking

## Core Components

```
 Client ──► API ──► [Job Store]
                       │
            ┌──────────▼──────────┐
            │   Frontier (Queue)  │◄─────────────┐
            └──────────┬──────────┘              │
                       ▼                         │
                  Fetch Workers ──► Parser ──► Dedup check
                       │                         │ (new URLs, depth+1 ≤ D)
                       ▼                         │
                 [Page Store]  ───────────────────┘
```

1. **API**: `POST /crawls {seed, maxDepth}` → `crawlId`. `GET /crawls/{id}` → status/progress.
2. **Frontier (queue)**: holds `(crawlId, url, depth)` tasks. At-least-once delivery with visibility timeout/lease.
3. **Fetch worker**: stateless. Takes a task, fetches (timeout, size cap, content-type check, robots.txt), stores result.
4. **Parser**: extracts `<a href>`, resolves relative URLs, normalizes (strip fragment, lowercase host, drop tracking params, sort query).
5. **Dedup ("seen" set)**: ensures each URL is enqueued once per crawl.
6. **Stores**: metadata DB + blob storage for raw content.

## Data Model (MVP: one SQL DB, e.g. Postgres, plus object store)

**crawl_job**: `crawl_id, seed, max_depth, status, created_at`

**url_task** (the frontier and the seen set in one table):
- `crawl_id, url_hash, url, depth, status (PENDING/IN_PROGRESS/DONE/FAILED), attempts, lease_expires_at, last_error`
- **Primary key `(crawl_id, url_hash)`**, which gives dedup for free.

**page**: `url_hash, fetched_at, http_status, content_hash, blob_path, title`

**link** (optional edge table): `from_hash, to_hash`

## Write Pattern
For each fetched page, in one transaction:
1. Upload the body to blob storage first (idempotent key = `content_hash`).
2. Insert the `page` row and mark the task `DONE`.
3. For each outlink where `depth+1 ≤ D`:
   `INSERT INTO url_task ... ON CONFLICT DO NOTHING`.

Writes are bursty: one page fans out to roughly 50–100 inserts. Batch them. The task is marked DONE in the same transaction as the child inserts, so a crash cannot leave a page half-processed.

## Query / Read Pattern
- **Claim work** (hot path):
  ```sql
  UPDATE url_task SET status='IN_PROGRESS', lease_expires_at=now()+60s
  WHERE (crawl_id,url_hash) IN (
    SELECT ... WHERE status='PENDING' OR (status='IN_PROGRESS' AND lease_expires_at<now())
    ORDER BY depth LIMIT N FOR UPDATE SKIP LOCKED) RETURNING *;
  ```
- **Seen check**: point lookup by PK, covered by the insert conflict.
- **Progress**: `COUNT(*) GROUP BY status` per crawl. The job is complete when no PENDING or IN_PROGRESS tasks remain.
- **Read page**: lookup by `url_hash` and fetch the blob.

## Reliability and Fail-Safety
| Failure | Handling |
|---|---|
| Worker crash mid-fetch | Lease expires, another worker re-claims the task. |
| Duplicate processing | Idempotent writes (PK conflict, content-hash blob key). |
| Transient fetch error (5xx, timeout) | Retry with exponential backoff, max attempts, then `FAILED`. The crawl still finishes. |
| Permanent error (404, bad content-type) | Mark `DONE` or `FAILED` with no retry. |
| Cycles and duplicates | Normalization plus the PK, and the depth limit guarantees termination. |
| Slow or huge pages | Timeout, max body size, and redirect limit. |
| Spider traps | Cap URLs per host/crawl and cap URL length. |
| Hammering a site | Per-host rate limit (e.g. 1 req/s), robots.txt, and a User-Agent. |
| DB or system restart | All state is in the DB, so workers simply resume. |

## Termination
The depth cap gives a finite bound. Seed is depth 0, and links found at depth D are *not* enqueued. Optionally add `max_pages` as a safety valve.

## MVP Scope
Single process or a few workers, one Postgres, local disk or S3 for blobs, HTML only, same-domain option. That is about 300 lines of code.

## Scaling Path (next phase)
1. **Frontier**: move to Kafka/SQS partitioned by host, so politeness is natural: one consumer per host partition.
2. **Seen set**: Redis or a Bloom filter in front of the DB, and shard the DB by `crawl_id` or `url_hash`.
3. **DNS cache**, connection pooling, and a **dead-letter queue**.
4. **Re-crawl** scheduling with ETag/If-Modified-Since.
5. **Metrics and alerts**: queue depth, error rate, per-host latency.
6. **Headless rendering** workers for JS-heavy pages.

---
Want me to build the MVP (e.g. Python + SQLite/Postgres), or dive deeper into any piece first, such as politeness or the frontier?

## User

No, walk me through one seed example, take a small exmaple.

I want to understand all the transation and atomic operation which need to performed , reason why we are choosing SQL data base

## Assistant

# Walkthrough: One Seed, Depth D = 2

## The tiny web
```
A (seed)  → links to B, C
B         → links to A, D
C         → links to D, E
D         → links to F
```
Request: `POST /crawls {seed: A, maxDepth: 2}` → `crawl_id = 1`

Rule: a page at depth `d` may enqueue children only if `d+1 ≤ D`.

---

## Step 0: Create the job (Transaction T0)
One transaction, two writes:
```sql
BEGIN;
INSERT INTO crawl_job(crawl_id, seed, max_depth, status) VALUES (1,'A',2,'RUNNING');
INSERT INTO url_task(crawl_id,url_hash,url,depth,status) VALUES (1,h(A),'A',0,'PENDING');
COMMIT;
```
**Why atomic:** if only the job were written, nothing would ever run. If only the task were written, there'd be no job to report on. Both or neither.

State:
| url | depth | status |
|---|---|---|
| A | 0 | PENDING |

---

## Step 1: Claim A (Transaction T1, atomic claim)
```sql
UPDATE url_task SET status='IN_PROGRESS', lease_expires_at=now()+60s, attempts=attempts+1
WHERE (crawl_id,url_hash) IN (SELECT ... WHERE status='PENDING' ... FOR UPDATE SKIP LOCKED LIMIT 1)
RETURNING *;
```
**Why atomic:** select and update happen as one step. Two workers can't both get A. `SKIP LOCKED` makes the second worker skip A and take another row.

The network fetch happens **outside** any DB transaction. Never hold a DB transaction open during slow I/O.

## Step 2: Fetch and parse A (no DB)
Worker fetches A, gets links `[B, C]`, and normalizes them. It uploads the body to blob storage under `content_hash` (idempotent: re-uploading the same bytes is harmless).

## Step 3: Commit A's result (Transaction T2, the key one)
```sql
BEGIN;
INSERT INTO page(...) VALUES (A, 200, content_hash, blob_path) ON CONFLICT DO NOTHING;
INSERT INTO url_task(crawl_id,url_hash,url,depth,status)
  VALUES (1,h(B),'B',1,'PENDING'), (1,h(C),'C',1,'PENDING')
  ON CONFLICT (crawl_id,url_hash) DO NOTHING;
UPDATE url_task SET status='DONE' WHERE crawl_id=1 AND url_hash=h(A)
  AND status='IN_PROGRESS' AND lease_expires_at > now();   -- fencing check
COMMIT;
```
**Why atomic:** this is the heart of the design.
- A crash before COMMIT: nothing is visible. A stays IN_PROGRESS, the lease expires, and another worker redoes it. No children were half-added.
- A crash after COMMIT: A is DONE and B, C exist. Nothing is lost.
- Without atomicity you could mark A DONE and crash before inserting B and C, so the whole subtree would be **silently lost forever**.

State:
| url | depth | status |
|---|---|---|
| A | 0 | DONE |
| B | 1 | PENDING |
| C | 1 | PENDING |

## Step 4: B and C processed (possibly in parallel by two workers)
- **B** finds `[A, D]`:
  - A: `ON CONFLICT DO NOTHING` skips it. **This is cycle prevention.**
  - D: inserted at depth 2.
- **C** finds `[D, E]`:
  - D: already inserted by B, so it's skipped. **This is dedup, and the DB resolves the race.** If B and C commit at the same instant, the PK uniqueness guarantees exactly one insert wins, and the other waits and then no-ops.
  - E: inserted at depth 2.

Which worker inserted D does not matter, since depth via BFS is the same or shorter. (Depth ordering in the claim query makes the first discovery usually the shortest path.)

## Step 5: D and E at depth 2 = D (the limit)
Workers fetch D and E. D's link to F would be depth 3 > 2, so **F is not enqueued**. D and E are marked DONE with no child inserts. Still one transaction.

## Step 6: Completion
```sql
SELECT count(*) FROM url_task WHERE crawl_id=1 AND status IN ('PENDING','IN_PROGRESS');
```
When this is 0, a finisher updates `crawl_job.status='COMPLETE'`. Because children are inserted in the same transaction that marks the parent DONE, there is **no instant when the count is 0 but work is still coming**. That invariant makes the check safe. Final: A, B, C, D, E DONE; F never visited.

---

## Failure scenarios
| Event | Result |
|---|---|
| Worker dies after claiming B | Lease expires and B is re-claimed. Attempts increments. |
| Worker is slow, not dead, and the lease expires while it's still running | A second worker also processes B. The first worker's T2 fails the **fencing check** (`lease_expires_at > now()` plus a status check), or its inserts no-op by PK. The result is the same either way: correct. |
| Fetch returns 503 | `UPDATE status='PENDING', next_attempt_at=now()+backoff` (no children). After max attempts, `FAILED`. A failed task is terminal, so the crawl still completes. |
| Blob uploaded but DB commit fails | Orphan blob, harmless. Same key on retry, and GC can clean up later. |

---

## Why SQL?
The design needs these guarantees at once:

1. **Multi-row atomic transactions.** Step 3 (page + N children + parent DONE) must be all-or-nothing. This is the correctness core.
2. **Uniqueness constraint as the dedup mechanism.** The PK `(crawl_id, url_hash)` with `ON CONFLICT DO NOTHING` gives race-free exactly-once enqueue without application locking.
3. **Queue + dedup set in one place.** The frontier and the seen set are the same table, so "enqueue if unseen" is a single atomic statement. With Kafka plus Redis, you'd have two systems and a dual-write problem: a crash between "mark seen" and "enqueue" loses URLs.
4. **Concurrent claiming.** `FOR UPDATE SKIP LOCKED` provides a safe work queue without a separate broker.
5. **Durable state and easy recovery.** A restart loses nothing. Leases are just a column.
6. **Ad hoc queries.** Progress, failure breakdowns, and debugging are `GROUP BY` queries.
7. **Simple.** One system to operate fits an MVP.

**Tradeoffs (why we'd move later):** a single DB has a write ceiling (roughly thousands of tasks/sec), and the claim query is a hot spot. At scale you move the frontier to a partitioned queue and the seen set to a sharded store or Bloom filter. You then have to rebuild the atomicity guarantee yourself with a transactional outbox or idempotent consumers. That's why starting with SQL gives correctness first.

Want to see this as runnable code next (Python + SQLite/Postgres)?
