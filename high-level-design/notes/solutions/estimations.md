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