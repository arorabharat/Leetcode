
---

# Feature Click Metrics System – High-Level Design

## Goal

Collect feature/button click events from clients and provide aggregated metrics over time (hourly, daily, weekly) for Product Managers.

---

## 1. Client-Side Event Collection

Each button click emits an event to the backend.

**Attributes sent from client:**

* `feature_id` / `button_id` – what was clicked
* `user_id` (or anonymous/session ID) – who clicked
* `client_timestamp` – optional, for debugging only
* `page/screen` – context
* `device/browser/app_version` – optional metadata

⚠️ **Do not trust client timestamps** for analytics. They are unreliable.

---

## 2. Event Ingestion Layer

**Flow:**

```
Client → API / Ingestion Service → Kafka
```

**Responsibilities:**

* Validate event schema
* Add **server-side timestamp** (source of truth)
* Enrich event (e.g., app version, region if needed)
* Publish event to Kafka topic

**Why Kafka?**

* Handles bursty traffic
* Decouples producers from consumers
* Enables reprocessing and multiple consumers

---

## 3. Stream Processing & Aggregation

A **Kafka consumer / stream processor** reads events and aggregates them.

**Aggregation dimensions:**

* Time bucket (hour/day/week)
* Feature or button ID
* Optional: app version, platform, experiment ID

**Example aggregation:**

```
(feature_id, day) → click_count
```

**Processing options:**

* Streaming (near real-time dashboards)
* Micro-batching (simpler, cheaper)

---

## 4. Storage Layer (Database)

Two logical storage layers are common:

### A. Raw Events Store (Optional but Recommended)

* Used for backfills, audits, new metrics
* Stored in cheap, append-only storage

### B. Aggregated Metrics Store (Primary)

Stores precomputed counts for fast queries.

**Schema example:**

```
date | feature_id | click_count
```

**Scaling strategies:**

* Time-based partitioning
* Sharding by feature or time
* Read replicas for dashboard queries

**Good fits:**

* Time-series DB
* Data warehouse
* Partitioned relational DB (for moderate scale)

---

## 5. Analytics & Dashboard Layer

Product Managers query **aggregated tables only**.

**Capabilities:**

* Daily / weekly trends
* Feature comparison
* Simple filters (date range, feature)

**Why pre-aggregation matters:**

* Fast dashboards
* Predictable query cost
* Avoids scanning billions of raw events

---

## 6. Key Design Principles

* **Server timestamp is the source of truth**
* **Never query raw events directly from dashboards**
* **Kafka enables replay and evolution**
* **Aggregation trades storage for speed (a good trade)**

---

## Mental Model (One Line)

> *Collect raw truth once, aggregate it many ways, and never make Product Managers wait.*

This design scales cleanly from “MVP with a few buttons” to “thousands of features and billions of clicks” without changing the fundamentals.
