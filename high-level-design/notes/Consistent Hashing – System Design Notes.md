# Consistent Hashing – System Design Notes

## Introduction

* **Consistent hashing** is a strategy to distribute data across multiple servers while minimizing **redistribution**
  when servers are added or removed.
* Commonly comes up in **system design interviews**, especially for components like **distributed caches, databases,
  message queues, or CDNs**.

---

## Problem with Simple Hashing + Modulo

* Example: Events website (like Ticketmaster). Initially:

    * Single DB → all events stored there.
    * As scale grows → multiple DBs needed.

* **Naive approach**:

    * Hash `eventId` → get large number.
    * Apply **modulo (# of DBs)** → decide storage DB.

* **Problem**:

    * Adding/removing a DB changes the modulo value.
    * Causes **mass redistribution** of data (almost all keys move).
    * Leads to **high database activity**, downtime, or crashes.
    * Example: moving from `mod 3 → mod 4` forces nearly all events to shift DBs.

---

## Consistent Hashing – How It Works

### Step 1: Hash Ring

* Represent key space as a **ring** (0 → 2³², simplified as 0–100).
* Place servers (DBs) on the ring at specific points.

    * Example: DB1 at 0, DB2 at 25, DB3 at 50, DB4 at 75.

### Step 2: Hash Keys

* Hash the object ID (e.g., `eventId`) → point on the ring.
* Move **clockwise** to find nearest server.

    * Example: `eventId = 2 → hash = 16` → goes to DB2.

### Step 3: Adding/Removing Servers

* When adding DB5 at position 90:

    * Only keys between 75–90 move to DB5.
    * **No mass redistribution** → only local movement.
* Removing a server:

    * Only keys in its segment are reassigned to the next clockwise server.

---

## Virtual Nodes (VNodes)

* Problem: If one server is removed, its entire segment may move to a single neighbor → imbalance.
* **Solution**: Assign each server multiple positions on the ring (virtual nodes).

    * Example: DB1 at 0, 20, 40, 60, 80.
    * DB2 at 5, 25, 45, 65, 85.
* Benefits:

    * Even distribution of load.
    * Smoother rebalancing when servers are added/removed.

---

## Visualization

```mermaid
flowchart TD
    subgraph Ring[Consistent Hashing Ring]
        A[Key = hash(eventId)]
        DB1[DB1 @0,20,40...]
        DB2[DB2 @25,45,65...]
        DB3[DB3 @50,70,90...]
        DB4[DB4 @75,95,15...]
    end

    A -->|Clockwise lookup| DB2
```

---

## Key Benefits

* **Scalability**: Easy to add/remove nodes with minimal movement.
* **Fault tolerance**: If a server fails, only its range of keys is redistributed.
* **Load balancing**: Virtual nodes prevent hotspots.

---

## When It’s Used (Real-World Systems)

* **Distributed caches**: e.g., **Redis Cluster, Memcached**.
* **Databases**: **Cassandra, DynamoDB**.
* **CDNs**: distributing content across edge servers.
* **Message queues**: sharding topics or partitions.

---

## When to Mention in Interviews

* If designing:

    * **Distributed cache** (e.g., Redis/Memcached).
    * **Distributed database** (Cassandra, DynamoDB).
    * **Message queue** (Kafka-like system).
* If asked:

    * **How do you minimize data movement when scaling servers?**
    * **How do CDNs or distributed systems achieve fault-tolerant partitioning?**

---

✅ **Key Terms to Remember**:

* **Consistent hashing**: partitioning data with minimal redistribution.
* **Hash ring**: circular space mapping of keys and servers.
* **Virtual nodes (VNodes)**: multiple points per server for better balancing.
* **Redistribution**: movement of keys when servers are added/removed.

---

Would you like me to also create a **comparison table** (Simple Hashing vs Consistent Hashing) for quick interview
recall?
