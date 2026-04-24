
---

### 3. The "In-Process" Queue (Movie Tickets & Flash Sales)
For extreme concurrency (like a blockbuster movie opening), systems often use **Request Buffering**.

* Instead of letting thousands of requests hit the "Lock" logic at once, they are pushed into a high-speed stream (like **Redis Streams** or **Kafka**).
* A pool of workers processes these requests sequentially per "Screen" or "Hall."
* This converts a chaotic "race" into a neat "line," which is much easier for a database to handle without locking up.



---
