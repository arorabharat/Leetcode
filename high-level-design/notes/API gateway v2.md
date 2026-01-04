Below are **clean, structured Markdown notes** capturing *everything we discussed*, distilled into an interview-ready + production-ready mental model.
No fluff, no cloud-marketing fog—just the mechanics.

---

# API Gateway — Practical Notes (Latency, Threads, State, Global Deployment)

---

## 1. Basic Request Flow

```
Client → API Gateway → Monolith → API Gateway → Client
```

* The API Gateway acts as a **reverse proxy**
* **No redirect** is involved
* Client never talks to the monolith directly
* The response always flows **back through the gateway**

**Key point**

> Gateways proxy responses; they do not redirect clients.

---

## 2. Latency Impact of Adding an API Gateway

### Typical Added Latency

* **1–3 ms** → simple routing
* **3–8 ms** → auth, headers, rate limiting
* **8–15 ms** → heavy policies, logging
* **20+ ms** → misconfiguration or expensive plugins

### Rule of Thumb

* If backend latency > 100 ms → gateway overhead is noise
* If backend latency < 20 ms → measure carefully

**Important insight**

> Latency is added by *work*, not by *layers*.

---

## 3. Does the Gateway Block Threads?

### Short Answer

**No.**

### Why?

Modern API Gateways are built on:

* Event-driven
* Non-blocking I/O
* OS socket multiplexing (epoll / kqueue)

### What actually happens

* No thread waits for backend response
* Request is represented by a **small in-memory state object**
* Threads do work briefly, then return to the pool

**Contrast**

* Gateway → async, event-driven
* Monolith → often thread-per-request (blocking)

---

## 4. How Many Connections Can an API Gateway Handle?

* **Tens of thousands** per instance (easily)
* **Hundreds of thousands** with tuning
* Millions globally with horizontal scaling

### Why this scales

* Connections are mostly idle
* No thread per connection
* Memory per request is tiny

### Real bottleneck

> The monolith dies long before the gateway does.

---

## 5. The Slow Client Problem (Sneaky Killer)

### What is a Slow Client?

* Sends request fast
* Reads response **very slowly**
* Or stops reading but keeps connection open

### Why this is dangerous

* Gateway must hold:

    * Socket
    * Buffers
    * Request state
* Nothing “fails”
* Resources silently drain

### Without Gateway

* Slow client blocks backend threads
* Thread pool exhaustion
* System collapse

### With Gateway

* Slow clients are absorbed
* Backend remains protected
* Gateway applies:

    * Backpressure
    * Timeouts
    * Connection limits

**Key sentence**

> Fast servers die from slow clients, not heavy computation.

---

## 6. What State Does an API Gateway Maintain?

### Critical Distinction

* **No application state**
* **Yes, connection/request state**

### Minimal Per-Request State

* Client socket reference
* Backend socket reference
* Request ID / correlation ID
* Byte progress
* Timeout deadline
* Status flags (completed, cancelled)

Stored:

* In memory
* Short-lived
* Destroyed after response

**Important**

> Stateless across requests, stateful within a request.

---

## 7. How Timeouts Work Without Blocking Threads

### Key Idea

> Nothing waits. Time is checked only when events occur.

### Mechanism

* Gateway sets a **deadline** (now + timeout)
* Deadline stored in:

    * Timer wheel or
    * Min-heap (priority queue)
* Event loop wakes when:

    * Socket is readable/writable
    * Timer fires

### On Timeout

* Check if request already completed
* If not:

    * Close backend connection
    * Send timeout response (e.g. 504)
    * Free request state

No sleeping.
No polling.
No blocked threads.

---

## 8. Where Should API Gateway Be Hosted for Worldwide Users?

### Correct Answer

**Not one region.**

### Deployment Models

#### 1. Edge / Global Gateway (Best Default)

* Clients connect to nearest POP
* TLS terminates close to user
* Traffic travels on cloud backbone

Benefits:

* Lowest latency
* Tight p95/p99
* Better mobile performance

#### 2. Multi-Region Gateway

* Gateway deployed per region
* Traffic routed via GeoDNS / Anycast
* Gateway and backend are co-located

Benefits:

* Strong isolation
* Predictable behavior

#### 3. Single-Region Gateway (Limited Use)

Only acceptable if:

* Users are regional
* Latency is not critical
* Backend is single-region

---

## 9. Why Edge / Global Gateways Reduce Latency

Latency drops because:

1. Client → Gateway distance is shorter
2. TLS handshake happens locally
3. Cloud backbone beats public internet
4. Fewer hops, lower packet loss
5. Better TCP behavior

**Important**

> Edge gateways move the front door, not the house.

---

## 10. Gateway and Backend Placement Rule

> Gateway and backend should usually be co-located.

Why?

* Gateway → backend calls are synchronous
* Cross-region hops increase tail latency
* Failures amplify across regions

Common pattern:

```
Client → Edge POP → Regional Gateway → Regional Backend
```

---

## 11. One-Line Mental Models (Sticky)

* **Gateways proxy, they don’t redirect**
* **Threads do work, sockets wait**
* **Timeouts don’t wait; they check**
* **Stateless across requests, stateful within one**
* **Slow clients kill systems quietly**
* **Latency is geography pretending to be software**

---

## 12. Interview-Grade Summary

> An API Gateway is an event-driven, stateless-across-requests reverse proxy that maintains minimal in-memory request state to stitch client and backend connections together. It protects backends from slow clients, enforces timeouts without blocking threads, and when deployed at the edge or per region, significantly reduces global latency and tail behavior.

---

If you want, next we can:

* Convert this into **one-pager interview notes**
* Add **ASCII diagrams**
* Map this directly to **AWS / GCP / Azure implementations**
* Turn it into a **system design answer script**

These notes already put you well above “diagram-only” system design discussions.
