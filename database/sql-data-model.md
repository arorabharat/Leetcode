
Yes, exactly — but I would make the index a **composite index on `(user_id, created_at)`**, rather than having two separate indexes.

Suppose:

```sql
reviews
-------------------------
id
user_id
review_text
created_at
```

Query:

```sql
SELECT *
FROM reviews
WHERE user_id = 123
ORDER BY created_at DESC
LIMIT 3;
```

Create:

```sql
CREATE INDEX idx_reviews_user_created
ON reviews(user_id, created_at DESC);
```

### Why composite?

Your query has two operations:

1. Find reviews belonging to `user_id = 123`
2. Get them ordered by `created_at DESC`

The index:

```text
(user_id, created_at)
```

is effectively organized like:

```text
user_id    created_at
----------------------------
123        2026-10-03 14:30
123        2026-09-28 10:20
123        2026-09-20 18:40
123        2026-08-15 12:10
...
456        2026-10-03 ...
```

So MySQL can:

```text
Find user_id = 123
        ↓
Start at newest review
        ↓
Read 3 entries
        ↓
STOP
```

It doesn't need to retrieve all of the user's reviews and sort them.

### This is particularly important

Without the appropriate index, MySQL might have to:

```text
Find all reviews for user 123
        ↓
Sort by created_at
        ↓
Take 3
```

With `(user_id, created_at)`:

```text
B+ Tree
   ↓
user_id = 123
   ↓
already ordered by created_at
   ↓
first 3
```

So the query becomes extremely efficient.

### One more improvement

If two reviews can have exactly the same timestamp, use `id` as a deterministic tie-breaker:

```sql
CREATE INDEX idx_reviews_user_created_id
ON reviews(user_id, created_at DESC, id DESC);
```

And:

```sql
SELECT *
FROM reviews
WHERE user_id = 123
ORDER BY created_at DESC, id DESC
LIMIT 3;
```

This is a very common **indexing pattern**:

> **Equality/filter columns first → ordering columns next.**

So here:

```text
WHERE user_id = ?
ORDER BY created_at DESC
LIMIT 3
```

→

```text
INDEX(user_id, created_at)
```

And because you're only asking for **3**, MySQL can stop after finding those 3 matching index entries.

Refine the review query

- Show the complete table definition
- Explain timestamp tie handling


Yes. **MySQL can absolutely be horizontally scaled**, but the approach depends on what you're trying to scale: reads, writes, or the total dataset.

For your review system, I'd evolve it progressively rather than immediately jumping to sharding.

### 1. Start with one MySQL primary

```text
                    Application
                         |
                       MySQL
                    Primary DB
                    /        \
                 users      reviews
```

This handles both:

```text
INSERT reviews
UPDATE review_count
SELECT latest 3 reviews
```

---

### 2. Add read replicas

If reads become the bottleneck:

```text
                         Application
                       /      |       \
                      ↓       ↓        ↓
                  Read      Read     Write
                 Replica   Replica   Primary
```

The primary handles writes:

```sql
INSERT INTO reviews ...
UPDATE users SET review_count = review_count + 1
```

Replicas handle queries such as:

```sql
SELECT *
FROM reviews
WHERE user_id = ?
ORDER BY created_at DESC
LIMIT 3;
```

This gives you **horizontal scaling for reads**.

But there's an important issue: **replication lag**.

If you write a review to the primary and immediately read from a replica, the new review may not be there yet.

---

### 3. What about horizontal scaling of writes?

That's where **sharding** comes in.

You can partition users across multiple MySQL instances:

```text
                    Application
                         |
                  Sharding Router
                 /       |       \
                ↓        ↓        ↓
             MySQL 1  MySQL 2  MySQL 3
             Users     Users     Users
             1-1M      1M-2M     2M-3M
```

For your use case, `user_id` is a very natural shard key:

```text
shard = hash(user_id) % N
```

Then all reviews belonging to a user go to the same shard:

```text
user_id = 123
     ↓
Shard 2
     ↓
users + reviews
```

That's particularly nice because your main queries are:

```sql
WHERE user_id = ?
```

and:

```sql
WHERE user_id = ?
ORDER BY created_at DESC
LIMIT 3;
```

Both can be routed to a **single shard**.

---

## Your `review_count` becomes interesting

Suppose:

```text
user 123 → shard 2
```

Then both:

```text
users.review_count
reviews
```

live on shard 2.

Your transaction remains local:

```text
BEGIN

INSERT review
UPDATE users
SET review_count = review_count + 1

COMMIT
```

That's excellent because you **don't need a distributed transaction across multiple MySQL databases**.

---

## A possible final architecture

At very large scale:

```text
                         API
                          |
                    Sharding Layer
                 /        |        \
                ↓         ↓         ↓
           Shard 1    Shard 2    Shard 3
           MySQL      MySQL      MySQL
             |          |          |
          replicas   replicas   replicas
```

Each shard could have:

```text
                 Primary
                /       \
          Replica       Replica
```

Writes → primary  
Reads → replicas

And:

```text
user_id
   ↓
consistent hashing / shard mapping
   ↓
specific MySQL shard
```

### The important interview answer

I'd explain the scaling strategy in this order:

| Problem | Solution |
|---|---|
| Query slow | Proper indexes |
| Read traffic increasing | Read replicas |
| Primary overloaded by reads | Move reads to replicas |
| Dataset too large for one DB | Sharding |
| Write traffic too high | Sharding |
| One shard becomes hot | Better shard key / consistent hashing |
| Very frequent count reads | Redis/cache |
| Replica lag | Read-after-write routing / primary reads |

And **yes, MySQL itself supports horizontal scaling**, but there's a distinction:

> **Read replicas = horizontal scaling of reads.**  
> **Sharding = horizontal scaling of both data capacity and writes.**

For your particular review system, **`user_id` is a strong candidate for the shard key** because your access pattern is already user-centric.

Explore the scaling details

- Handle replica lag after writes
- Design shard routing by user_id