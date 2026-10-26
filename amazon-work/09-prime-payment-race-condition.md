## Never Prime race condition
While I was working at Amazon, I came across an interesting issue during one of my on-call rotations. I was actually investigating a completely different bug, digging through customer journeys and distributed logs, when I spotted an odd pattern: customers trying to buy Prime for the very first time were failing on their initial transaction.

This issue had never been reported by customers or flagged by our standard alarms because it wasn’t completely blocking. If a user simply hit retry, the second attempt went through. But looking at the telemetry, this broken first-time experience was hitting around 30% to 40% of new customers using UPI recurring mandates. For me, that was unacceptable—a customer’s very first interaction with Prime shouldn’t be a failed payment screen.

So, I decided to pull on that thread. Correlating the traces across our payments platform and the subscription engine, I uncovered a timing race condition. To keep the checkout redirect snappy, the Prime Subscription ID was being created asynchronously in the background. For credit and debit cards, this worked fine because users had to wait for an SMS OTP, switch apps, or type it in—giving our backend plenty of time (15 to 30 seconds) to create the entity. But with UPI, users were jumping into their banking app, punching in their 4- or 6-digit mPIN in a couple of seconds, and returning immediately. Because the UPI flow was so fast, the payment callback was hitting our systems before the backend had finished creating the Subscription ID.

I needed to fix this quickly, but redesigning cross-service contracts would take weeks. So, I split the solution into two phases: an immediate tactical mitigation and a proper long-term architectural fix.

For the mitigation, I planned to introduce a temporary delay stage in AIPS—our payment orchestration service—specifically for first-time Prime customers on UPI, leaving card users and existing subscribers untouched. We faced initial pushback from the AIPS team; they are a multi-tenant platform handling traffic for Checkout, Kindle, and other businesses, and they understandably didn’t want a client-specific delay in their engine.

I set up a meeting with their team, walked them through the data, and showed them the daily customer failure volume. I made it clear that this was temporary tech debt to protect user experience right now, logged a formal ticket with an agreed timeline to remove it, and showed that I was parallelly driving the permanent solve with the core Subscription team. With that data and a clear exit strategy, they signed off. I then analyzed our subscription creation telemetry, saw its p95 latency was roughly 3 seconds, and ran experiments from 1 to 5 seconds. Three seconds hit the sweet spot: about 95% of those failing first-time UPI transactions started passing cleanly.

Parallelly, I drove the permanent architectural fix with the core Subscription team. I pitched a pre-reservation pattern where they would expose an API to reserve an uncommitted Subscription ID the moment a high-intent user landed on the checkout page. To make sure abandoned checkouts didn't pollute their database, we agreed on a 24-hour TTL for these uncommitted leases. That duration was chosen directly from our order lifecycle data, where Amazon orders stay open for a maximum of 24 hours before auto-canceling.

Once the Subscription team rolled out the reserve API, we integrated it on our side. Claiming an existing reserved ID took only 200 to 500 milliseconds instead of 3 seconds. We cut over to the new flow, cleaned up our tech debt in AIPS by stripping out the artificial delay, and permanently wiped out that 30% to 40% failure spike for new UPI customers while keeping the checkout fast.

-----------------------------------------


Here is the combined interview evaluation across the four core pillars, breaking down exactly why this narrative earns a **9.5 / 10** for an Atlassian Senior Software Engineer Manager Round:

---

### **1. Proactive Discovery over Reactive Ticket-Taking (Pillar 2: Operational Excellence)**

* **The Signal:** Starting with *"I found this during an unrelated on-call investigation, and it was never reported because it wasn't a hard block"* instantly sets you apart from mid-level engineers.


* **Why it scores high:**
* Mid-level engineers wait for P0/P1 alarms or customer support escalations.


* Senior engineers proactively spot latent system defects in telemetry while looking at other systems.


* It directly demonstrates the recruiter's requirement: *"proactively seeking problems to solve"* rather than *"expecting opportunities to be given to them"*.





---

### **2. Customer-Centric Ownership (Pillar 1: Owning Outcome & Values)**

* **The Signal:** Even though users could simply hit retry and succeed, you refused to treat a 30%–40% initial failure rate as acceptable.
* **Why it scores high:**
* It maps directly to Atlassian’s core value: **"Don’t #@!% the customer"**.


* You recognized that first-impression churn is silent—many customers drop off permanently instead of retrying.
* It proves you hold a high bar for product quality even when business metrics don't trigger red alerts.



---

### **3. Distributed Systems Mastery & Human Factors**

* **The Signal:** You didn't just diagnose a race condition; you explained the **behavioral asymmetry** behind it (SMS OTP friction of 15–30s vs. UPI mPIN muscle memory of 2s).
* **Why it scores high:**
* Demonstrates that you evaluate software within real-world human contexts, not just in isolated unit tests.
* It explains why card transactions worked cleanly while UPI failed, showing precise technical diagnosis across service boundaries (AIPS, core payments, subscription engine).





---

### **4. Pragmatic Engineering: Mitigation vs. Permanent Architecture**

* **The Signal:** You divided the delivery into two deliberate phases: an immediate tactical mitigation (delay stage) and a long-term architectural redesign (pre-reservation pattern).
* **Why it scores high:**
* Shows mature trade-off analysis under pressure: you didn't stall for months to build a perfect architecture while customers were actively failing, nor did you leave an artificial 3-second delay in production permanently as tech debt.


* You used data (the **p95 creation latency of 3 seconds**) to configure the delay rather than guessing, which directly matches the recruiter's criterion for data-driven action.





---

### **5. Influence Without Authority & Respecting Multi-Tenancy (Pillar 3: Leadership)**

* **The Signal:** Navigating the pushback from the AIPS team.
* **Why it scores high:**
* You understood and respected their perspective: they run a multi-tenant platform (Checkout, Kindle, etc.) and had every right to reject client-specific hacks.


* You didn't escalate to managers or force your way through. Instead, you:


1. Shared data on the customer impact.


2. Framed it as explicit, tracked tech debt with a committed removal deadline.


3. Showed you were already working with the Subscription team on the exit strategy.




* This matches Atlassian's interview pillar: *"influencing ability where you make others agree not by force, but because they see your leadership and rationale"*.





---

### **6. End-to-End Architectural Rigor (Definition of "Done")**

* **The Signal:** The **Pre-Reservation Pattern with a 24-hour TTL**.
* **Why it scores high:**
* Moving from a 3,000ms synchronous dependency to a 200–500ms reservation claim is an elegant, scalable pattern.
* Tying the 24-hour TTL directly to the order lifecycle (Amazon orders staying open for a max of 24 hours, auto-canceling in 8–16 hours) proves your definition of "done" accounts for database garbage collection, state bloat, and resilience.





---

### **Summary of the Score**

| Dimension | Rating | Reason |
| --- | --- | --- |
| **Pillar 1: Owning Outcome**

| **10 / 10** | End-to-end journey ownership, proactive discovery, and tracking down tech debt until completely resolved.

|
| **Pillar 2: Operational Excellence**

| **9.5 / 10** | Latency reduction from $3\text{s} \to 200\text{ms}$, p95-based experiment sizing, and cleanup worker TTL.

|
| **Pillar 3: Leadership Ability**

| **9.5 / 10** | Overcoming platform team pushback with data, clear trade-offs, and mutual alignment.

|
| **Pillar 4: Organizational Impact**

| **9.0 / 10** | Reusable pre-reservation pattern across subscription services.

|
| **Overall** | **9.5 / 10** | **Strong Hire signal.** Delivered naturally, it highlights deep technical judgment, zero ego, and clear customer empathy.

|