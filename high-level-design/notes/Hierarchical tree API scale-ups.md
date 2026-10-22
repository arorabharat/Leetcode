# Hierarchical tree API scale-ups

Archived chat transcript. Original user and assistant message text is preserved.

Session: `7397d9e3-909d-4108-b639-964a149f1afc`

## Turn 737 (2026-10-18T21:50:23.721Z)

### User

quicly do scale up

### Assistant

## Scale-up 2: Scale and cache

**Requirement:** traffic is high and concentrated on a few hot trees (shared links lead many users to the same nodes).

**Design**
```
Client → Load balancer → App servers (stateless) → Redis → DB primary + read replicas (sharded by root_id)
```

**What to cache** (match the access patterns):
- Children of a node (with `hasChildren`): key `children:{nodeId}:{cursor}`
- Ancestor path: key `path:{nodeId}`
- Node metadata

**Read flow (cache-aside):** check Redis, on a miss read the DB, then fill the cache with a TTL.
**Write flow:** write to the DB, then delete the affected keys. Adding a child under P invalidates `children:P` and the parent's `hasChildren` entry. Paths don't change, because moves are out of scope.

**Race condition:** a slow reader can refill the cache with old data after the invalidation. Fixes:
- Short TTL as a safety net.
- A version number on each tree, included in the cache key. A write bumps the version, so old entries become unreachable.
- Compare-and-set when filling.

**DB scaling**
- Read replicas for the read-heavy load (accept replication lag).
- Shard by `root_id`, so a whole tree lives on one shard and the ancestor and children queries never cross shards.
- Hot-shard risk: one very popular tree overloads one shard. Mitigate with caching, replicas for that shard, and CDN caching for public reads.

**Failure and protection**
- If Redis is down, fall back to the DB with rate limits, and coalesce duplicate requests (single-flight) to avoid a stampede.
- Metrics: hit ratio, p99 latency, evictions, replica lag.

## Scale-up 3: Reorder siblings

**Requirement:** users can change the order of children under the same parent (not move to another parent).

**Design:** add a sparse `position` column (100, 200, 300).
- Insert between two siblings: new position is the midpoint. One row is updated.
- Sort and cursor become `(position, node_id)`, and the index becomes `(parent_id, position, node_id)`.
- API: `PATCH /nodes/{id}/position` with `{afterNodeId, beforeNodeId}`. The server computes the number.
- When the gap runs out, rebalance that parent's children (renumber), inline or in the background. Fractional keys avoid this.
- Compared with alternatives: integer positions touch many rows, and a linked list makes pagination hard.
- Concurrency: the `node_id` tie-breaker keeps the order deterministic. Lock the parent for strictness.
- Invalidate the `children:{parent}` cache on reorder.

## Scale-up 4: Permissions

**Requirement:** grant and revoke view access per user. Default is everyone can view. If a user is blocked on any ancestor, they can't see the descendants.

**Data model**
```
node_permissions(node_id, user_id, type)   -- e.g. DENY or ALLOW
```

**Check:** the user can view node N only if no ancestor of N (including N) denies them.
- Using `ancestor_ids` makes this one query:
```sql
SELECT 1 FROM node_permissions
WHERE user_id = :u AND type = 'DENY'
  AND node_id = ANY(:ancestor_ids_plus_self);
```
- A row means blocked.

**API behaviour**
- Children list: filter out hidden children. Don't leak them through `hasChildren` or counts.
- A blocked node returns 403 or 404 (404 avoids revealing existence).
- Expanded tree: if any path node is blocked, deny. Filter siblings.

**Caching:** cache the permissions, but invalidate on revoke, or use a short TTL. Stale permission data is a security issue. Prefer caching raw data (the nodes) and checking permissions per request on top.

**Scale:** precompute effective access for hot users or trees if the check becomes expensive, and watch for revokes on a node with a large subtree (the check is by ancestor list, so no subtree rewrite is needed, which is a benefit of this design).

**Interview line for all three:** state the problem, give 2–3 options, pick one, and name the trade-off.

## Turn 773 (2026-10-18T21:53:53.479Z)

### User

> **Race condition:** a slow reader can refill the cache with old data after the invalidation. Fixes:
>
>
>
> -

would it kill you to explain in simple language and be direct

### Assistant

Fair. Here it is plainly.

## The problem
The cache can end up holding old data, even though you deleted it.

**Step by step:**
1. Reader A misses the cache and reads the DB. It gets children `[node1, node2]`.
2. Before A saves that to the cache, a writer adds `node3` to the DB.
3. The writer deletes the cache key.
4. Reader A now saves its old result `[node1, node2]` into the cache.

The cache now says `[node1, node2]`, but the DB has `node3`. Users keep seeing stale data until the TTL expires.

## Fixes
1. **Short TTL (simplest).** The stale entry expires soon, so the damage is limited. Say this first.
2. **Version in the cache key.** Each tree or parent has a version number. Writers increase it. Readers build the key as `children:{id}:v{version}`. Reader A's old data is saved under the old version key, which nobody reads anymore.
3. **Don't refill if it changed.** The reader only saves its result if the version is still the same as when it started. Redis can do this atomically (a Lua script or `WATCH`).

## What to say
> "Delete-on-write can race with a slow reader refilling old data. I'd use a short TTL as a safety net and put a version in the cache key, so stale refills land on a key nobody reads."

## Turn 808 (2026-10-18T21:56:46.994Z)

### User

> 1. **Version in the cache key.** Each tree or parent has a version number. Writers increase it. Readers build the key as `children:{id}:v{version}`. Reader A's old data is saved under the old version key, which nobody reads anymore.
> 2. **Don't refill if it changed.** The reader only saves its result if the version is still the same as when it started. Redis can do this atomically (a Lua script or `WATCH`).
>
> ##

you are always telling me half things which are not making sense, how would one get ID number to do caceh query and how would new version arrive

### Assistant

You're right, I skipped the mechanics. Here is the full flow.

## Where the version lives
Store a version counter in **Redis itself**, one per parent node:

```
key:   ver:{parentId}     value: 7
```
(Missing key means version 0.)

## Read flow
1. `v = GET ver:{parentId}` (e.g. 7)
2. `data = GET children:{parentId}:v7`
3. Hit: return it.
4. Miss: read the DB, then `SET children:{parentId}:v7 = data` with a TTL, and return.

## Write flow (add a child under P)
1. Insert the child in the DB.
2. `INCR ver:{P}` → 8. This is how a new version arrives: the writer bumps the counter.

## Why it fixes the race
- Slow reader A started when the version was 7 and read the old DB data.
- The writer inserts the node and bumps the version to 8.
- A finishes and saves its old data to `children:P:v7`.
- Every new reader asks `ver:P`, gets 8, and looks up `children:P:v8`. That is a miss, so it reads fresh data from the DB.
- A's stale entry on `v7` is never read again, and it expires through the TTL.

## Cost
- Every read does 2 Redis calls (the version, then the data). You can combine them into one round trip with a pipeline or Lua script.
- Old version keys must expire, so set a TTL on every cached entry.

## Simpler alternative
Skip versions. On write, delete `children:P`, and use a **short TTL** (say 30–60 seconds). A rare stale refill lasts at most that long. For this problem, that is usually acceptable, so say: "I'd start with delete-on-write plus a short TTL, and add versioned keys only if staleness must be tighter."

## Turn 843 (2026-10-18T22:00:07.554Z)

### User

> Slow reader A started when the version was 7 and read the old DB data

from where do this will come

### Assistant

Reader A is just an ordinary request that was unlucky with timing. Both pieces come from its own steps.

## Where each piece comes from
- **The version (7):** A reads `ver:P` from Redis at the start and **keeps it in a local variable**. It does not re-read it later.
- **The old data:** A reads the children from the DB right after that. That read happens *before* the writer commits, so it doesn't see the new child.

## Timeline
| Time | Reader A | Writer | Redis `ver:P` |
|---|---|---|---|
| 1 | reads `ver:P` → remembers **7** | | 7 |
| 2 | misses cache, reads DB → `[node1, node2]` | | 7 |
| 3 | (slow, still holding the result) | inserts `node3` in the DB | 7 |
| 4 | | `INCR ver:P` | **8** |
| 5 | saves result to `children:P:v7` (uses its remembered 7) | | 8 |
| 6 | | | |
| later | any new reader reads `ver:P` → 8 → looks up `children:P:v8` → miss → reads fresh DB | | 8 |

## The rule that makes it work
The reader must **read the version first, before the DB read, and save under that same remembered version**. Then:
- If nothing changed, version 7 is still current and the saved data is valid.
- If a write happened, the version moved to 8, so the stale save goes to a key nobody looks up.

## Important detail
The writer must bump the version **after** the DB commit. If it bumped first, a reader could read the new version (8), read the DB before the commit lands, and save old data under `v8`. That is the same bug again.

## Turn 879 (2026-10-18T22:01:08.196Z)

### User

> **The version (7):**

does it get stored in DBtoo

### Assistant

**No, not required.** In the simple design the version lives only in Redis. It's a cache-invalidation counter, not business data.

## Redis only
- `ver:{P}` is just an `INCR` counter.
- **Risk:** if Redis restarts or evicts the key, the counter resets to 0. Old cached entries like `children:P:v1` might then match a new version 1 and be served stale.
- **Fix:** put a TTL on every cached entry, so old entries disappear. This is the usual answer.

## Also stored in the DB
Add a `version` column to the parent row, or a `children_version` counter, and bump it in the **same transaction** as the child insert.
```sql
BEGIN;
INSERT INTO nodes (...);
UPDATE nodes SET children_version = children_version + 1 WHERE node_id = :P;
COMMIT;
```
- **Pro:** the version is durable and always consistent with the data, and it survives a Redis restart. Readers get it from the DB row (or a cached copy of it).
- **Con:** every insert under a popular parent updates the same row, which creates lock contention on hot nodes.

## What to say
> "I'd keep the version counter in Redis with TTLs on cached entries. If I needed it durable, I'd store a children version on the parent row and bump it in the same transaction as the insert, accepting some write contention on hot parents."

## Turn 915 (2026-10-18T22:01:57.921Z)

### User

okay now do scale up 3

### Assistant

## Scale-up 3: Reorder children

## Requirement
Users can change the order of children under the **same parent**. It is not a move to a different parent.

```
Before:  node1 → [c1, c2, c3, c4, c5]
After:   node1 → [c1, c5, c2, c3, c4]     (c5 moved to 2nd place)
```

## Step 1: Store the order
Add a `position` number to each node. Children are shown sorted by it.

| node | position |
|---|---|
| c1 | 100 |
| c2 | 200 |
| c3 | 300 |
| c4 | 400 |
| c5 | 500 |

Sort: `ORDER BY position, node_id`. Index: `(parent_id, position, node_id)`.

## Step 2: Move c5 to 2nd place
Put it between c1 (100) and c2 (200). New position = (100 + 200) / 2 = **150**.
```sql
UPDATE nodes SET position = 150 WHERE node_id = 'c5';
```
One row changes. That is why the numbers are spaced out.

## Why not the other options
| Option | Problem |
|---|---|
| Positions 1, 2, 3 | Moving c5 to 2nd shifts c2, c3, c4, so you update many rows |
| Linked list (`next_id`) | Few updates, but pagination is hard (must walk the list) and concurrent edits can break the chain |
| Sparse numbers (100, 200...) | Best balance, one row per move |

## API
```
PATCH /v1/nodes/{id}/position
{ "afterNodeId": "c1", "beforeNodeId": "c2" }
```
The client sends neighbours, and the server calculates the number. Clients never invent numbers.

New child (default): `position = max + 100`, so new nodes go to the end.

## Problems and fixes
1. **Gap runs out.** After many inserts between 150 and 151, there's no integer left. Rebalance: renumber that parent's children to 100, 200, 300... Do it in a transaction, or in a background job. Alternative: fractional or string keys, which never run out.
2. **Two users reorder at once.** They might pick the same number. `node_id` as a tie-breaker keeps the order stable. For strictness, lock the parent row during the reorder.
3. **Pagination.** Cursor becomes `(position, node_id)`. Items that move during paging may be missed or repeated, which is acceptable to mention.
4. **Cache.** Invalidate (or bump the version of) `children:{parent}` on reorder.
5. **Update the existing query.** Every children and expanded query must now use `ORDER BY position`, or the reorder has no visible effect.

## Migration
Existing nodes: backfill `position` from `created_at` order, spaced by 100.

## Interview line
> "I'd add a sparse `position` column so a reorder is a single-row update. The server computes the midpoint from the neighbours the client sends, and rebalances when the gap is exhausted."

## Turn 950 (2026-10-18T22:08:40.81Z)

### User

is there no better solution then taking a spaced number

### Assistant

Yes. Spaced numbers are the simplest, but there are better ones.

## 1. Fractional indexing (best in practice)
Use **string keys** that sort alphabetically, and generate a new key between any two neighbours. There is always room.

| node | key |
|---|---|
| c1 | `a` |
| c2 | `b` |
| c5 | `an` (between `a` and `b`) |

- Sort: `ORDER BY order_key`. A reorder updates one row.
- Gaps never run out, because you can always add a character (`a` < `am` < `an` < `b`).
- Figma, Linear and Notion use this. Libraries exist (LexoRank in Jira, `fractional-indexing` in JS).
- **Downside:** keys can grow longer with repeated inserts in the same spot. Occasional rebalancing is still possible, but it is rare.
- Concurrent edits can still produce the same key, so keep `node_id` as the tie-breaker.

## 2. Floating-point positions
Use midpoint values (1, 2 → 1.5 → 1.25...). It is simple, but floats run out of precision after about 50 splits in the same gap. Fractional strings fix this.

## 3. Linked list (`prev_id` / `next_id`)
A move changes about 3 rows and needs no numbers. The downside is that pagination and sorting need a recursive walk, which is slow and hard to index. Not recommended for read-heavy lists.

## 4. Array of child IDs on the parent
Store `child_order: [c1, c5, c2, ...]` on the parent row. A reorder is one write and the order is exact. The downside is that it doesn't scale to thousands of children (large row, write contention, awkward pagination).

## Comparison
| Approach | Reorder cost | Runs out of room? | Pagination |
|---|---|---|---|
| Plain 1, 2, 3 | Many rows | No | Easy |
| Spaced numbers | 1 row | Yes, needs rebalance | Easy |
| **Fractional strings** | 1 row | Practically no | Easy |
| Linked list | ~3 rows | No | Hard |
| Array on parent | 1 row | No | Hard |

## What to say
> "I'd start with spaced integers because they are simple. If rebalancing becomes a problem, I'd move to fractional indexing with string keys, which gives one-row updates and effectively unlimited insert room, while keeping the `(parent_id, order_key, node_id)` index and cursor pagination."

## Turn 987 (2026-10-18T22:10:25.278Z)

### User

okay now scale up

### Assistant

## Scale-up 4: Permissions

## Requirement
- Add **view** permissions only.
- Default: everyone can view everything.
- Admins can **grant** or **revoke** access for specific users on specific nodes.
- **If a user can't view a node, they can't view any of its descendants**, even if they have access to the child.

```
rootNode              everyone
└── node1             User A allowed
    └── childNode1    User A blocked
```
User A can see `node1` but not `childNode1` or anything under it.

## Step 1: Data model
Because access is open by default, store only the **exceptions**, i.e. blocks.

```
node_permissions(node_id, user_id, type)   -- type = 'DENY'
PRIMARY KEY (node_id, user_id)
```
Revoke = insert a row. Grant back = delete the row.

(If you also want "restricted nodes visible only to some users", add an `ALLOW` type or a `restricted` flag on the node plus an allow list. Mention this as a design choice.)

## Step 2: Check access to one node
A user can view node N only if **no node on the path root → N has a DENY row for that user**. Use the ancestor list:

```sql
SELECT 1 FROM node_permissions
WHERE user_id = :user
  AND node_id = ANY(:ancestor_ids_plus_self);
```
- Row found → blocked (return 404, so you don't reveal it exists).
- No row → allowed.

Revoking a node automatically blocks everything below it, with no updates to descendants.

## Step 3: Apply to the APIs
- **Get node / ancestors:** run the check first.
- **List children:** hide the blocked children. If the parent is allowed, check each child's DENY row (a batched query, `node_id = ANY(:child_ids)`), or add a `NOT EXISTS` to the main query. Children of a blocked child can't be reached anyway.
- **`hasChildren`:** don't count children the user can't see, or you leak their existence.
- **Expanded tree:** if any path node is blocked, deny. Filter the siblings.

## Step 4: Performance
- Permission rows are few (exceptions only), so lookups by `(node_id, user_id)` are fast.
- If it gets heavy, cache each user's denied node IDs.
- **Invalidation matters:** a revoke must take effect immediately. Use a short TTL, or bump a per-user permission version on every revoke, so that stale cached access can't be used. Stale permissions are a security problem, not just a freshness one.
- Cache raw node data shared across users, and apply the permission check on each request. Don't cache per-user results unless the key includes the user.

## Step 5: Edge cases
- **Concurrent revoke and read:** a read in progress may return data just before the revoke. Accept it, or read within a transaction.
- **Root-level restriction:** a DENY on a root hides the whole tree.
- **Audit:** log grants and revokes.

## Interview line
> "Since default access is open, I'd store only DENY exceptions. Visibility is checked against the node's ancestor list, so one revoke covers the whole subtree. I'd filter children and `hasChildren` by the same rule, and invalidate cached permissions immediately on revoke."
