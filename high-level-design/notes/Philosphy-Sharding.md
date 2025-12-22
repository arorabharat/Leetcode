[![Alt text](https://img.youtube.com/vi/L521gizea4s/0.jpg)](https://www.youtube.com/watch?v=L521gizea4s)

This video provides a comprehensive explanation of sharding in the context of system design interviews. It covers what sharding is, why it's necessary, different ways to implement it, the challenges it presents, and how to effectively discuss it in an interview setting.

Key takeaways from the video include:

Motivation for Sharding (0:33-2:25): Sharding becomes necessary when a single database can no longer handle the load due to high write/read volumes or large storage requirements, even after vertical scaling (upgrading to bigger hardware).
What is Sharding? (2:25-3:00): Sharding is the process of splitting data across multiple machines, with each shard acting as its own standalone database holding a subset of the total data. This allows for horizontal scaling of storage and throughput.
How to Shard (3:35-15:18):
Choosing a Shard Key (3:40-8:38): The shard key is the field used to group data. A good shard key should have high cardinality (many unique values), allow for even distribution of data, and align with common query patterns. Examples of good shard keys include user ID for a social media app or order ID for an e-commerce site. Bad shard keys include is_premium (low cardinality) or creation date (uneven distribution and hotspots for recent data).
Distribution Strategies (8:40-15:18):
Range-Based Sharding (8:49-10:21): Data is split into simple ranges based on the shard key (e.g., user IDs 0-10M on Shard 1). While intuitive, it can lead to uneven distribution and hotspots if data growth isn't uniform.
Hash-Based Sharding (10:24-13:10): The industry default. A hash of the shard key is taken and then modulo the number of shards to determine placement. This provides even distribution but can lead to massive data reshuffling when adding or removing shards. Consistent hashing (11:50-12:38) is introduced as a solution to this rebalancing problem, placing both keys and shards on a virtual ring to minimize data movement during scaling.
Directory-Based Sharding (13:11-15:18): Uses a lookup table to store which shard each record belongs to, offering maximum flexibility for rebalancing and handling hot users. However, it introduces extra latency due to an additional lookup step and creates a single point of failure if the directory goes down. The video advises against using this in interviews as a primary solution.
Challenges of Sharding (16:04-26:37):
Hotspots/Load Imbalance (16:16-18:47): Even with good shard keys, some shards can become overloaded (e.g., "celebrity problem" with Messi on Shard 1). Solutions include using compound shard keys (17:01-17:46) to further distribute data or having dedicated celebrity shards (17:51-18:47) for high-traffic users, using a directory-based lookup.
Cross-Shard Operations (18:48-22:50): Queries that require data from multiple shards become expensive as they need to "fan out" to all relevant shards, aggregate data, and then return the result. Solutions include:
Choosing a good shard key (19:52-19:57) that aligns with most common queries.
Caching (20:08-21:01) the results of expensive cross-shard queries.
Denormalizing data (21:04-21:59) so related information lives together on a single shard, at the expense of more complex writes.
Consistency (22:32-26:37): Maintaining atomicity for transactions that span multiple shards is complex.
Two-Phase Commit (2PC) (23:46-24:21) is a textbook solution but is often slow and fragile in practice.
The Saga Pattern (24:58-26:30) is a more commonly used approach in production, involving a sequence of smaller, independent operations, each with a compensating action in case of failure.
How to Discuss Sharding in System Design Interviews (26:37-29:58):
Justify the Need (27:03-28:22): Prove that sharding is necessary by showing that storage, write throughput, or read throughput limits of a single database are being exceeded (e.g., "500 million users with 5 KB data each is 2.5 TB, a single PostgreSQL instance won't handle that"). Be aware that sometimes sharding might not be necessary, and it's impressive to show that understanding.
Propose a Shard Key (28:31-28:48): Based on your access patterns (e.g., "sharding by user ID for a social media app as most queries are user-centric").
Choose a Distribution Strategy (28:49-29:10): Default to hash-based sharding with consistent hashing.
Call Out Trade-offs (29:11-29:30): Discuss the challenges introduced, such as expensive global queries, and propose solutions like caching.
Address Growth Handling (29:34-29:46): Explain how you'll start with a certain number of shards and how consistent hashing will facilitate adding more shards as needed.

1. Assume you opened a post office , where people are coming to courier.
2. You hired one person who take the courier, initial 5 people ere coming per second.
3. Now you post office grew and 10 people are coming per second, even the most efficient person on earth is not able to take 10 courier per second.
4. So you decided to hire one more person, this is point where you have sharded, i.e. split the traffic into two queue.
5. Now sharding should be done in a way where all the data for a user live on same physical machine , ie with same courier guy so he can answer all the queries instantly.
6. 
