The outbox pattern is a design pattern used to reliably publish events or messages from a service that also writes to a database, solving the "dual write problem."

**The problem it solves**

Imagine your service needs to do two things in one operation: update a database (e.g., create an order) and publish a message to a message broker (e.g., notify other services via Kafka or RabbitMQ). If you do these as separate operations, you can end up in inconsistent states:

- Database write succeeds, but message publish fails → other services never hear about the change
- Message publishes, but database write fails or rolls back → other services act on something that didn't happen

You can't wrap both in a single transaction because the database and message broker are separate systems.

**How the outbox pattern works**

Instead of publishing directly to the message broker, you write the message to an "outbox" table in the same database, in the same transaction as your business data:

1. In one atomic database transaction, insert the business record (e.g., the order) AND insert a row into the `outbox` table describing the event to publish.
2. Either both succeed or both fail — no inconsistency possible.
3. A separate process (a relay or dispatcher) reads new rows from the outbox table and publishes them to the message broker.
4. Once successfully published, the row is marked as processed (or deleted).

**How the relay reads the outbox**

Two common approaches:

- *Polling:* The relay periodically queries for unprocessed outbox rows. Simple, but adds latency and database load.
- *Change Data Capture (CDC):* Tools like Debezium tail the database's transaction log and stream outbox inserts to the broker. More efficient and lower latency.

**Important guarantee**

The pattern provides at-least-once delivery, not exactly-once. The relay might crash after publishing but before marking the row processed, leading to duplicates. Consumers should be idempotent — typically by including a unique event ID and deduplicating on the consumer side.

This pattern is widely used in microservices architectures where reliable event-driven communication matters.