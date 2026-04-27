Clean power-of-10 formula:

**Peak TPS ≈ (DAU × Actions/User/Day × 10) / 10⁵ = DAU × Actions / 10⁴**

For your example (1M DAU × 1,000 actions):
- Peak TPS = 10⁶ × 10³ / 10⁴ = **10⁵ TPS = 100,000 TPS**
- Average TPS = 10⁶ × 10³ / 10⁵ = **10⁴ TPS = 10,000 TPS**

**Napkin rules to memorize:**

| Metric | Formula | Just do this |
|---|---|---|
| Total daily requests | DAU × Actions | Add the exponents |
| Average TPS | (DAU × Actions) / 10⁵ | Subtract 5 |
| Peak TPS (10×) | (DAU × Actions) / 10⁴ | Subtract 4 |

**Quick reference table** (Peak TPS, in powers of 10):

| DAU \ Actions | 10 | 100 | 1,000 |
|---|---|---|---|
| 10K (10⁴) | 10¹ | 10² | 10³ |
| 100K (10⁵) | 10² | 10³ | 10⁴ |
| 1M (10⁶) | 10³ | 10⁴ | 10⁵ |
| 10M (10⁷) | 10⁴ | 10⁵ | 10⁶ |
| 100M (10⁸) | 10⁵ | 10⁶ | 10⁷ |

Just add the exponents of DAU and Actions, then subtract 4. Done.


**Peak TPS table (just the exponents, peak factor = 10):**

| DAU \ Actions | 1 | 2 | 3 | 4 |
|---|---|---|---|---|
| 4 | 1 | 2 | 3 | 4 |
| 5 | 2 | 3 | 4 | 5 |
| 6 | 3 | 4 | 5 | 6 |
| 7 | 4 | 5 | 6 | 7 |
| 8 | 5 | 6 | 7 | 8 |

**Rule:** Peak TPS exponent = DAU exponent + Actions exponent − 4

Example: 1M DAU (6) × 1,000 actions (3) → 6 + 3 − 4 = **5** → 10⁵ TPS

----------------------------------------------------------
**Write throughput table (just the exponents, in bytes/sec):**

**Rule:** Write throughput exponent = DAU exponent + Actions exponent + Bytes/request exponent − 4

(Same peak factor of 10, divided by 10⁵ seconds/day)

**Bytes per request reference:**
| Type | Bytes | Exponent |
|---|---|---|
| Tiny (event ping, like) | ~100 B | 2 |
| Small (tweet, chat msg) | ~1 KB | 3 |
| Medium (post with metadata) | ~10 KB | 4 |
| Large (image upload) | ~100 KB – 1 MB | 5–6 |
| Very large (video chunk) | ~10 MB | 7 |

**Peak write throughput exponent table** (for 1M DAU = 10⁶):

| Actions \ Bytes | 2 (100B) | 3 (1KB) | 4 (10KB) | 5 (100KB) | 6 (1MB) |
|---|---|---|---|---|---|
| 1 (10/day) | 5 | 6 | 7 | 8 | 9 |
| 2 (100/day) | 6 | 7 | 8 | 9 | 10 |
| 3 (1K/day) | 7 | 8 | 9 | 10 | 11 |
| 4 (10K/day) | 8 | 9 | 10 | 11 | 12 |

**Quick interpretation of throughput exponents:**

| Exponent | Throughput | Real-world feel |
|---|---|---|
| 6 | 1 MB/s | Single small server |
| 7 | 10 MB/s | Saturates 100 Mbps link |
| 8 | 100 MB/s | Saturates 1 Gbps link |
| 9 | 1 GB/s | Saturates 10 Gbps link |
| 10 | 10 GB/s | Needs distributed system |
| 11 | 100 GB/s | Hyperscaler territory |

**Example:** 1M DAU (6) × 1,000 actions (3) × 1 KB writes (3) → 6 + 3 + 3 − 4 = **10⁸ bytes/sec = 100 MB/s** peak write throughput. You'd saturate a 1 Gbps NIC and need sharding or a distributed write path (Kafka, Cassandra, etc.).
---------------------------------------------------------------------------------------------------
