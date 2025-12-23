Here’s a **one-pager, low-level design (LLD) cheat sheet** you can literally carry into interviews or design reviews. It’s written the way production engineers think, not textbook architects.

---

# Handling Dependent Service Failure — LLD One-Pager

## Core Principle

**Dependent services will fail.**
Your job is not to prevent failure, but to **contain, classify, and respond** without taking your system down.

> Partial failure is normal. Cascading failure is a design bug.

---

## 1. Where failure is handled (layering rule)

**Never let failure leak into the core blindly.**

| Layer            | Responsibility                    |
| ---------------- | --------------------------------- |
| Adapter (infra)  | Detect, translate, absorb failure |
| Port (interface) | Declare failure semantics         |
| Application      | Decide business behavior          |
| Domain           | Remains pure, no infra knowledge  |

---

## 2. Make failure explicit in the Port (contract design)

Bad (lies by omission):

```java
PaymentResult charge(PaymentRequest req);
```

Good (honest):

```java
ChargeOutcome charge(PaymentRequest req);
```

or

```java
Optional<PaymentResult> charge(PaymentRequest req);
```

or

```java
throws PaymentUnavailableException
```

**Rule:**
If failure is possible, it must be visible in the method signature.

---

## 3. Contain failure inside the Adapter (first firewall)

Adapters must handle:

* Timeouts
* Retries
* Circuit breakers
* Error mapping

**Adapter responsibilities**

* Convert HTTP/gRPC/SDK errors → domain-level outcomes
* Never propagate raw infra exceptions upward

```java
try {
    callDownstream();
} catch (TimeoutException e) {
    return ChargeOutcome.unavailable();
}
```

Adapters are allowed to be ugly. That’s their job.

---

## 4. Mandatory low-level protection mechanisms

### a) Timeouts (non-negotiable)

* Never rely on default client timeouts
* Timeout < your SLA

**Why:**
A slow response kills threads; a failed response preserves capacity.

---

### b) Circuit Breaker

States:

* Closed → normal
* Open → fail fast
* Half-open → cautious recovery

**Purpose:**
Protect *your service* from retry storms and thread exhaustion.

---

### c) Retries (with discipline)

Retry **only if**:

* Operation is idempotent
* Failure is transient (timeouts, 5xx)

Never retry:

* Non-idempotent operations
* Client errors (4xx)

---

### d) Bulkheads

* Isolate thread pools / connection pools per dependency

**Why:**
One bad dependency must not starve the entire service.

---

## 5. Application-level strategies (business decisions)

Once failure is contained, the application decides *what to do*.

### Strategy 1: Fail Fast (hard dependency)

Use when correctness matters more than availability.

Examples:

* Payments
* Auth
* Inventory reservation

```java
if (paymentUnavailable) {
    throw OrderRejectedException();
}
```

---

### Strategy 2: Graceful Degradation (soft dependency)

Use when partial functionality is acceptable.

Examples:

* Recommendations
* Search ranking
* User preferences

```java
return defaultRecommendations();
```

---

### Strategy 3: Deferred Execution (async escape hatch)

Convert availability problems → eventual consistency.

Examples:

* Emails
* Webhooks
* Audits

```java
enqueueForLater();
```

---

### Strategy 4: Cached / Stale Data

Use last known good data.

Examples:

* User profile
* Config
* Feature flags

```java
return cachedResponse();
```

---

### Strategy 5: Feature Disable / Kill Switch

Turn off dependency usage dynamically.

Examples:

* High error rates
* Incident mitigation

**Controlled degradation > uncontrolled outage**

---

## 6. Observability requirements (non-optional)

When a dependency fails, you must know:

* Which dependency
* Failure type (timeout, 5xx, open circuit)
* Impacted user paths

Minimum signals:

* Error rate per dependency
* Latency percentiles
* Circuit breaker state
* Retry counts

No visibility = blind failure.

---

## 7. Testing strategy (LLD-level)

| Test Type         | How                           |
| ----------------- | ----------------------------- |
| Unit tests        | Mock the port                 |
| Integration tests | Stub downstream               |
| Chaos tests       | Inject latency / failure      |
| Prod safety       | Feature flags + fast rollback |

If tests require real dependent services → design is already leaking.

---

## 8. Common LLD anti-patterns (red flags)

* Controllers calling downstream services directly
* No timeouts
* Infinite retries
* Shared thread pools for all dependencies
* Treating all dependencies as hard dependencies
* Returning raw HTTP errors to core logic

Each of these has caused real outages.

---

## Final mental checklist (before shipping)

Ask yourself:

1. What happens if this dependency is slow?
2. What happens if it is completely down?
3. Do we fail, degrade, or defer?
4. Can this failure spread beyond this boundary?
5. Will we know it’s happening in production?

If you can answer all five, your design is production-grade.

---

### One-line summary to remember

> **Adapters absorb failure, ports declare it, applications decide, and the system survives.**

This is the difference between *systems that work in demos* and *systems that stay alive at 3 a.m.*
