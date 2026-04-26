# Techniques to Scale Reads in System Design

Scaling reads is usually easier than scaling writes because reads can be duplicated, cached, and served from many places without coordination. Here are the main techniques, roughly ordered from simplest to most involved.

**1. Caching**
The single most impactful technique. Put frequently accessed data in fast, in-memory stores like Redis or Memcached so you avoid hitting the database. Common patterns include cache-aside (app checks cache first, falls back to DB, then populates cache), read-through (cache fetches from DB on miss), and write-through (writes go to cache and DB together). You can layer caches: browser → CDN → application cache → distributed cache → database.

**2. CDNs and edge caching**
For static assets and even semi-dynamic content, push data geographically closer to users. CloudFront, Cloudflare, and Fastly cache responses at edge locations, dramatically reducing latency and origin load.

**3. Read replicas**
Most databases (Postgres, MySQL, MongoDB) support primary-replica replication where writes go to the primary and reads can be served from many replicas. You scale horizontally by adding replicas behind a load balancer. The catch is replication lag — replicas are eventually consistent, so reads-after-writes may return stale data.

**4. Database indexing and query optimization**
Before scaling out, scale up your queries. Proper indexes, denormalization, materialized views, and query rewrites can turn a 2-second query into a 5ms query and eliminate the need for more infrastructure.

**5. Sharding (horizontal partitioning)**
Split data across multiple database instances by some key (user ID, region, tenant). Each shard handles a subset of reads and writes. This scales both reads and writes but adds significant complexity around routing, rebalancing, and cross-shard queries.

**6. Denormalization and precomputation**
Instead of computing results on every read, precompute and store them. Materialized views, summary tables, and denormalized documents (one document per read pattern) trade storage and write complexity for fast reads. This is the core idea behind CQRS — separate read and write models optimized for their specific use cases.

**7. Search-optimized stores**
Offload complex queries to systems built for them. Elasticsearch or OpenSearch handle full-text search and aggregations far better than a relational database. Replicate data into them via change data capture (CDC) tools like Debezium.

**8. Asynchronous and streaming pipelines**
Use Kafka, Kinesis, or similar to fan out writes to multiple read-optimized stores, each tuned for a specific access pattern (a graph DB for relationships, a key-value store for lookups, a search index for queries).

**9. Load balancing and connection pooling**
Distribute read traffic across replicas with smart load balancers, and use connection poolers like PgBouncer to handle high concurrency without exhausting database connections.

**10. Request coalescing and batching**
When many clients request the same data simultaneously, coalesce them into a single backend call (singleflight pattern). For high-throughput scenarios, batch reads to reduce round trips.

**11. Pagination, projections, and pushdown**
Don't return more than you need. Cursor-based pagination, sparse fieldsets (GraphQL-style projections), and pushing filters into the database all reduce the work each read does.

**12. Tiered storage**
Hot data in memory or SSD-backed databases, warm data in standard databases, cold data in object storage or data lakes. Reads hit the appropriate tier based on access patterns.

A practical approach: start with indexing and caching, add a CDN, then read replicas, and only reach for sharding, CQRS, or specialized stores when those have been exhausted. Most systems get very far on the first three.

Want me to go deeper on any of these — say, cache invalidation strategies, replication lag handling, or designing a CQRS pipeline?