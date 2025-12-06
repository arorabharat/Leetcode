This is how I'd prepare for a system design interview if I were starting from scratch.

The first thing I would do is build a solid base with just the 6 core concepts which form the foundation of any scalable system:

1. Storage: Data models, ACID vs BASE, appropriate use cases
2. Scalability: Vertical vs horizontal, sharding, handling hotspots
3. Networking: HTTP, TCP/UDP, API design principles
4. Performance: Latency numbers, throughput calculations
5. Fault Tolerance: Replication strategies, graceful recovery
6. CAP Theorem: Consistency vs availability trade-offs

Then, System design is largely just a combination of components. So the next thing I would do is learn these 7 components including what they do, and when to use them.

With these 7, you can conquer almost any question:

1. Server: The compute layer that services requests and executes business logic
2. Database: The persistent storage layer with various paradigms for different needs
3. Cache: The speed layer that reduces database load and improves response times
4. Message Queue: The asynchronous communication layer that decouples services
5. Load Balancer: The traffic distribution layer for improved availability and scale
6. Blob Storage: The unstructured data layer for files, images, and binary data
7. CDN: The edge delivery layer that reduces latency and backend load

With the foundation in place, I would work from the top down, understanding how the top 10 most common questions are answered in this order:

1. URL Shortener (Bitly)
2. Dropbox
3. Ticketmaster
4. News Feed
5. WhatsApp
6. LeetCode
7. Uber
8. Web Crawler
9. Ad Click Aggregator
10. Facebook's Post Search

(You can find detailed answer key to each of these here: https://lnkd.in/gRrt72Bq)

Once I get through the first few, I'd start trying them myself before reading/watching any solution. This part is key! Passive learning only gets you so far.

Each time you encounter a concept you don't know, dig deeper until it clicks.

By the time you've solved 5-6 problems, the patterns will emerge and what once seemed overwhelming transforms into an intuitive framework you can apply to any problem. Just like with LeetCode.

Read more about my recommendation for getting up to speed in system design quickly in our latest newsletter. https://lnkd.in/gfNfTRsg