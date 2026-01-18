Good, this is a *systems-thinking* question. You’re zooming out from one pattern to the whole toolbox. I’ll give you a **clean mental map**, not a shopping list.

I’ll group **write-scaling techniques by the problem they solve**, because that’s how interviewers think.

---

## 1. Absorb write volume (don’t lose data)

### 1) **Log-based writing (append-only)**

You already know this one.

* Kafka, Kinesis, Pulsar
* Sequential writes → fast
* Decouples producers from consumers

Used when:

* Writes are huge
* Ordering matters
* Replay is required

This is the *foundation* pattern.

---

### 2) **Backpressure**

Not a data structure, but critical.

* Slow consumers signal producers
* Prevents cascading failures
* Protects storage and downstream systems

Without backpressure, scaling writes is fake.

---

## 2. Reduce write frequency (most important in practice)

### 3) **Batching**

Instead of:

```
1 write per event
```

Do:

```
1 write per N events
```

Examples:

* DB bulk inserts
* Kafka producer batching
* Flink window emission

Tradeoff:

* Slight latency
* Massive throughput gain

This is the **highest ROI optimization** in real systems.

---

### 4) **Coalescing / write collapsing**

If the same key is written repeatedly:

```
A → +1
A → +1
A → +1
```

Collapse into:

```
A → +3
```

Used in:

* Counters
* Metrics systems
* Caches

This is *semantic batching*.

---

## 3. Spread write load (avoid hotspots)

### 5) **Sharding / partitioning**

Split data by key.

* Hash-based
* Range-based
* Consistent hashing

Used when:

* Dataset grows
* One machine can’t handle writes

But:

> Sharding doesn’t fix hot keys by itself.

---

### 6) **Fan-out / fan-in**

You already learned this.

* Break key affinity early
* Restore it later

Used when:

* A single key dominates traffic

---

### 7) **Key salting**

Artificially split hot keys.

```
linkId → linkId#1, linkId#2, linkId#3
```

Later:

* Merge results

Tradeoff:

* Extra complexity
* Merge cost

This is a **last-mile fix**, not a primary design.

---

## 4. Make writes cheaper (per operation)

### 8) **Append-only storage (LSM trees)**

Used by:

* Cassandra
* HBase
* RocksDB
* LevelDB

Writes:

* Append to log
* Compact later

Why it scales:

* No random writes
* Sequential I/O
* Deferred cleanup

This is why these systems crush write-heavy workloads.

---

### 9) **Async writes**

Don’t block the client.

* Write to buffer
* Acknowledge early
* Flush later

Used in:

* Metrics
* Logging
* Analytics

Tradeoff:

* Potential data loss window

---

## 5. Defer or relax guarantees

### 10) **Eventual consistency**

Don’t coordinate synchronously.

* No global locks
* No quorum per write (or smaller quorum)

Massive write scalability win.

---

### 11) **Idempotent writes**

Allow retries safely.

* Write same event multiple times
* Deduplicate by ID

This doesn’t increase throughput directly, but:

* Makes retries cheap
* Prevents write amplification from failures

---

## 6. Change the problem (advanced)

### 12) **Pre-aggregation**

You’ve seen this.

* Aggregate early
* Store summaries, not raw data

Used in:

* Analytics
* Counters
* Top-K
* Monitoring

---

### 13) **Approximation**

When exactness is optional.

* Count-Min Sketch
* HyperLogLog
* Reservoir sampling

Orders of magnitude write reduction.

But:

> Only when the product allows it.

---

## The “popular & proven” short list (interview safe)

If you have to rattle off **the greatest hits**:

> “Log-based ingestion, batching, sharding, pre-aggregation, append-only storage, and fan-out/fan-in for hot keys.”

That covers 90% of real systems.

---

## Mental rule of thumb (this is the wisdom)

* **Logs absorb**
* **Batching reduces**
* **Sharding spreads**
* **Aggregation compresses**
* **LSM trees cheapen**
* **Fan-out fixes skew**

If you internalize that, you can design almost any write-heavy system.

---

If you want next:

* A **decision tree** (“when to use what”)
* Or a **write-scaling checklist** you can run during interviews
* Or map these techniques to **real systems** (Kafka, DynamoDB, Cassandra, Bigtable)

All good continuations.
