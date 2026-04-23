It is a great observation. If Consistent Hashing is the "industry standard" for big players like DynamoDB and Cassandra, why did Salvatore Sanfilippo (the creator of Redis) explicitly avoid it?

The answer lies in **Control vs. Chaos**. Consistent Hashing is built for **Elasticity** (nodes coming and going constantly), while Redis Cluster is built for **Predictability and Atomicity**.

Here are the three main reasons why Redis chose **Hash Slots** over Consistent Hashing:

---

### 1. The "Multi-Key" Problem (Atomicity)
In a booking system, you often need to update multiple pieces of data at once (e.g., updating a `User` profile and a `Booking` record in one go).
* **In Consistent Hashing:** If you add a node to the ring, the "boundary" shifts. Two keys that used to be on the same server might suddenly be split across two different servers. This breaks atomic transactions.
* **In Redis (Hash Slots):** A slot is a "hard container." All keys in Slot 500 stay together until an engineer manually moves the *entire slot*. This allows for **Hash Tags** (e.g., `{user123}:profile` and `{user123}:booking`), ensuring related data **always** lands on the same physical node, even after a cluster rebalance.



---

### 2. Predictability of Data Movement
Consistent Hashing is "probabilistic." When you add a node, the math says *about* $1/N$ of the keys will move, but you don't have fine-grained control over exactly *which* ones.
* **The "Churn" Issue:** In Consistent Hashing, data movement is triggered automatically by the topology change. If your network is already stressed, this "auto-rebalancing" can actually crash the cluster (the "Cascading Failure" effect).
* **The Redis "Resharding" Philosophy:** Redis ignores the ring and says: *"I have 16,384 buckets. I will only move Bucket #500 when you tell me to."* This makes it a **manual/orchestrated** process. You can move data at 3:00 AM when traffic is low, rather than having the system decide to move data in the middle of a Taylor Swift ticket sale.

---

### 3. Avoiding the "Imbalanced Ring"
In a simple Consistent Hashing ring, you can get "clumping" where one node ends up with 30% more data than its neighbor just by chance. To fix this, you have to add **Virtual Nodes**, which adds significant complexity to the client-side code.
* **The Slot Advantage:** Because 16,384 is a fixed, manageable number, a human or a simple script can look at the cluster and say, *"Node A is at 90% memory, Node B is at 20%. I will move exactly 1,000 slots from A to B."*
* You get **perfectly balanced** shards because you can assign slots based on actual memory usage, not just a random hash function.

---

### Summary Comparison for your Design

| Feature | Consistent Hashing (Dynamo) | Hash Slots (Redis) |
| :--- | :--- | :--- |
| **Why use it?** | For "Infinite Scale" where nodes join/leave daily. | For "Controlled Scale" where you want atomic operations. |
| **Who moves data?** | The Hash Function (Automatic). | The Administrator (Orchestrated). |
| **Key Grouping** | Difficult/Impossible. | Easy (via Hash Tags). |
| **Complexity** | High (Virtual nodes, ring math). | Low (Simple table of 16,384 entries). |

**The Bottom Line:**
Redis avoided Consistent Hashing because it wanted to give the developer **deterministic control**. In a booking system, "deterministic" is better than "probabilistic." You want to know exactly which server has the Bali villa's calendar, and you don't want that calendar moving to a different server just because a background health check failed for 2 seconds.

Does this help explain why "automatic" isn't always "better" in high-stakes system design?