How would you design a system to collect feature button click counts and show aggregated metrics over time for product managers? – Focus on large-scale data collection, real-time vs batch processing, scalable storage (e.g. time-series database), and efficient aggregation (e.g. MapReduce or streaming).

How would you design a distributed search index system (inverted index) like Elasticsearch? – Expect discussion of indexing pipelines, sharding/partitioning the index, replication for availability, query routing, and balancing consistency vs latency.

How would you design a high-level system to detect malicious or spam URLs on the platform? – Look for an architecture that ingests URL content, uses heuristics or ML models to score links, and handles high throughput; also error handling and updating the model.

How would you design a job/task scheduler (e.g. with task dependencies and deadlines)? – The interviewer will look for handling dependencies, timing (Cron-like or DAG scheduling), distributed execution, fault tolerance (retries, monitoring) and scalability (queuing, worker design).

How would you design a key-value storage system to support high throughput? – Test for partitioning strategy (e.g. consistent hashing), in-memory vs disk storage, replication, caching, and trade-offs around consistency, availability, and durability.

How would you design a system to compute and serve the top N most-shared or most-engaged articles? – Expect handling streaming event data, maintaining counters (like a leaderboard), efficient ranking (e.g. using heaps or streaming algorithms), and scalable updates.

How would you design a calendar or scheduling system (e.g. meeting scheduler)? – Look for handling time zones, recurring events, conflict detection, notifications, and scaling to many users with fast lookup of events by time/date.

How would you design a metrics aggregation and visualization system (dashboard) for real-time analytics? – Focus on collecting metrics (counters, logs) from services, using a time-series database or analytics engine, and providing query interfaces or dashboards; discuss real-time vs batch tradeoffs.

How would you design a system to track user engagement metrics across devices (web, mobile, etc.)? – Expect linking identities across devices, data ingestion from different sources, maintaining user profiles and session aggregation, and ensuring data consistency and low-latency queries.

How would you design a fault-tolerant data pipeline to handle delayed or missing data? – The interviewer is likely looking for designs involving buffering (message queues), idempotent processing, data reconciliation (handling out-of-order events), and storage that tolerates late-arriving data (e.g. watermarking in stream processing)

