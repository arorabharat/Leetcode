


[![Alt text](https://img.youtube.com/vi/1NngTUYPdpI/0.jpg)](https://www.youtube.com/watch?v=1NngTUYPdpI)


This video provides a comprehensive overview of caching in system design interviews. It defines caching as temporary storage for faster data retrieval, highlighting the significant speed difference between accessing data from memory (cache) versus disk (database) (1:17).

The video then covers different caching layers:

External Caching (2:06): A dedicated caching service like Redis, shared by multiple application servers.
In-Process Caching (3:20): Caching data directly within the application server's memory, offering the fastest access but without shared consistency.
CDNs (Content Delivery Networks) (4:51): Geographically distributed servers that cache content closer to users to reduce network latency.
Client-Side Caching (6:51): Storing data directly on the user's device (browser or app) to avoid network calls.
It also explains common cache architectures:

Cache Aside (9:00): The application checks the cache first, falling back to the database on a miss and updating the cache. This is the most common pattern.
Write Through (10:01): The application writes to the cache, which then synchronously writes to the database.
Write Back (Write Behind) (12:05): The cache writes to the database asynchronously.
Read Through (13:14): The cache handles the database lookup on a miss, acting as a proxy.
The video details cache eviction policies for managing limited memory:

Least Recently Used (LRU) (15:36)
Least Frequently Used (LFU) (16:00)
First In, First Out (FIFO) (16:25)
Time To Live (TTL) (16:42)
Finally, it discusses common caching issues like:

Cache Stampede / Thundering Herd (18:00): Many requests hitting the database when a popular cache entry expires.
Cache Consistency (20:10): Cache and database holding different values for the same data.
Hot Keys (23:00): A single cache entry receiving disproportionately high traffic.
It concludes with advice on when and how to discuss caching in system design interviews, emphasizing justification, describing behavior, and addressing potential downsides (25:00).