## Assume you are making a booking management system where people come and book accommodation like Airbnb, and your job is to introduce a feature where people can view the most popular bookings based on, like the number of bookings in the last, let's say, a week, or number of view counts, or number of search results. So how would you create flow-level design of such a feature? To start with, will you have this as a counter? So let's say you're incrementing a counter of number of bookings. What if that counter write fails? Will you fail the booking as well, or will you do a blocking call to do the write? How will you handle these scenarios?

Yes — this is a classic **“core transaction + derived analytics”** design problem. The most important design decision is:

> **Popularity should never be on the critical path of booking.**

I would separate **booking correctness** from **popularity counting**.

### 1. High-level flow

```text
                    ┌───────────────┐
User ──► Booking API│ Booking Service│
                    └───────┬───────┘
                            │
                     DB Transaction
                            │
                   ┌────────▼────────┐
                   │ Booking DB       │
                   │ reservation      │
                   │ inventory        │
                   └─────────────────┘
                            │
                       Outbox Event
                            │
                   ┌────────▼────────┐
                   │ Message Queue    │
                   │ Kafka / SQS      │
                   └────────┬─────────┘
                            │
                    ┌───────▼────────┐
                    │ Popularity      │
                    │ Counter Service  │
                    └───────┬────────┘
                            │
                  ┌─────────▼─────────┐
                  │ Redis / Aggregates │
                  └───────────────────┘
```

The key is that **the booking DB transaction does not depend on Redis/Kafka/counter-service availability.**

---

## 2. What happens when a booking is made?

Suppose Airbnb listing `A123` is booked.

The Booking Service does:

```text
BEGIN TRANSACTION

1. Check availability
2. Lock/update inventory
3. Create booking
4. Create outbox event:
   BOOKING_CREATED {
       bookingId: B123,
       listingId: A123
   }

COMMIT
```

Then asynchronously:

```text
Outbox → Kafka → Popularity Consumer → counter
```

So your counter might eventually become:

```text
listing:A123:bookings:2026-10-05 → 17
```

---

# 3. Why not increment the counter synchronously?

You could do:

```text
Booking API
    │
    ├── DB booking
    │
    └── Redis INCR popularity
```

But now you have a problem.

Suppose:

```text
DB booking succeeds
Redis INCR fails
```

What do you do?

### Option A — Fail booking

That's terrible.

The user successfully booked an accommodation, but you tell them:

> "Booking failed."

Why?

Because an **analytics counter failed**.

You don't want a non-critical system determining whether money/inventory transactions succeed.

### Option B — Ignore Redis failure

Better, but now you have:

```text
Booking = successful
Popularity counter = missed
```

You have an inaccurate counter.

For popularity, that's generally acceptable temporarily.

But we can do better.

---

# 4. Use an Outbox Pattern

This is where the **transactional outbox** becomes very useful.

Instead of:

```text
DB → Redis
```

do:

```text
DB transaction
    │
    ├── Booking
    │
    └── Outbox Event
```

Both are committed atomically.

For example:

```sql
BEGIN;

INSERT INTO bookings
    (booking_id, listing_id, user_id)
VALUES
    ('B123', 'A123', 'U456');

INSERT INTO outbox
    (event_id, event_type, payload)
VALUES
    ('E123', 'BOOKING_CREATED',
     '{"bookingId":"B123","listingId":"A123"}');

COMMIT;
```

Now you have a very important guarantee:

> **If the booking exists, the event eventually exists.**

Even if Kafka is completely down.

---

# 5. What if Kafka is down?

Suppose:

```text
Booking DB = UP
Kafka = DOWN
```

Booking still succeeds.

The outbox contains:

```text
E123 → BOOKING_CREATED
```

A background publisher keeps retrying:

```text
Outbox Publisher

E123 → Kafka ❌
E123 → Kafka ❌
E123 → Kafka ❌
E123 → Kafka ✅
```

Then:

```text
Kafka → Popularity Consumer → Redis
```

Eventually:

```text
booking count = correct
```

So you've converted:

> **temporary infrastructure failure**

into:

> **eventual consistency**

which is perfectly reasonable for popularity.

---

# 6. But now there's another interesting problem

Suppose Kafka delivers the event twice.

```text
E123 → Kafka
E123 → Kafka
```

Your consumer receives:

```text
BOOKING_CREATED
BOOKING_CREATED
```

If you blindly do:

```redis
INCR listing:A123:bookings
```

you get:

```text
count = 2
```

when the actual number of bookings is:

```text
count = 1
```

Therefore, the consumer needs to be **idempotent**.

For example:

```text
eventId = E123
```

Consumer maintains:

```text
processed_events
```

Before processing:

```text
if E123 already processed:
    ignore
else:
    increment counter
    mark E123 processed
```

Or use an atomic Redis operation/Lua script to make:

```text
check event
+
increment counter
+
mark processed
```

atomic.

---

# 7. Now your "last 7 days" requirement

This is another important design decision.

Don't just maintain:

```text
listing:A123:totalBookings
```

because you need:

> bookings in the **last 7 days**

You need a time dimension.

One simple approach:

```text
listing:A123:bookings:2026-10-01 → 10
listing:A123:bookings:2026-10-02 → 15
listing:A123:bookings:2026-10-03 → 8
...
```

Then:

```text
last 7 days popularity
=
sum of today's + previous 6 days
```

You can maintain this in Redis using sorted sets, hashes, or time buckets depending on your query pattern.

For example:

```text
ZSET popularity

score = booking count
member = listingId
```

But for a sliding window, I'd generally prefer **time buckets + periodic aggregation** rather than trying to make one global counter represent everything.

---

# 8. Views and searches are slightly different

You mentioned:

- bookings
- views
- search appearances

I would treat them as **different events**.

```text
BOOKING_CREATED
LISTING_VIEWED
SEARCH_RESULT_SHOWN
```

Then potentially calculate:

```text
Popularity Score =
    0.6 × bookings
  + 0.3 × views
  + 0.1 × searches
```

You can change the weights without changing the booking system.

For example:

```text
Listing A
Bookings = 100
Views    = 10,000
Searches = 20,000

Popularity = f(bookings, views, searches)
```

This becomes an **analytics/ranking system**, rather than a booking-system concern.

---

# 9. What about views?

Views can be extremely high volume.

Imagine:

```text
1 million views/sec
```

You don't want:

```text
1 million synchronous DB writes/sec
```

So I'd use:

```text
Client
  ↓
Listing API
  ↓
Event
  ↓
Kafka
  ↓
Stream processor
  ↓
Redis / ClickHouse / Cassandra / etc.
```

And aggregate:

```text
listing A
    1
    1
    1
    1
    ...
```

into batches:

```text
listing A → +10,432 views
```

rather than writing every individual view to a relational database.

---

# 10. The key distinction I'd explain in an interview

There are three different categories of data:

### A. Source-of-truth data

```text
Booking
Payment
Inventory
```

Needs strong correctness.

Failure may need to fail the operation.

### B. Derived operational data

```text
Popularity
View counts
Search counts
Recommendations
```

Can usually be eventually consistent.

Failure should **not** fail booking.

### C. Analytics data

```text
Daily bookings
Weekly views
Conversion rate
Trending listings
```

Can be processed asynchronously and potentially with some delay.

---

# 11. What if the counter itself fails?

Suppose:

```text
Booking = successful
Kafka = successful
Popularity Consumer = successful
Redis = DOWN
```

Consumer should **not lose the event**.

Kafka gives you the ability to retry:

```text
Kafka
  ↓
Consumer
  ↓
Redis ❌
  ↓
don't acknowledge message
  ↓
retry
```

Eventually:

```text
Redis comes back
    ↓
event processed
    ↓
counter updated
    ↓
ACK
```

So the fundamental reliability chain becomes:

```text
Booking DB
    ↓
Transactional Outbox
    ↓
Kafka
    ↓
Consumer
    ↓
Counter Store
```

Each stage has its own durability/retry mechanism.

---

# 12. One subtle issue: booking cancellation

This is something I'd expect an interviewer to probe.

Suppose:

```text
Booking created → +1 popularity
```

Then user cancels:

```text
Booking cancelled → -1 popularity
```

So events should be modeled as:

```text
BOOKING_CREATED
BOOKING_CANCELLED
```

Then:

```text
popularity
=
created bookings
-
cancelled bookings
```

Or, depending on the business definition, you might count **completed bookings** rather than reservations.

This is an important requirement clarification.

---

# 13. What I'd say in an interview

I'd give this answer:

> "I wouldn't make the popularity counter part of the synchronous booking transaction. Booking and inventory are source-of-truth operations and need strong consistency, whereas popularity is derived data and can be eventually consistent.
>
> I'd use a transactional outbox. When the booking transaction commits, I atomically persist both the booking and a BOOKING_CREATED event in the outbox. A background publisher sends that event to Kafka. A popularity consumer consumes it and updates a Redis/time-bucketed counter.
>
> If Redis or Kafka is down, the booking still succeeds because the booking transaction doesn't depend on them. The event remains in the outbox/Kafka and is retried until processed.
>
> Since Kafka provides at-least-once delivery, the popularity consumer must be idempotent, typically using an event ID or another deduplication mechanism.
>
> For last-seven-day popularity, I'd maintain time-bucketed counters, such as daily or hourly buckets, and aggregate the relevant seven-day window. Views and search impressions would follow a similar asynchronous pipeline, but because they're much higher volume, I'd aggregate them in batches."

That is the **core flow-level design** I'd expect for this problem.

Explore the design details

- Walk through failure and retry cases
- Design the 7-day popularity data model