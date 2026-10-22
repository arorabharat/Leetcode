# HLD and System Design Topic Index

A quick map of the high-level design material in this repository. The notes range from interview fundamentals and distributed-system building blocks to end-to-end system designs.

## HLD process and interview preparation

| Topic | Covered in |
|---|---|
| Requirements discovery, functional/non-functional requirements, constraints, and design workflow | [Fundamentals of HLD](FundamentalsOfHld.md), [HLD template](HLD%20Template.md) |
| Interview delivery framework and quick learning approach | [Hello Interview](hello-interview.md), [Quick learning trick](quickLearningTrick.md) |
| Capacity, storage, and throughput estimation | [Estimation framework](EstimationFramework.md), [Estimation solutions](solutions/estimations.md) |
| Reusable design patterns and common interview topics | [HLD design patterns](solutions/hld-design-pattern.md), [Design questions](DesignQuestions.md), [Small topics](SmallTopics.md), [Design improvements](improvements/what-can-you%20improve.md) |

## Architecture and service building blocks

| Topic | Covered in |
|---|---|
| API gateway responsibilities, architecture, caching, and design variants | [API Gateway](API%20Gateway.md), [API Gateway summary](API%20Gateway%20-%20Summary.md), [API Gateway caching](API%20Gateway%20Caching.md), [API Gateway diagram](API%20Gateway%20d2.md), [API Gateway v2](API%20gateway%20v2.md) |
| Load balancing, reverse/forward proxies, and traffic routing | [Load balancer](Load%20Balancer.md), [Load balancing summary](Load%20Balancing%20-%20Summary.md), [Proxy servers](Proxy%20Servers.md), [Proxy summary](Proxy%20-%20Summary.md) |
| Three-tier architecture and REST API design | [Three-tier architecture](ThreeTierArchitecture%20-%20Summary.md), [REST API best practices](REST%20API%20Best%20Practices.md) |
| Caching and distributed caching | [Caching](Caching.md), [API Gateway caching](API%20Gateway%20Caching.md), [Design questions](DesignQuestions.md) |
| Rate limiting, throttling, and Redis rate-limiter patterns | [Rate limiter](Rate%20limiter.md), [Throttling](Throttling%20-%20Summary.md), [Redis rate limiter](definitions/redis-rate-limiter.md) |
| Server-sent events (SSE), polling, WebSockets, and webhooks | [Polling, SSE, WebSocket, and Webhooks](Polling%2C%20SSE%2C%20WebSocket%2C%20and%20Webhooks.md), [Practical SSE](Practical-SSE.md), [Real-time SSE at one million users](Real-time%20SSE%20for%201m%20users.md) |

## Distributed systems, data, and scalability

| Topic | Covered in |
|---|---|
| CAP theorem and consistency models, including eventual consistency | [CAP theorem](CAP%20Theorem%20%E2%80%94%20What%20to%20Say%20in%20System%20Design%20Interviews%20.md), [Eventual consistency](Eventual%20Consistency%20in%20Distributed%20Systems.md) |
| Sharding, partitioning, consistent hashing, and data distribution | [Sharding philosophy](Philosphy-Sharding.md), [Consistent hashing](Consistent%20Hashing%20%E2%80%93%20System%20Design%20Notes.md), [Consistent-hashing trade-offs](trade-offs/consistent-hashing-algorithm-sharding.md), [Redis equal distribution](definitions/redis-equal-distribution.md), [Redis hash tags](definitions/redis-hash-tags.md) |
| Scaling reads/writes, fan-out, hotspots, and buffering | [Scaling reads](solutions/scaling-reads.md), [Controlling read distribution](tackle/controlling-read-distribution.md), [Controlling write distribution](tackle/controlling-write-dsitribution.md), [Request buffering](tackle/request-bufferring.md), [Hot partitions](../Fundamental-Design/HotPartition.md), [Write hotspots](../Fundamental-Design/NoWriteHotspot.md), [Scaling writes](../Fundamental-Design/ScalingWrites.md), [Fan-out and related topics](SmallTopics.md) |
| Tail latency and latency-aware design | [Latency summary](Latency%20-%20Summary.md), [Tail latency](../Fundamental-Design/TailLatency.md) |
| Distributed coordination, locks, and concurrency control | [ZooKeeper](Zookeeper%20-%20Summary.md), [Distributed locks](tackle/distributed-lock.md), [Redis distributed lock](definitions/redis-distributed-lock.md), [Strict locking](tackle/strict-lock.md), [Pessimistic and optimistic locking](pesimistic-optimistic-locking.md), [ABA problem](distributed-system-issues/aba-problem.md) |
| Failure handling and resilience | [Chaos and failure scenarios](distributed-system-issues/chaos.md), [Monitoring system](monitoring-system/Readme.md) |
| Messaging, event streaming, CDC, and reliable event publication | [Kafka](kafka.md), [Kinesis](kinesis.md), [Message queue vs. event streaming](solutions/message-queue-vs-event-streaming.md), [Outbox pattern](solutions/outbox-pattern.md), [Outbox example](../../design-pattern/src/hld/outbox.md) |
| Batch and stream processing | [MapReduce](Map%20Reduce%20-%20Summary.md), [Lambda architecture](Lambda%20-%20Summary.md), [Click-stream aggregation](ClickStreamAggregation/CSM.md) |
| Object and document storage, uploads, and pre-signed URLs | [Object storage](Object%20Storage.md), [Pre-signed URLs](Pre-Signed%20URLs.md), [Simple document store](../Fundamental-Design/simple-document-store.md) |
| Identifier generation and UUID trade-offs | [Central ID generator](../Fundamental-Design/CentralIDGenerator.md), [Incremental ID generator](../Fundamental-Design/IdGenerator-incremental.md), [UUIDs](../Fundamental-Design/uuid.md) |
| Redis fundamentals and application patterns | [Redis internals](definitions/redis-working.md), [Redis distributed locks](definitions/redis-distributed-lock.md), [Redis rate limiter](definitions/redis-rate-limiter.md), [Redis hash tags](definitions/redis-hash-tags.md), [Redis equal distribution](definitions/redis-equal-distribution.md) |

## End-to-end system designs

| System / topic | Covered in |
|---|---|
| Social feed, social search, and ad-click aggregation | [Design questions](DesignQuestions.md) |
| URL shortener | [HLD URL shortener](../tiny-url.md), [System-design URL shortener](../../system-design/src/URL-Shortner.md) |
| Instagram | [Instagram design](../../system-design/src/instagram.md) |
| Uber / ride-hailing backend | [Uber notes](Uber.md), [System-design Uber backend](../../system-design/src/uber-backened.md) |
| WhatsApp messaging | [WhatsApp design](solutions/whatsapp.md) |
| Web crawler | [Web crawler design](Web%20crawler%20high-level%20design.md) |
| Elevator system | [Elevator design](../../system-design/src/elevator-problem.md) |
| High-concurrency connections | [Million concurrent connections](../../system-design/src/million-concurrent-connection.md) |
| Hierarchical tree API and scale-up options | [Tree API design](Hierarchical%20tree%20API%20design.md), [Tree API scale-ups](Hierarchical%20tree%20API%20scale-ups.md) |
| Shared to-do API | [Shared todo API design](Shared%20todo%20API%20design.md) |
| Tagging service | [Atlassian tagging service](Atlassian%20tagging%20service%20design.md) |
| Client-side telemetry, metric collection, and monitoring | [Client metrics service](CSMTool.md), [Click-stream aggregation](ClickStreamAggregation/CSM.md), [Monitoring system](monitoring-system/Readme.md) |
| Card-on-file tokenization for recurring payments | [Tokenization HLD](../../behaviour-round/card-on-file-tokenisation-HLD-style.md) |

## Other HLD-related reference

| Topic | Covered in |
|---|---|
| Fundamental interview concepts and terminology | [System-design key parameters](../../system-design/src/key-parameter.md) |
| Operational/project context for payment-method changes | [Payment-method project notes](../../behaviour-round/amazon-office-work/update-payment-method.md) |
| LinkedIn-related design note | [Fundamental design notes](../Fundamental-Design/linkedin-post.md) |
