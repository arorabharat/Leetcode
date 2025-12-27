Alright. Let’s do this **properly**, end to end, no hand-waving.

We’ll take **Option 1: Centralized sequence generator**, assume a **real database**, **multi-AZ replication**, and walk through **exact request flow, replication behavior, failure modes, and why this still hurts**.

This is the version teams actually build in the real world before they later replace it.

---

## Mental model first (very important)

This option is based on one core assumption:

> “There is exactly **one source of truth** for the counter.”

Replication is for **durability and availability**,
**not** for parallel ID generation.

Only **one writer** is allowed to increment the counter.

---

## High-level architecture

![Image](https://miro.medium.com/1%2AX2x-kL7ujebuv4FJodZcuQ.jpeg)

![Image](https://jayendrapatil.com/wp-content/uploads/2016/06/RDS-Read-Replicas-vs-Multi-AZ.png)

![Image](https://rootninja.files.wordpress.com/2010/11/image.png)

```
Clients (any AZ)
   |
   v
Application Service (any AZ)
   |
   v
Primary DB (AZ-1)  <-- ONLY writer
   |
   +--> Replica DB (AZ-2)
   |
   +--> Replica DB (AZ-3)
```

Key rule:

* **Writes go only to the primary**
* Replicas are **read-only**

---

## Database schema (simple but critical)

Example table:

```
id_sequence
------------------
name        (PK)
current_val BIGINT
```

Single row example:

```
("global_id", 1029384756)
```

---

## End-to-end request flow (happy path)

Let’s say a client wants to create a document.

### Step 1: Client sends request

```
POST /documents
(payload: 200–500 words)
```

---

### Step 2: App needs an ID (blocking step)

The application **cannot proceed** without the ID.

So it executes:

```
BEGIN TRANSACTION;

UPDATE id_sequence
SET current_val = current_val + 1
WHERE name = 'global_id';

SELECT current_val
FROM id_sequence
WHERE name = 'global_id';

COMMIT;
```

Important details:

* The update acquires a **row-level lock**
* This guarantees **no duplicates**
* All ID requests are **serialized**

---

### Step 3: Primary DB assigns the ID

Suppose:

* Old value = `1029384756`
* New value = `1029384757`

That value is returned to the app.

---

### Step 4: App uses the ID

```
INSERT INTO documents(id, content)
VALUES (1029384757, ...);
```

At this point:

* The document exists
* The ID is unique globally

---

### Step 5: Replication happens (async or sync)

The primary now ships changes to replicas.

Two possibilities:

#### Async replication (most common)

* Primary returns success immediately
* Replicas catch up later
* Small replication lag exists

#### Sync replication (rare, expensive)

* Primary waits for replicas to confirm
* Higher latency
* Better durability guarantees

---

## Why this works (from a correctness standpoint)

* **Single writer** → no conflicts
* **Database lock** → strict ordering
* **Replication** → durability across AZs
* **Crash recovery** → counter resumes correctly

From a *correctness* perspective, this is rock-solid.

---

## Now the cracks start showing

Let’s talk about what actually happens under load.

---

## Bottleneck #1: Serialization

Every ID request must:

* Lock the same row
* Wait its turn
* Increment one integer

Even if your DB can do 50k QPS:

* That’s your **global write limit**
* Entire system throughput is capped

Your whole platform queues behind one row.

---

## Bottleneck #2: Cross-AZ latency

If:

* App runs in AZ-2
* Primary DB is in AZ-1

Every ID generation involves:

* Network hop AZ-2 → AZ-1
* DB lock wait
* Network hop back

This inflates:

* p95 latency
* p99 latency
* timeout probability

---

## Failure scenario #1: Primary DB crashes

Sequence of pain:

1. Primary DB goes down
2. Replica is promoted
3. Connections reconnect
4. Writes resume

But during failover:

* ID generation **stops**
* All write requests fail or hang
* Clients retry
* Load spikes right after recovery

This is a classic **thundering herd**.

---

## Failure scenario #2: Client retry ambiguity

Worst subtle case:

1. DB increments counter
2. Network drops before response
3. Client retries
4. Counter increments again

Result:

* Gap in IDs (logically fine)
* Operators panic
* Engineers add “fixes” that make it worse

IDs don’t need to be gapless. Humans just think they should be.

---

## Failure scenario #3: Split brain (rare, catastrophic)

If leader election goes wrong:

* Two primaries briefly exist
* Both increment counters independently
* **Duplicate IDs appear**

This is why databases are extremely conservative about failover.

---

## Can we shard the counter?

People try:

```
counter_1
counter_2
counter_3
```

Then hash requests.

What happens:

* Ordering breaks
* Complexity explodes
* You’ve reinvented Snowflake badly

---

## Where document content fits (and doesn’t)

You *can* do:

```
BEGIN;
hash = SHA256(document);
INSERT INTO documents(hash, ...) IF NOT EXISTS;
UPDATE counter;
COMMIT;
```

This helps with:

* Deduplication

It does **not** help with:

* Throughput
* Latency
* Centralization

The counter is still the choke point.

---

## When this design is actually acceptable

This is important.

Use this when:

* Write QPS is low
* Strong ordering matters
* You want dead-simple reasoning
* You’re okay with a single writer

Examples:

* Invoice numbers
* Financial ledgers
* Admin systems
* Early-stage products

---

## Why large systems abandon this

Because:

* **ID generation becomes part of the critical path**
* **Critical paths must be local and fast**
* **Central coordination kills tail latency**

That’s the reason Snowflake-style IDs exist:
They move uniqueness **into math**, not **into a mutex**.

---

## One sentence summary (take this with you)

> A centralized auto-increment ID generator with multi-AZ replication is **correct, durable, and simple** — but it scales by queueing, not by distributing.

It fails not because it’s wrong,
but because it asks one integer to carry the weight of the entire system.

That’s too much existential pressure for a number.
