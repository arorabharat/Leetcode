This is a classic question about the **Statistical Properties** of hashing. In theory, the $CRC16$ function is designed for uniform distribution, but in the real world, "hotspots" can and do happen.

Here is how the balance is maintained and why your scenario of an 80/10/10 split is rare but technically possible.

---

## 1. The Math of Uniform Distribution
The $CRC16$ (Cyclic Redundancy Check) function is a mathematical algorithm that treats your input string (the key) as a large polynomial and divides it.

* **High Entropy:** Even a tiny change in the input (e.g., `listing_1` vs `listing_2`) results in a vastly different hash value.
* **Uniform Mapping:** Because the output of the hash is essentially "pseudo-random," the keys are spread across the 16,384 slots with near-perfect equality.

If you have 1 million **randomly named** keys, the laws of probability ensure that each of your 3 Master nodes will handle roughly 33.3% of the keys.



---

## 2. When would M1 get 80% of the traffic?
Even if the **keys** are distributed 33/33/33, the **traffic** can be skewed. Your 80/10/10 scenario happens due to three real-world factors:

### A. The "Hot Key" Problem
You might have 1,000 listings on $M1$ and 1,000 on $M2$. However, if $M1$ contains the "Most Popular Villa in Bali" and it's currently being featured on the front page of Airbnb, that **one single key** could generate 80% of your total system traffic. Redis handles the keys evenly, but it cannot control user behavior.

### B. Misuse of Hash Tags
As we discussed, using `{hashtag}:key` forces keys into the same slot.
* **The Mistake:** If a developer decides to put all bookings for a specific city into one hash tag like `{Paris}:booking_1`, `{Paris}:booking_2`, etc.
* **The Result:** Every single booking for Paris will land on the **exact same shard**. If Paris is your busiest market, $M1$ will catch fire while $M2$ and $M3$ stay idle.

### C. Low Cardinality
If you only have 10 total listings in your entire database, probability doesn't have enough "room" to work. By pure chance, 8 of those 10 listings might hash to slots owned by $M1$.

---

## 3. How to detect and fix the 80/10/10 split
In a production environment, you don't just "hope" the distribution is even. You monitor it.

1.  **Monitor Memory & Ops/sec:** You check the `info replication` or `info stats` command on each node. If $M1$ is doing 80k ops/sec and $M2$ is doing 10k, you have a skew.
2.  **The "Big Key" Scan:** Use `redis-cli --bigkeys` to see if $M1$ is holding a few massive sets or lists that are eating up all the resources.
3.  **Manual Resharding:** If $M1$ is naturally "hotter" because it holds more active listings, you can **rebalance** the cluster. You don't have to have 5,461 slots on each node. You could move 2,000 slots from $M1$ to $M2$ to even out the load.



---

## 4. Summary for the "Interview"
If an interviewer asks: *"Is the distribution always equal?"*

**Your Answer:** *"Mathematically, $CRC16$ provides a **uniform distribution of keys** across the 16,384 slots. However, **traffic distribution** can be skewed by 'Hot Keys' or 'Hash Tagging' where a few keys receive the majority of requests. We mitigate this by monitoring 'Ops per second' per shard and using manual resharding or application-level caching to offload the hot shards."*

Does the difference between **key distribution** (where they live) and **traffic distribution** (how often they are hit) help explain why $M1$ might get overwhelmed?