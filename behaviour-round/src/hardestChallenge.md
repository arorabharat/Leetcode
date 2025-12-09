**“What is the hardest challenge you’ve overcome at work?”**,

---

# ⭐ **Answer: Hardest Challenge I Overcame at Work**

One of the toughest challenges I faced was fixing a long-standing race condition in Amazon India’s Prime subscription flow that disproportionately affected *never-Prime* customers—people purchasing Prime for the first time. Around **10% of ~850 daily UPI customers** were failing during payment because AIPS redirected them to the OTP page **before** the subscription ID was created. This led to cancelled orders, stale mandates, and a measurable drop in payment success rate, directly hurting Prime acquisition.

The challenge was hard for three reasons:

1. The failure was intermittent and timing-sensitive, making it difficult to reproduce.
2. The root cause sat across multiple systems (AIPS, PFW, APX, Prime), each with different ownership.
3. Any fix risked introducing latency or breaking a mission-critical payment flow.

To solve it, I designed and implemented a new **GetSubscriptionIdStage** in the AIPS backend that ensured the subscription ID was fully written before moving customers to OTP. I introduced a **configurable delay mechanism** and built a **weblab experiment** with 1s/3s/5s treatments to measure the trade-off between consistency and latency. I created new helpers to detect never-Prime customers, refactored payment-source logic to update client references safely, added fail-safes that abort the flow cleanly if data is still missing, and wrote **300+ lines of unit tests** to ensure the fix was airtight. I coordinated with multiple teams to update execution sequences and verify no regression in payment success.

We launched the solution on time, and the impact was substantial:
– The race condition was **completely eliminated** for both UPI and card payments.
– Payment Success Rate for new Prime subscribers improved significantly.
– Stale-mandate issues and customer escalations dropped to near zero.
– The new stage became a reusable pattern for future payment flows.

This was the hardest challenge because it demanded deep debugging, cross-team alignment, careful experimentation, and a design that was both robust and backward-compatible. Delivering this fix materially improved the Prime acquisition funnel and strengthened a critical part of Amazon’s payments infrastructure.

---

If you want, I can also tighten this into a **60–90 second spoken version** or expand it for **a promotion packet narrative**.
