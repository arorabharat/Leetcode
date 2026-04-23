This is a subtle point that often confuses people in interviews. The short answer is: **No, Redis Cluster does not use Consistent Hashing** in the traditional sense (like DynamoDB or Cassandra). It uses **Algorithmic Sharding** via Hash Slots.

Here is the breakdown of why that distinction matters when a Master goes down.

---

## 1. Consistent Hashing vs. Redis Hash Slots
In **Consistent Hashing**, keys are mapped to a circle (ring), and nodes are also placed on that ring. If a node leaves, its keys "slide" to the next available node on the ring.

In **Redis Cluster**, the mapping is fixed:
* There are **16,384 slots**.
* The mapping of `Key -> Slot` is **constant** (it never changes).
* The mapping of `Slot -> Node` only changes when an administrator or a script explicitly triggers a reshard.

### What happens when a Master goes down?
If Master-A (owning slots 0–5000) crashes:
1.  **No Redistribution:** Redis does **not** move slots 0–5000 to Master-B or Master-C. If it did that automatically, it would cause a massive "shuffling" of data across the network, which is dangerous during a failure.
2.  **Replica Promotion:** Instead, the Cluster promotes Master-A’s **Replica** to become the new Master.
3.  **Slot Ownership Stays Put:** The new Master (formerly the replica) now owns slots 0–5000. To the rest of the cluster and the clients, the "map" hasn't changed; the IP address for those slots has just been updated.



---

## 2. Why "No Redistribution" is a Feature, Not a Bug
In a booking system, redistribution during a failure would be a disaster. Imagine if a node goes down and the system immediately starts moving terabytes of data to other nodes while you are trying to handle a surge in bookings.

By using **Replicas** instead of **Redistribution**:
* **Failover is fast:** It takes a few seconds to promote a replica.
* **Zero Data Movement:** No keys are actually moved across the network during failover.
* **Predictability:** You always know which node is responsible for "Listing_123."

---

## 3. What if a Master AND all its Replicas go down?
This is the "Nightmare Scenario." If Master-A and its only Replica both die:
* **Partial Outage:** The slots 0–5000 become "orphan slots."
* **Cluster Health:** By default, the entire cluster will stop accepting writes because it can no longer guarantee the integrity of the 16,384 slots (though you can configure `cluster-require-full-coverage no` to keep the other shards alive).
* **Manual Recovery:** You would have to bring the nodes back online or manually reassign those slots to other masters (which would mean data loss for those specific slots).

---

## 4. Summary: The Failover Flow

| Event | Action | Key Location |
| :--- | :--- | :--- |
| **Normal Ops** | Master-A handles Slot 1. | Master-A |
| **Master-A Crashes** | Replica-A is elected as new Master. | Replica-A (now Master) |
| **Resharding (Manual)** | Admin moves Slot 1 to Master-B. | Master-B |

### The "DSA" Perspective:
If an interviewer asks: *"Why not just use Consistent Hashing so the keys automatically move to Master-B if Master-A fails?"*

**Your Answer:** *"Consistent Hashing is great for stateless caches, but for a database like Redis, automatic redistribution is too heavy. Redis prefers **high availability via replication**. We keep the slot-to-node mapping stable and just swap the 'Leader' (Master) of that slot when a failure occurs. This keeps the system predictable and avoids network congestion during a crisis."*

Does this explain why the "Read Replica" is the hero of the story when a Master fails, rather than a redistribution algorithm?