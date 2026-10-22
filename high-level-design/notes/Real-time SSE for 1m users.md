# Real-time SSE for 1m users

Archived chat transcript. Messages are preserved as they appeared in the session.

Session: `37d80d79-9f19-409a-8f5d-17f86ef086c9`

## User

Assume you are to manage the SSE for multiple users lets say 1 million user to provide update on the HLD.

lets you are making a todo list app which allow sharing the todo list and if someone udpate the task, then other should be able to get the update instantly

## Assistant

# HLD: Real-time Shared Todo Lists over SSE (1M concurrent users)

## 1. Requirements
- **Functional:** create/share lists, edit tasks, collaborators see changes in near real time (<1s).
- **Non-functional:** 1M concurrent SSE connections, high availability, at-least-once delivery, resumable after disconnect, horizontal scalability.

## 2. Capacity estimate
- 1M connections at ~10–20K per node → **~60–100 SSE gateway nodes**, given a 64 GB node with tuned FDs and epoll.
- Writes: assume 1M users × 1 edit/min peak ≈ ~17K writes/s. Fan-out is ~3–5 collaborators per list on average, so ~50–100K events/s outbound.
- Per-connection memory is ~10–30 KB, so ~20–30 GB across the fleet. This is trivial.

## 3. Architecture

```
Client ──HTTPS──► L7 LB (HTTP/2, no buffering, long timeouts)
                    │
         ┌──────────┴───────────┐
         ▼                      ▼
   API Service (REST)     SSE Gateway (stateful connections)
     │  write                   ▲ subscribe
     ▼                          │
  DB (Postgres/Dynamo)    Pub/Sub backbone (Redis Cluster / Kafka / NATS)
     │ outbox / CDC ───────────►│
```

**Components**
1. **API service (stateless):** validates auth and permissions, writes the task to the DB with a per-list monotonic `version`, and writes an event to an **outbox** in the same transaction.
2. **Event bus:** a CDC/outbox relay publishes to Kafka, partitioned by `listId`. This keeps per-list ordering. Redis Pub/Sub or NATS is a lower-latency alternative if loss is tolerable, because clients can resync.
3. **SSE gateway:** holds client connections. Each node keeps an in-memory map `listId → {connections}`. It subscribes to the bus only for lists that have local listeners. Subscribing is ref-counted, with unsubscribe on last disconnect.
4. **Presence/routing registry (optional):** Redis maps `listId → gateway nodes`. This allows targeted delivery instead of broadcasting every event to every node.
5. **Auth service:** JWT validated at connect. Share/ACL changes publish a revoke event that makes the gateway drop affected subscriptions.

## 4. Flow
1. Client calls `GET /stream?lists=...` with `Authorization` and `Last-Event-ID`.
2. Gateway authenticates, registers the connection, and subscribes to the topics for the user's lists.
3. If `Last-Event-ID` is present, the gateway replays missed events from a short-lived log (Redis Streams or Kafka offsets, retained 5–15 min). If the gap is too large, it sends a `resync` event and the client refetches via REST.
4. User A edits a task: REST `PATCH` → DB + outbox → bus → the gateways holding User B's connection → `event: task.updated, id: <listId:version>` → User B's UI.
5. The writer's own client dedupes by `version` or client mutation ID.

## 5. Scaling and reliability concerns
- **Connection distribution:** the LB uses least-connections. Gateways are stateless apart from their sockets, so scale them behind autoscaling on connection count.
- **Thundering herd on deploy or crash:** clients reconnect with exponential backoff plus jitter. Rolling deploys drain nodes gradually, and the gateway sends a `retry:` hint and closes gracefully.
- **Hot lists:** a list with 100K viewers produces large fan-out. Mitigate with batching or coalescing of events (50–100 ms) and per-node fan-out, so the bus carries one message per node rather than one per user.
- **Backpressure:** each connection has a bounded send buffer. A slow client is dropped and forced to resync.
- **Heartbeats:** send `: ping` every 15–30 s to keep proxies from closing idle connections and to detect dead peers.
- **HTTP/1.1 limit:** browsers cap connections at ~6 per domain, so use HTTP/2, or multiplex all lists over a **single stream per client**, which is what is shown above.
- **Ordering and idempotency:** per-list version numbers; clients ignore stale or duplicate events. Conflicts use last-writer-wins per field, or CRDT/OT if you need collaborative text editing.
- **Multi-region:** regional gateways with a global event bus (Kafka MirrorMaker, or a DB with replication). Users connect to the nearest region.
- **Security:** short-lived tokens (pass them in a header via fetch-based SSE polyfill, since native `EventSource` can't set headers), per-list ACL checks on subscribe, and rate limits per user.
- **Observability:** metrics for connection count, event latency (write → delivery), reconnect rate, dropped slow clients, and replay hit rate.

## 6. Data model (sketch)
- `lists(id, owner, version)`
- `list_members(list_id, user_id, role)`
- `tasks(id, list_id, title, done, version, updated_at)`
- `outbox(id, list_id, version, type, payload, created_at)`

## 7. Alternatives
- **WebSockets:** needed for high-frequency bidirectional traffic. SSE is simpler here because updates flow server→client and writes go over REST. It also gets auto-reconnect and `Last-Event-ID` for free.
- **Long polling / push notifications:** fallback for mobile in the background, where FCM/APNs replaces SSE.

## 8. Summary
Stateless REST writes → transactional outbox → partitioned event bus → ref-counted subscriptions on a fleet of ~100 SSE gateways → per-list versioned events, with replay via `Last-Event-ID` and a REST resync fallback. This gives sub-second updates, ordered per list, and graceful recovery at 1M connections.

## User

User A edits a task: REST `PATCH` → DB + outbox → bus → the gateways holding User B's connection → `event: task.updated, id: <listId:version>` → User B's UI.

Tell me how this event bus works , will there be hashing or Q in between

## Assistant

# Event bus: how it works

**Yes to both: a queue/log in the middle, and hashing for partitioning.**

## 1. The bus is a partitioned log (Kafka-style), not a plain queue
- Topic `todo-events` with e.g. 256 partitions.
- **Hashing:** `partition = hash(listId) % N`. All events for one list land in one partition, so **per-list ordering** is guaranteed. Across lists there is no ordering, and none is needed.
- The log is durable, with retention of 15 min–1 hr. That makes replay possible after a gateway crash or a client reconnect.

## 2. Producer side
Outbox table → relay (Debezium CDC or a poller) → publishes `{listId, version, type, payload}` keyed by `listId`. The outbox gives atomicity: no DB commit without an event, and no event without a commit.

## 3. Consumer side: the key design choice
Each gateway needs only events for lists that its connected users are viewing. Two options:

**A. Every gateway consumes everything (broadcast).**
- Each gateway reads all partitions with a unique consumer group (no sharing).
- Cheap to build. It drops events with no local subscribers.
- At ~100K events/s × 100 gateways, that is 10M msgs/s of wasted reads. It works at moderate scale but doesn't scale well.

**B. Routed delivery (recommended at 1M).**
- Redis (or the gateway registry) keeps `listId → {gatewayIds}`, updated via ref-counted subscribe and unsubscribe.
- A **fan-out/router tier** consumes Kafka (consumer group, each partition owned by one router, so ordering is preserved). It looks up the gateways for the list and pushes **one message per gateway** through a per-gateway channel (NATS subject `gw.<id>`, Redis Pub/Sub, or gRPC stream).
- The gateway then fans out locally from its `listId → connections` map.

Simpler variant: use **NATS/Redis Pub/Sub with subject = `list.<listId>`**. Gateways subscribe only to lists they have listeners for, and the broker does the routing. It is lower latency, but has no durable log, so you pair it with a Redis Stream or the DB for replay.

## 4. Per-gateway queue
Each connection has a **bounded in-memory queue** (e.g. 256 events). If a client is slow and the queue overflows, the gateway drops it and the client reconnects with `Last-Event-ID` or does a REST resync. This is the backpressure mechanism.

## 5. Delivery semantics
- At-least-once on the bus; the client dedupes via `version`.
- Gaps in `version` (e.g. 5 → 7) trigger a REST fetch of the missing events.

## Recommendation
Kafka (hash-partitioned by `listId`) for durability and ordering → router tier → per-gateway channels (NATS) → local fan-out. For a smaller system, skip Kafka and use Redis Pub/Sub or NATS with `list.<id>` subjects, plus Redis Streams for replay.

## User

exaplin in more details this approach - Routed delivery (recommended at 1M).

and in simpler style

## Assistant

# Routed delivery, in simple terms

## The problem it solves
You have ~100 gateway servers. Each one holds connections for different users. When a task changes in list **L1**, only the gateways with someone watching L1 need the event. Sending it to all 100 is wasteful.

Think of it like a **postal system**: you don't deliver every letter to every house. You look up the address first.

## The pieces

1. **Kafka (the log):** stores every event, partitioned by `hash(listId)`. It keeps ordering and durability.
2. **Routing table (Redis):** a map of `listId → which gateways have viewers`.
   ```
   L1 → {gw-3, gw-17}
   L2 → {gw-3}
   L9 → {gw-42, gw-55, gw-60}
   ```
3. **Router tier:** a small group of workers that read from Kafka and do the lookup.
4. **Gateways:** hold the SSE connections and keep their own local map `listId → [connections]`.

## Step by step

**When a user connects (setup):**
1. Bob opens the app and connects to `gw-17`. He views lists L1 and L5.
2. `gw-17` adds him to its local map: `L1 → [Bob]`.
3. If he is the first local viewer of L1, the gateway tells Redis: `SADD routes:L1 gw-17`.
4. When the last viewer of L1 leaves, it runs `SREM routes:L1 gw-17`.

This is called **ref-counting**: register on the first viewer, unregister on the last.

**When Alice edits a task in L1 (delivery):**
1. The API writes to the DB and the outbox, and an event goes to Kafka keyed by `L1`.
2. Hashing puts it in, say, partition 42. **One router owns partition 42**, so L1's events are processed in order.
3. The router asks Redis: "who watches L1?" → `{gw-3, gw-17}`.
4. The router sends **one message to each of those gateways** (not one per user), over a NATS subject like `gw.17`, or a gRPC stream.
5. `gw-17` looks at its local map and writes the event to every SSE connection watching L1. Bob sees it.

```
Alice PATCH → API → DB+outbox → Kafka(L1→partition 42)
                                     ↓
                               Router (owns p42)
                                     ↓ lookup routes:L1
                          ┌──────────┴──────────┐
                          ▼                     ▼
                       gw-3                  gw-17
                    local fan-out         local fan-out
                     (users)               (Bob, ...)
```

## Why this works well
- **Little waste:** an event goes only to the 2 gateways that need it, not all 100.
- **Ordering stays intact:** one partition → one router → one gateway channel → one connection.
- **Two-level fan-out:** the router fans out to gateways (a few), and the gateways fan out to users (many). A list with 100K viewers across 80 gateways costs 80 messages from the router, not 100K.
- **Independent scaling:** add routers for event volume, and add gateways for connection count.

## Things to watch
- **Stale routes:** if a gateway crashes, its Redis entries linger. Fix with a **TTL** on entries plus a heartbeat refresh, so they self-clean. A message sent to a dead gateway is lost, but the client reconnects and replays via `Last-Event-ID`.
- **Race at subscribe time:** an event can arrive between a user connecting and the route registration. Fix by registering the route **first**, then replaying from the last seen version.
- **Router restart:** Kafka consumer-group rebalance hands partitions to another router, which resumes from the committed offset (at-least-once, so the client dedupes by `version`).
- **Extra hop:** adds ~a few ms latency, which is worth it at this scale.

## When not to use it
Under ~10K connections or ~10 gateways, broadcast (every gateway reads everything) is simpler and fine. Routed delivery earns its complexity when waste would otherwise grow with `events × gateways`.
