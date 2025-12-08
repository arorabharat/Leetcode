## Design FB News Feed

[![Alt text](https://img.youtube.com/vi/Qj4-GruzyDU/0.jpg)](https://www.youtube.com/watch?v=Qj4-GruzyDU)

This video explains how to design a Facebook News Feed system using the "Hello Interview delivery framework" for system design interviews.

The process covers:

Requirements (2:57): Defining functional needs (like creating posts, following users, viewing feeds with pagination) and non-functional requirements (such as eventual consistency, latency, and scale for billions of users).
Core Entities (5:53): Identifying the main "nouns" in the system: User, Post, and Follow relationship.
API Design (6:34): Creating the necessary API endpoints for the functional requirements, including POST /posts, PUT /users/{ID}/followers, and GET /feed with pagination.
High-Level Design (9:22): Building a basic, non-optimized system to meet functional requirements. This involves a Post Service and Follow Service (using DynamoDB for storage) and a Feed Service that queries these tables to fetch posts from followed users. The video also introduces the concept of Global Secondary Indexes (GSIs) for efficient data retrieval (11:51, 14:46).
Deep Dives (16:19): Optimizing the design for scalability and addressing bottlenecks. Key optimizations include:
Fanout on Write / Precomputed Feed (19:51): Storing feed content for users when a post is created, using an async worker pool to handle the writes.
Hybrid Approach (20:51): Combining precomputation for highly followed accounts with real-time fetching for others to balance read/write loads.
Hotkey/Hot Shard Problem (22:06): Addressing issues with a single post getting too many requests by using a distributed cache with multiple instances.


### Facebook post search

[![Alt text](https://img.youtube.com/vi/l38XL9914fs/0.jpg)](https://www.youtube.com/watch?v=l38XL9914fs)

This video breaks down how to design a system for Facebook post search, following the "Hello Interview Delivery framework" (0:49).

The video covers:

Requirements (1:20): Clarifying functional (e.g., create posts, like posts, search by keyword, sort by recency/likes) and non-functional (e.g., low read latency like 500ms, eventual consistency for writes within 1 minute, high recall, high availability) requirements.
Estimates (1:30): Calculating read and write throughput (identifying it as a write-heavy system) and storage needs (3.6 petabytes for posts).
Core Entities & API Design (19:02): Defining key components like users, posts, and likes, and designing APIs for creating posts, liking/unliking, and searching.
High-Level Design (22:52): Proposing a simple system with a post service, like service, and a search service. It introduces the inverted index (27:45) as a core data structure for efficient keyword searches and discusses how to handle sorting by time or likes using sorted sets (33:50).
Deep Dives / Optimizations (37:10): Addressing non-functional requirements by:
Implementing caching (38:06), including CDN (40:53) for read throughput.
Using Kafka (43:53) for async processing to handle high write volume and bursts.
Optimizing like write volume using logarithm of likes (49:05).
Sharding Redis with keyword as a partitioning key (52:58).
Handling storage requirements by separating hot and cold data using S3 (58:32) and leveraging Count-Min Sketch (59:44) to identify cold data.
Extending search to handle multi-keyword queries (1:02:07) using biograms and Count-Min Sketch to filter uncommon ones.

What is CDC ?
The video mentions CDC, or Change Data Capture (29:59).
It explains that CDC is a way for a service, like the ingestion service, to read changes (like new posts or updates) directly from a database (29:59). This means that whenever a post is created or changed in the database, the ingestion service automatically knows about it, eliminating the need for other services to directly tell the search index about these changes (30:14).
The video also notes that if you tag Elastic Search as a CDC on your database, it will be eventually consistent with the data you're writing (1:10:59).

What is inverted index ?
An inverted index is described in the video as an ideal data structure for fast searches (27:45).
It works by taking the content of a post, breaking it into individual keywords (a process called tokenization), and then associating those keywords with the post IDs that contain them (28:23).
The "ideal data structure" for an inverted index would, for a given keyword, have a list of all the post IDs that match that keyword (28:09). This allows the system to return search results very quickly by looking up the keyword and getting the corresponding post IDs (28:35).

## Ad click aggregator

[![Alt text](https://img.youtube.com/vi/Zcv_899yqhI/0.jpg)](https://www.youtube.com/watch?v=Zcv_899yqhI)

This video walks through designing an ad click aggregator system, a common system design interview question. The presenter outlines a roadmap for infrastructure design questions, starting with defining functional and non-functional requirements (2:35).

Key takeaways:

Functional Requirements [(5:00)](https://www.youtube.com/watch?v=Zcv_899yqhI): Users click ads and are redirected; advertisers can query click metrics over time (e.g., clicks per hour or minute).
Non-functional Requirements (6:58): Scalability (10,000 ad clicks per second peak), low latency analytics queries (less than 1 second), fault tolerance, high data integrity (no lost clicks), real-time data (within 1 minute granularity), and idempotency of ad clicks (one click per ad impression per user).
System Interface & Data Flow (1:49): The system takes click data and advertiser queries as input, and outputs redirections and aggregated click metrics. The data flow involves receiving clicks, redirecting users, validating and logging clicks, and then aggregating them.
High-Level Design (14:12): The initial design involves a Click Processor Service, an Ad DB, and a Query Service to store and retrieve raw click data in a write-optimized database like Cassandra.
Deep Dives (29:39):
Real-time Analytics: To achieve real-time data, the design shifts from batch processing (Spark jobs) to stream processing using a Click Event Stream (Kinesis/Kafka) and a stream aggregator (Flink) for real-time aggregation and flushing (31:04).
Scalability: Services are scaled horizontally, streams are sharded (e.g., by ad ID), and Flink jobs scale to read from multiple shards (3:57). The "celebrity problem" for hot shards is addressed by adding a number to the shard key (39:11).
Fault Tolerance & Data Integrity: Retention policies on the stream (7-day retention) prevent data loss if Flink goes down (4:24), and periodic reconciliation using batch processing (Spark dumping raw events to S3) ensures high data accuracy (45:24).
Idempotency of Ad Clicks: To prevent users/bots from spamming clicks, the system generates an "ad impression ID" on the server. When a user clicks, this ID is sent along with a signed impression ID. The Click Processor Service verifies the signature and checks a Redis cache to ensure the impression ID hasn't been clicked before (53:05).




