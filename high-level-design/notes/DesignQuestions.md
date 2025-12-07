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

