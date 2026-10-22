# Reliability in System Design

Reliability is a system's ability to keep behaving correctly and consistently over time, including when components fail, inputs are unexpected, or traffic spikes. In production, a reliable system does more than stay online: it protects data, avoids incorrect or duplicate operations, limits the impact of failures, and recovers safely.

## Reliability in practice

| Failure or risk | Reliability practices |
|---|---|
| A service crashes or becomes unhealthy | Health checks, redundancy, automatic restart, and failover |
| A database or downstream service is temporarily unavailable | Timeouts, bounded retries with backoff, circuit breakers, and graceful degradation |
| A message is delivered more than once | Idempotent consumers and deduplication |
| An event is lost during processing | Durable messaging, acknowledgements, retries, and dead-letter handling |
| One failing dependency overloads other services | Bulkheads, load shedding, and circuit breakers |
| A deployment introduces a regression | Progressive or canary rollout, monitoring, and rollback |
| A request or background job produces bad or incomplete results | Input validation, consistency checks, time limits, and recoverable processing |

These techniques address different failure modes and should be chosen to match the system's requirements. Retries, for example, need limits and backoff; otherwise they can amplify an outage. Idempotency is important whenever a retry could repeat a side effect, such as charging a payment.

## Reliability, performance, and scalability

These qualities are related but measure different things:

| Quality | Main question | Example |
|---|---|---|
| Performance | How quickly does the system respond? | Reduce payment API latency from 300 ms to 100 ms |
| Scalability | Can the system handle growth in traffic or data? | Increase capacity from 1,000 to 10,000 requests per second |
| Reliability | Does it continue to produce correct outcomes despite failures? | A retry does not charge a customer twice, and a temporary outage does not lose a payment |
| Engineering productivity | Can engineers build, test, deploy, and debug the system efficiently? | Automate schema compatibility checks before event changes are released |

A system can be fast and scalable but unreliable if it loses data or returns incorrect results under failure. Reliability is about dependable outcomes, not merely uptime.

## Example: schema governance for Kafka events

Suppose multiple teams publish JSON events to Kafka. A producer changes an event schema incompatibly, causing downstream consumers to fail. A schema governance platform can infer and version schemas and validate compatibility before changes are published.

This improves reliability by catching incompatible changes earlier, reducing the risk of consumer failures and invalid processing, and making event contracts safer to evolve. In an interview, describe the measured outcome when you have evidence. Otherwise, say that the change reduced risk rather than claiming it prevented production incidents.

For example:

> "I improved the reliability of our event-driven ecosystem by adding schema versioning and automated compatibility validation, reducing the risk that incompatible changes would break downstream consumers."

## Measuring reliability

Choose measurements that reflect the system's user-facing promises and failure modes:

| Metric | What it tells you |
|---|---|
| Availability and SLO compliance | How often the service meets its operational target |
| Error rate | How frequently requests fail or return unsuccessful outcomes |
| MTTR | How long recovery takes after a failure |
| MTBF | How much time typically passes between failures |
| Data loss or duplication rate | Whether events or operations are lost or repeated |
| Incident frequency | How often production-impacting failures occur |

Reliability work is strongest when connected to a meaningful before-and-after measure. A useful interview test is whether the change made the system less likely to fail, lose data, produce incorrect results, or require manual recovery.
