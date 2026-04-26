Great question. Distributed systems have a rich catalog of patterns, each solving a specific class of problems. Here's a tour of the most important ones, grouped by the problem they address.

## Data consistency & messaging patterns

**Saga pattern** — Manages distributed transactions across services without using two-phase commit. A business operation is broken into a sequence of local transactions, each with a compensating action to undo it if a later step fails. Two flavors: *choreography* (services react to each other's events) and *orchestration* (a central coordinator drives the steps).

**Transactional inbox** — The mirror of the outbox, applied on the consumer side. Incoming messages are first written to an "inbox" table within the same transaction that processes them, enabling deduplication and idempotent handling.

**Event sourcing** — Instead of storing the current state, store the full sequence of state-changing events. Current state is derived by replaying events. Provides a complete audit log and natural time-travel debugging, but adds complexity around schema evolution and snapshots.

**CQRS (Command Query Responsibility Segregation)** — Separates the write model (commands) from the read model (queries), often with different data stores optimized for each. Frequently paired with event sourcing.

## Service communication patterns

**API Gateway** — A single entry point for clients that routes requests to backend services, handling cross-cutting concerns like authentication, rate limiting, and request aggregation.

**Backend for Frontend (BFF)** — A specialized API gateway tailored to a specific client type (mobile, web, IoT), shaping responses to that client's needs rather than forcing one generic API to serve all.

**Service mesh** — Infrastructure layer (Istio, Linkerd) that handles service-to-service communication concerns — load balancing, encryption, observability, retries — outside application code via sidecar proxies.

**Service discovery** — Mechanism for services to find each other dynamically (via registries like Consul, Eureka, or Kubernetes DNS), since instances come and go in elastic environments.

## Resilience & fault tolerance patterns

**Circuit breaker** — Wraps calls to a remote service. After repeated failures, it "opens" and short-circuits further calls for a cooldown period, preventing cascading failures and giving the downstream service time to recover.

**Bulkhead** — Isolates resources (thread pools, connection pools) per dependency so that one failing or slow downstream service can't exhaust resources used by others. Named after the watertight compartments in ships.

**Retry with exponential backoff** — Automatically retry failed calls with increasing delays, often with jitter to prevent thundering-herd effects when many clients retry simultaneously.

**Timeout** — Always bound how long you'll wait for a response. Sounds obvious, but missing timeouts are a common cause of cascading failures.

**Rate limiting and throttling** — Cap the number of requests a service accepts to protect itself from being overwhelmed.

**Fallback** — Provide a degraded but functional response when a dependency fails (e.g., return cached data, default values, or a simpler computation).

## Deployment & scaling patterns

**Sidecar** — Deploy a helper container alongside your service to handle peripheral concerns (logging, config, proxying). The sidecar shares the lifecycle of the main container.

**Strangler fig** — Gradually replace a monolith by routing specific functionality to new services over time, until eventually the old system can be retired. Named after the strangler fig vine that grows around and eventually replaces a host tree.

**Blue-green deployment** — Run two identical environments; deploy to the inactive one, test, then switch traffic over. Enables instant rollback.

**Canary release** — Roll out changes to a small subset of users first, monitor, then gradually expand if healthy.

## Observability patterns

**Distributed tracing** — Propagate a trace ID through every service call so you can reconstruct the full path of a request across services (OpenTelemetry, Jaeger, Zipkin).

**Centralized logging** — Aggregate logs from all services into one searchable store (ELK stack, Loki) since logs spread across dozens of containers are useless.

**Health check API** — Each service exposes an endpoint reporting its health, used by orchestrators and load balancers to route traffic away from unhealthy instances.

## Coordination patterns

**Leader election** — When multiple instances of a service run but only one should perform a task (like running a scheduled job), they elect a leader using systems like ZooKeeper, etcd, or Raft consensus.

**Idempotency keys** — Clients send a unique key with each request so the server can detect and safely ignore duplicates — essential when retries are in play.

**Distributed lock** — Coordinate exclusive access to a shared resource across services using something like Redis Redlock or ZooKeeper.

---

If you'd like, I can go deeper on any of these — saga and circuit breaker in particular have a lot of interesting nuance worth exploring.