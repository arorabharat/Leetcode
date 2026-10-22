# Shared todo API design

Archived chat transcript. Message text is preserved as stored in the session.

Session: `b208125a-cb9c-4868-b50a-e6ff04724a1a`

## Turn 7 (2026-10-12T11:27:31.643Z)

### User

|  | Imagine you are tasked with designing a backend API for a shared/collaborative TODO list that supports real-time updates, enabling users to see new or modified items by others immediately without refreshing the page. |
| --- | --- |
|  | • Please share the functional require<br>ments with the candidate. |
|  | Functional requirements From a user's perspective, there are the following key experiences that we want to build here: |
|  | • Users can create a new Too List and share it with others. Only users with whom the list is shared can access it. |
|  | • This should prompt the candidate to ask whether sharing includes permissions like view-only or edit. |
|  | • Users can view all Too Lists created or shared with them. |
|  | • Users can add and view ToDo List items. |
|  | • This should prompt the candidate to ask whether viewing includes filtering, sorting, or pagination. |
|  | • Users can view other users' modifications in near real-time without reloading the page. |
|  | • This should prompt the candidate to clarify what near real-time means, such as the acceptable delay. |
|  | • While you can offer guidance, the candidate should independently and proactively consider non-functional requirements. |
|  | Non-functional requirements |
|  | • Scale |
|  | • Overall scale (medium-scale app): |
|  | • MAU: \\~2,000; DAU: \\~500 |
|  | • Peak concurrent users: \\~150-250 |
|  | • Global traffic targets: |
|  | • Read traffic: \\~100-200 reads/sec typical, \\~250-350 reads/sec peak |
|  | • Write traffic: \\~50-100 writes/sec typical, \\~150-250 writes/sec peak |
|  | • New list creation rate: |
|  | • Average: 50-100 new lists/day (\\~0.5-1.2 lists/min) |
|  | • Design peak: 5-10 new lists/min (\\~0.1-0.17 lists/sec) |
|  | • Performance |
|  | • The backend API must scale to support increased traffic without degrading performance or reliability. The backend API response time for a ToDo list initial load must remain under |
|  | 1 second. |
|  | • Availability/Reliability: no single points a failure. |




help me build a high level and low level design for this

### Assistant

# Collaborative TODO List: HLD and LLD

## 0. Clarifying assumptions (state these up front)
- **Sharing:** Owner / Editor / Viewer roles. Sharing is by user ID or email. Only the owner can manage shares.
- **Viewing:** Cursor pagination (default 50 items), filter by status/assignee, sort by position or created/updated time.
- **Near real-time:** p95 under 2s from write to other clients. The target is under 500ms.
- **Conflicts:** Last-write-wins per field, using a version number for optimistic concurrency.
- **Auth:** JWT from an existing identity provider.
- **Deletes:** Soft delete (tombstone), so offline clients can sync.

## 1. Capacity estimate
- Peak is about 350 reads/s and 250 writes/s. That is small, so one modest Postgres primary plus a replica handles it comfortably.
- Peak concurrent users are about 250, so a few hundred WebSocket connections. Two or three app nodes are enough.
- Storage: 100 lists/day × ~50 items works out to a few GB at most, even after years.
- **The real design concern is availability and fan-out correctness, not raw throughput.** Avoid over-engineering.

## 2. High-level design

```
Clients (Web)
   │ HTTPS REST            │ WebSocket / SSE
   ▼                       ▼
 Load Balancer (multi-AZ, sticky not required)
   │
 ┌─┴───────────────────────────────┐
 │ API/Realtime service (≥3 stateless nodes, multi-AZ) │
 │  - REST handlers                │
 │  - WS gateway (subscribe to list channels) │
 └─┬───────────┬───────────────────┘
   │           │ pub/sub (list:{id})
   │           ▼
   │     Redis (HA, with replica + sentinel/managed multi-AZ)
   ▼
 PostgreSQL (primary + sync standby in another AZ + read replica)
```

**Key choices**
- **Postgres:** the data is relational (users, lists, memberships, items), and it needs transactions and strong consistency.
- **Realtime transport:** WebSocket, with SSE or long-polling as a fallback. Updates are mostly server→client, so SSE is also viable. I'd choose WS because clients also send presence and ack messages.
- **Fan-out:** each node subscribes to Redis pub/sub channels `list:{id}` for the lists its connected clients have open. After a DB commit, the writer publishes an event. Every node pushes it to its local subscribers.
- **Reliability of events:** use the **transactional outbox**. The write and an `outbox` row commit in one transaction, and a relay publishes to Redis. This avoids a "DB committed but publish failed" gap.
- **Missed events:** every list has a monotonically increasing `list_seq`. Clients reconnect with `?since=seq`. The server replays from the `item_events` table, or tells the client to refetch if the gap is too large. Redis pub/sub is lossy, so this catch-up path covers it.
- **No single point of failure:**
  - LB: managed and multi-AZ.
  - App: stateless, with autoscaling and health checks.
  - Redis: managed multi-AZ with failover. If it is down, clients fall back to polling every 5s, so the system degrades but keeps working.
  - Postgres: synchronous standby with automatic failover, plus PITR backups.
- **Initial load under 1s:** one indexed query by `list_id` with a limit, then an optional Redis cache of the first page. At this scale, the indexed query is already about 10–30ms.
- **AuthZ:** check the membership role on every REST call and on every WS subscribe. Cache memberships in Redis for 60s, and invalidate the cache on share/unshare. Unshare must also close the user's active subscription.

## 3. Low-level design

### 3.1 Data model (Postgres)
```sql
users(id uuid pk, email unique, name, created_at)

todo_lists(
  id uuid pk, owner_id fk, title, 
  last_seq bigint not null default 0,   -- per-list change counter
  created_at, updated_at, deleted_at)

list_members(
  list_id fk, user_id fk, role text check (role in ('owner','editor','viewer')),
  created_at, primary key(list_id, user_id))
-- index (user_id, list_id) for "my lists"

todo_items(
  id uuid pk, list_id fk, title, description,
  status text check (status in ('open','done')),
  assignee_id null, position numeric,   -- fractional ordering
  version int not null default 1,
  created_by, created_at, updated_at, deleted_at)
-- index (list_id, deleted_at, position, id) ; (list_id, status)

item_events(                              -- change log + outbox
  list_id, seq bigint, type, item_id, payload jsonb, 
  created_at, published bool default false,
  primary key(list_id, seq))
```
Write path: `UPDATE todo_lists SET last_seq=last_seq+1 WHERE id=? RETURNING last_seq`. This also serializes per-list writes, which gives a gap-free ordering. Then update the item and insert the event, all in one transaction. Per-list contention is low at this scale.

### 3.2 REST API
```
POST   /v1/lists                         {title}              -> 201 list
GET    /v1/lists?cursor=&limit=          lists I own or am shared on (+ role)
GET    /v1/lists/{id}                    metadata + role
DELETE /v1/lists/{id}                    owner only

POST   /v1/lists/{id}/members            {email|userId, role}  owner only
PATCH  /v1/lists/{id}/members/{uid}      {role}
DELETE /v1/lists/{id}/members/{uid}

GET    /v1/lists/{id}/items?status=&assignee=&sort=position|created|updated&cursor=&limit=
POST   /v1/lists/{id}/items              {title, ...}  Idempotency-Key header
PATCH  /v1/lists/{id}/items/{itemId}     {fields..., version}  -> 409 if stale
DELETE /v1/lists/{id}/items/{itemId}
GET    /v1/lists/{id}/events?since=seq   catch-up
```
- `GET items` returns `list_seq` along with the page, so the client subscribes from exactly that point and misses nothing.
- Errors: 401, 403 (no role), 404 (hide existence from non-members), 409 (version conflict), 429.
- Idempotency keys make client retries safe on POST.

### 3.3 Realtime protocol (WS `/v1/ws`)
```
C→S {op:"subscribe", listId, sinceSeq}
S→C {op:"event", listId, seq, type:"item.created|updated|deleted|member.removed", item:{...}}
S→C {op:"resync", listId}     // gap too large, so refetch
C→S {op:"ping"} / S→C {op:"pong"}   // heartbeat every 25s
```
- The server checks the role on subscribe. If `sinceSeq` is less than `current_seq`, it replays the missing events first, then goes live.
- The client applies events in `seq` order and ignores any `seq` it has already seen. If it sees a gap, it refetches.
- Limits: max subscriptions per connection, and a per-user rate limit.

### 3.4 Write flow
1. The client sends `PATCH`. The API checks the JWT and the role (editor or above).
2. Transaction: bump `last_seq`, update the item `WHERE version=:v`, insert the `item_events` row.
3. Commit, then return 200 with the new item.
4. The outbox relay (or a post-commit hook with relay fallback) publishes to Redis `list:{id}`.
5. Each node pushes the event to its subscribed sockets. This usually takes tens of milliseconds.

### 3.5 Concurrency
- Two editors editing the same field: the second gets 409 with the current item, and the client merges or retries. Edits to different fields use PATCH semantics, so they don't clobber each other.
- Reordering uses fractional `position` (midpoint of neighbours), with an occasional rebalance.
- Cheap alternative: skip the version check and use last-write-wins by `updated_at`. This is acceptable for a TODO app, but I'd call out the tradeoff.

### 3.6 Caching
- Memberships: Redis, TTL 60s, invalidated on change.
- First page of items: optional cache keyed `list:{id}:page1:{seq}`. Because the key includes the seq, it never goes stale.
- Hot reads can go to the replica. Use the primary for read-your-writes when the client sends the last seen seq.

### 3.6b Non-functional details
- **Security:** TLS, JWT validation on WS handshake and periodic re-validation, input size limits, parameterized queries, and rate limiting per user (429).
- **Observability:** metrics for p95 latency, event publish-to-deliver lag, WS connection count, outbox backlog, and error rate. Add tracing and alerts on SLO breach.
- **Deployment:** rolling deploys with connection draining. Clients auto-reconnect with jittered backoff and resume from `sinceSeq`, so deploys cause no data loss.
- **Retention:** prune `item_events` older than 7 days. Older gaps force a resync.

## 4. Failure scenarios
| Failure | Behaviour |
|---|---|
| App node dies | LB reroutes. Clients reconnect and resume via seq. |
| Redis down | Polling fallback via `/events?since`. Outbox relay retries. |
| Postgres primary down | Failover to the standby (RPO≈0 with sync replication). Writes are unavailable for about 30–60s, and reads continue from the replica. |
| Publish lost | The client detects a seq gap or a periodic heartbeat that includes `current_seq`, and then catches up. |
| User unshared | The member-removed event closes their subscription, and the 403 applies immediately. |

## 5. Scaling beyond the stated numbers
- Shard WS gateways by connection count (they are stateless, so add nodes).
- Replace Redis pub/sub with Redis Streams or Kafka if replay durability matters.
- Partition `todo_items` and `item_events` by `list_id` hash if tables grow past hundreds of millions of rows.

## 6. Tradeoffs worth discussing
- WS vs SSE vs polling. Polling at 250 users is workable but wastes load, and it misses the latency target.
- Pub/sub vs a durable log: pub/sub is simpler, and the seq catch-up covers its loss.
- SQL vs NoSQL: SQL, because the data is relational and the scale is small.
- OT/CRDT for collaborative text in a description field is out of scope. Use last-write-wins on a single field.

I can also draw this as a diagram, or write the OpenAPI spec or SQL DDL if you want.

## Turn 41 (2026-10-12T11:32:11.234Z)

### User

before jumping into muliple problems I want to focus one problem at a time, ONe problem I see is concurrent update lets brainstorm on that

### Assistant

# Concurrent updates: brainstorm

## First, which conflicts can happen?
| Scenario | Example | Severity |
|---|---|---|
| A. Same field, same item | A and B both rename "Buy milk" | Real conflict |
| B. Different fields, same item | A marks done, B edits title | Usually mergeable |
| C. Edit vs delete | A edits an item that B just deleted | Needs a rule |
| D. Reorder vs reorder | A and B both drag items | Position collisions |
| E. Create duplicates | Client retries POST | Idempotency, not concurrency |
| F. Stale client | A is offline or missed an event, then writes | Detection problem |

## Approaches

**1. Last-write-wins (LWW)**
- Use the server timestamp or the arrival order.
- Pros: trivial and no conflicts surface to the user.
- Cons: silent data loss. Client clocks can't be trusted, so use server time.
- Fits scenario B only if PATCH sends changed fields only.

**2. Optimistic concurrency (version number)**
- The client sends `version`, and the server runs `UPDATE ... WHERE id=? AND version=?`.
- On a mismatch, it returns 409 plus the current item.
- Pros: no lost updates and no locks held. Contention is rare at this scale.
- Cons: the client needs conflict handling. Whole-item versions produce false conflicts for scenario B.

**3. Per-field versioning or field-level merge**
- The PATCH carries `{changes, baseVersion}`. The server checks whether the fields changed since `baseVersion` overlap with the fields in the PATCH.
- No overlap means auto-merge. Overlap means 409.
- Pros: handles B cleanly. Cons: more bookkeeping (a per-field `updated_at/version` or a lookup in `item_events`).

**4. Pessimistic locking (`SELECT ... FOR UPDATE`)**
- Pros: simple server-side reasoning.
- Cons: only protects the DB transaction, not the user's "think time". It does not solve the user-facing problem. It is still useful inside the write transaction.

**5. Soft edit locks or presence ("Bob is editing")**
- Reduces conflicts through UX rather than correctness. It is good as a complement, not as a primary mechanism.

**6. OT / CRDT**
- Needed only for concurrent text edits within a single field. It is overkill for a TODO app, so I'd mention it and reject it.

## My recommendation
A hybrid of **2 and 3**:
1. Every item has a `version`, incremented on each write.
2. The PATCH sends only the changed fields plus `baseVersion`.
3. In one transaction, run `SELECT ... FOR UPDATE` on the row:
   - If `version == baseVersion`, apply and bump the version.
   - Otherwise, check whether the fields changed since `baseVersion` intersect the patch. If not, merge and apply. If they do, return 409 with the current item.
4. Clients get the new state through the realtime event, so a rejected client sees the winner right away.
5. Specific rules:
   - **Delete vs edit:** delete wins, and the edit returns 410 or 404. The tombstone keeps this deterministic.
   - **Done toggle:** make it idempotent, with `status=done` as a set operation rather than a toggle.
   - **Reorder:** use fractional positions. If two items get the same position, break ties by `id`. A background rebalance fixes density.
   - **Counters or append-style data:** none here, so no special handling.

The simpler alternative is whole-item versions with a 409 for any mismatch. Start there, and add field-level merge only if false conflicts show up in practice.

## Interview-style discussion points
- What happens to the user's edit on 409? Options are auto-retry on rebased state, show a diff, or silently take the server's value.
- Is per-field LWW acceptable for a TODO app? Probably yes. Say so explicitly, as a business decision.
- Optimistic UI: the client applies the change locally, then reconciles with the server event. This needs the event to carry the version.

## Questions for you
Which direction do you want to dig into next?
1. Details of the field-level merge (schema and transaction code).
2. Reordering and fractional positions.
3. The client-side conflict UX and optimistic updates.
4. Which approach to choose and defend for the interview.
