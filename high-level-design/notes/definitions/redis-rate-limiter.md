This document outlines the implementation of a **Fixed Window Rate Limiter** using Redis and Lua. For a Senior Engineer, the focus here is on ensuring **atomicity** and **operational stability**.

---

## 1. The Implementation: Redis + Lua
To achieve a limit of **300 requests per minute**, we use a Lua script to ensure the "Check-Incr-Expire" cycle is indivisible.

### The Lua Script (`rate_limit.lua`)
```lua
-- KEYS[1]: The rate limit key (e.g., "ratelimit:user_123:min_55")
-- ARGV[1]: The window duration in seconds (e.g., "60")
-- ARGV[2]: The maximum allowed requests (e.g., "300")

local current = redis.call("INCR", KEYS[1])

-- If this is the first request of the window, set the expiry
if current == 1 then
    redis.call("EXPIRE", KEYS[1], ARGV[1])
end

-- Return the current count to the application
return current
```

### The Application Logic (Pseudocode)
1.  **Generate Key:** `key = "rate:" + userId + ":" + currentMinuteTimestamp`
2.  **Execute:** `result = redis.eval(script, 1, key, 60, 300)`
3.  **Enforce:** * If `result > 300`: Return `429 Too Many Requests`.
    * Else: Proceed to business logic.

---

## 2. Setting EXPIRE: Inside vs. Outside Lua
In distributed systems, the "where" matters as much as the "how."

### Option A: Inside Lua (Atomic)
The `EXPIRE` command is packaged with the `INCR`.
* **Pros:**
    * **Guaranteed Atomicity:** It is impossible for a key to be created without a TTL.
    * **Zero "Ghost Keys":** Prevents memory leaks where keys stay in Redis forever because a network blip killed the second command.
    * **Lower Latency:** Only one round-trip (RTT) between your service and Redis.
* **Cons:** * Slightly higher CPU usage on the Redis side (negligible for this logic).

### Option B: Outside Lua (Independent Commands)
The service calls `INCR`, waits for a response, then calls `EXPIRE`.
* **Pros:**
    * Easier to debug using standard Redis monitoring tools (`MONITOR`).
* **Cons:**
    * **Race Conditions:** If the service crashes after `INCR` but before `EXPIRE`, that user is permanently blocked (or the key lives forever).
    * **Double RTT:** Doubling the network overhead for every single request.
    * **Complexity:** You have to write "Retry" logic in your application code to handle cases where the second command fails.

---

## 3. Pros and Cons of the Fixed Window Pattern

### Pros
* **Extreme Speed:** $O(1)$ complexity. It is the fastest way to rate limit in Redis.
* **Memory Efficiency:** You only store one integer per user per window.
* **Simplicity:** Very easy to reason about and implement across multiple microservices.

### Cons
* **The Boundary Problem:** A user can send 300 requests at `00:59` and another 300 at `01:01`. Within a 2-second span, your system just took 600 requests, potentially spiking your downstream DB.
* **Bursting:** It doesn't smooth out traffic; it just caps it.

---

## 4. Production Checklist for SDEs
If you are deploying this at scale (e.g., for an Amazon-scale service):

* **Script Pre-loading:** Use `SCRIPT LOAD` to get a SHA1 hash of your Lua script. Use `EVALSHA` in your code to avoid sending the full script text over the wire on every request.
* **Client-Side Fallback:** If Redis is down (Connection Timeout), decide if you want to **Fail Open** (allow the request) or **Fail Closed** (block everything). Most consumer apps fail open to prioritize availability.
* **Key Design:** Include the time bucket in the key name (e.g., `...:min:20`). This acts as a secondary safety net for memory—even if an `EXPIRE` fails, that key becomes "logically" dead after 60 seconds.
* **MaxMemory Policy:** Ensure your Redis instance is set to `allkeys-lru` or `volatile-lru` so it stays healthy under heavy load.


----------------------------------------------------------------------------------------------------------

For a Senior Engineer evaluating performance at scale, it is important to distinguish between **Server-Side Execution Time** and **End-to-End Latency**.

Using a Fixed Window approach with a Lua script is highly efficient because it avoids multiple round-trips. Below is the breakdown of the latency you can expect.

---

## 1. Breakdown of Latency Components

### Server-Side Execution (The "Redis Time")
Redis is single-threaded and keeps all data in RAM. A simple Lua script that performs an `INCR` and an `EXPIRE` typically executes in:
* **< 0.1ms to 0.5ms**

Because there is no disk I/O and the logic is $O(1)$, the bottleneck is almost never the script execution itself, provided you aren't running massive loops inside the Lua code.

### Network Round-Trip (The "Wire Time")
This is usually the largest component of your latency.
* **Same Availability Zone (AZ):** ~0.5ms – 1.0ms
* **Cross-AZ:** ~1.0ms – 2.0ms
* **Cross-Region:** 50ms – 200ms+ (Not recommended for rate limiting)

### Total End-to-End Latency
In a standard production environment (Service and Redis in the same VPC/AZ), you are looking at a total overhead of:
> **1ms to 3ms per request.**

---

## 2. Latency: Inside vs. Outside Lua
As you noted earlier, setting the expiry outside the script affects the "throttling" of your application. It also directly impacts your **p99 latency**.

| Approach | Network Round-Trips | App-Side Latency (Total) |
| :--- | :--- | :--- |
| **Inside Lua** | **1 RTT** | **~1ms - 2ms** |
| **Outside Lua** | **2 RTTs** (Incr + Expire) | **~2ms - 4ms** |


---

## 3. Factors That Spike Latency (The "Tail")

While the *average* latency is low, you should watch out for these "tail latency" (p999) killers:

* **Script Compilation:** If you use `EVAL` instead of `EVALSHA`, Redis must parse and compile the script every time. Always pre-load the script.
* **Large Payloads:** If your keys are excessively long or you are passing large arguments, serialization/deserialization time increases.
* **Redis CPU Saturation:** If Redis is hitting 80-90% CPU usage, the "Single-Threaded" nature means your script has to wait in the queue (Head-of-Line blocking), potentially spiking latency to **10ms+**.
* **Connection Management:** Using a connection pool in your service is vital. Creating a new TCP/TLS connection for every `EVAL` would add **20ms - 100ms** of overhead.

---

## 4. Operational Comparison

| Metric | Fixed Window (Lua) | Token Bucket (Lua) |
| :--- | :--- | :--- |
| **Execution Complexity** | $O(1)$ | $O(1)$ |
| **Average Latency** | ~1.5ms | ~1.7ms |
| **Throughput** | High (100k+ ops/sec) | Slightly Lower (More math) |

**Conclusion:** For a 300 req/min service, Redis won't even break a sweat. You could likely handle 10,000+ users with this exact setup on a single small Redis node while staying well under a **2ms** latency budget.

Are you planning to run this on a managed service like AWS ElastiCache, or a self-managed Redis cluster?

-------------------------------------------------------------------------------------------------------------------------------