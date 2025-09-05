# Kafka (called “CFA” in the talk) — System Design Interview Revision Notes

[![Alt text](https://img.youtube.com/vi/DU8o-OTeoCc/0.jpg)](https://www.youtube.com/watch?v=DU8o-OTeoCc)

> The speaker repeatedly says **CFA** while describing **Kafka**. Treat **CFA = Kafka** for the purposes of these notes.

## What Kafka/CFA Is (Big Picture)

* **Event streaming platform** usable as a **message queue** and for **stream processing**.
* Scales via **topics** (logical grouping) and **partitions** (physical append-only logs) across **brokers** (servers).
* Core pitch for interviews: reliable ordering *within a partition*, horizontal scalability via partitioning, consumer *
  *groups** for parallelism, and strong **durability** via replication.

---

## Motivating Example (World Cup Live Updates)

* **Producers** (match reporters) push **events** (goals, bookings, subs).
* **Consumers** update the website in (per-game) **order**.
* Horizontal scale introduces ordering issues → **partition by game** to keep per-game order.
* High read throughput → multiple consumers but avoid duplicates via **consumer groups** (each message processed by
  exactly one consumer in a group).
* Multi-sport expansion → use **topics** (e.g., `soccer`, `basketball`) so consumers subscribe to only what they need.

---

## Core Concepts & Terms (highlighted for recall)

* **Broker**: server (physical/virtual) in the cluster; stores partitions.
* **Partition**: **ordered**, **immutable**, append-only **log file** on disk. Provides **per-partition ordering**.
* **Topic**: **logical grouping** of partitions (organize data); partitions **physically scale** data.
* **Producer / Consumer**: writes to / reads from topics.
* **Consumer Group**: a set of consumers where **each message is delivered to exactly one** consumer in the group.
* **Record/Message**: consists of **key**, **value**, **timestamp** (ordering), **headers** (key–value metadata).
* **Offset**: per-partition position (0, 1, 2, …). Consumers track and **commit offsets** (to Kafka) for recovery.
* **Partitioning strategy**: choose **partition key** (e.g., `gameId`) → **hash(key) mod N** decides partition (speaker
  mentions **Murmur** as a typical fast hash).
* **Leader / Followers**: each partition has a **leader** replica handling reads/writes and **followers** that replicate
  and can take over on failure.
* **Cluster Controller**: assigns partition leadership and tracks partition → broker mapping.

---

## Message Lifecycle (High Level)

```mermaid
sequenceDiagram
    autonumber
    participant P as Producer
    participant CC as Cluster Controller
    participant B as Broker (Leader for P_i)
    participant C as Consumer (Group G)
    P ->> P: Create record (key, value, ts, headers)
    P ->> P: Pick topic; compute partition = hash(key) % N (or round-robin if no key)
  P->>CC: Discover metadata (which broker leads partition)
P->>B: Append record to partition (append-only log)
Note right of B: Offsets assigned 0..N
C->>B: Fetch from last committed offset+1
C->>B: Process record
C->>B: (periodically) Commit offset to Kafka
Note over C, B: On consumer restart, resume from committed offset
```

---

## When to Use Kafka in an Interview

* **Asynchronous processing**: e.g., **video transcoding**—emit `videoId` + **S3 URL**; workers transcode
  asynchronously.
* **Ordered processing**: e.g., **ticketing wait queues**—release users in batches while respecting arrival order (per
  key).
* **Decoupling & independent scaling**: e.g., code-judging—frontends enqueue, controlled pool of workers executes.
* **Streaming analytics / aggregation**: e.g., **ad click aggregator**—continuous counts/metrics in near real time.
* **Pub/Sub fan-out**: e.g., **live comments**—multiple services subscribe to the same stream to update many clients.

---

## Ordering & Partitioning (Trade-offs)

* **Order guarantee**: only **within a single partition**. Choosing the **key** is crucial.
* **Without a key**: round-robin distribution; **no cross-partition ordering**.
* **Hot partitions** (skewed keys, e.g., a viral **adId**):

    * **Remove key** (if you truly don’t need order).
    * **Compound key** (e.g., `adId:shardId` where `shardId`∈\[1..K]) to spread load.
    * **Use another dimension** (e.g., add **userId** prefix or partial).
    * **Backpressure**: slow producers if a topic/partition is overwhelmed (only feasible in some systems).

---

## Consumer Groups, Offsets & Recovery

* **Exactly-once per group**: each record goes to **one** consumer in the group.
* **Offset commits**:

    * Commit **after** finishing the logical unit of work (e.g., after persisting to **S3**).
    * Committing **too early** risks **data loss** on crash; committing **too late** risks **reprocessing**.
* **Rebalance**: if a consumer dies, the group **rebalances** partition assignments automatically.

---

## Durability & Fault Tolerance

* **Replication**: leader + followers; followers replicate from leader; auto leader election on failure.
* **Key configs**:

    * **`acks`**: how many replicas must acknowledge a write before the producer proceeds.

        * `all` → highest durability (wait for all in-sync replicas), lower throughput.
        * lower values → higher throughput, lower durability.
    * **Replication factor** (default **3**): more replicas → higher durability & storage cost.

---

## Errors & Retries

* **Producer retries**: configure retry count/backoff; enable **idempotent producer** to avoid duplicates on retry.
* **Consumer retries**: Kafka doesn’t natively retry consumer processing.

    * Pattern: **main topic** → on failure, send to **retry topic** (include attempt count) → if still failing after N
      attempts, send to **dead-letter queue (DLQ)** for inspection.

```mermaid
flowchart LR
    A[Consume from main topic] -->|process ok| B[Commit offset]
    A -->|process fails| R[Publish to retry topic (+attempt)]
R -->|retry consumer|A2[Reprocess]
A2 -->|>N fails| D[DLQ (no consumers)]
```

---

## Performance Optimizations (Throughput/Latency)

* **Batching** on the producer: send multiple records per request (limits by **batch size** and **linger time**).
* **Compression** on the producer (e.g., **gzip**): fewer bytes over the wire → higher throughput.
* **Partitioning for parallelism**: the **biggest lever**—spread uniformly across partitions/brokers.
* **Record size guidance**: keep messages **< \~1 MB** for performance (avoid giant payloads).

    * **Anti-pattern**: placing **media blobs** directly on Kafka.
    * **Preferred**: store blob in **S3** and put a **pointer (URL)** + metadata in the Kafka record.

---

## Retention Policies (Storage & Cost)

* Per-topic settings; log segments are purged by **time** or **size** (whichever comes first):

    * **`retention.ms`**: default **7 days**.
    * **`retention.bytes`**: default **\~1 GB** (speaker says “1 GB”).
* If you need longer **replay** windows (weeks/months), **raise retention** and **call out storage/cost impact**.

---

## Rough Capacity Numbers (Back-of-the-Envelope)

* **Per broker** (hardware dependent; for interview math):

    * \~**1 TB** storable data.
    * \~**10,000 msgs/sec** throughput.
* If your estimated load is below this, you can explicitly state **a single broker suffices**; otherwise **add brokers**
  and **increase partitions** (plus a good partition key).

---

## Managed Kafka Notes

* **Managed services** (e.g., **Confluent Cloud**, **AWS MSK**) automate broker scaling/ops.
* Even with managed Kafka, **you still must choose** an effective **partition key** and **topic design**.

---

## End-to-End Architecture (At a Glance)

```mermaid
graph LR
    subgraph Producers
        P1[Web/App Servers]
        P2[Services]
    end

    subgraph Kafka Cluster
        T1((Topic A)):::topic
        T2((Topic B)):::topic
        classDef topic fill: #eef, stroke: #99f, stroke-width: 1px;
    end

subgraph Consumers
CG1[Consumer Group 1<br/>(Workers)]
CG2[Consumer Group 2<br/>(Analytics)]
end

P1 --> T1
P2 --> T1
P2 --> T2
T1 --> CG1
T1 --> CG2
T2 --> CG1
```

---

## Interview-Ready Talking Points & Trade-offs

* **Partition key choice**: what guarantees you need (**ordering**) and how to avoid **hot partitions**.
* **Consumer offset commit timing**: **at-least-once** vs **at-most-once** behavior; when to commit to balance
  duplicates vs loss.
* **Durability vs throughput**: **`acks=all`** & higher **replication factor** increase durability but reduce
  throughput.
* **Retry strategy**: explain **retry topic + DLQ** pattern for consumer failures.
* **Payload design**: **pointer vs blob**; keep records small (<\~1 MB).
* **Retention**: justify **retention.ms/bytes** based on **replay needs** vs **storage cost**.
* **Scalability path**: start single broker → add brokers → increase partitions → revisit keys → compound
  keys/backpressure if needed.

---

## Quick Glossary (1-liners)

* **Topic**: named stream; logical grouping of messages.
* **Partition**: ordered, append-only log backing a topic; unit of parallelism.
* **Broker**: server hosting partitions.
* **Consumer Group**: consumers sharing work so each message is processed once per group.
* **Offset**: position of a record in a partition.
* **DLQ (Dead-Letter Queue)**: holding area for messages that repeatedly fail processing.
* **Backpressure**: producers deliberately slow down when downstream is saturated.

---

## Open Notes (from the talk)

* Hashing noted as “I believe Murmur” → for interviews, you can say **“hash(key) mod partitions”** (hash family
  implementation is not the key point).
* Defaults mentioned: **replication factor = 3**, **retention.ms = 7 days**, **retention.bytes ≈ 1 GB**.

---

*Use these notes to confidently (1) decide when Kafka belongs in your design, and (2) go deep on partitions, groups,
durability, retries, and performance when probed.*
