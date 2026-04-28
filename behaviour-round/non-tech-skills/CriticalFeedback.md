1. Receiving a critical feedback on code - take feedback constructively and build on it. Took time to discuss.
2. Being proactive on sharing status update.
3. Coworker gave you feedback that your document was complete. UPMA COE.
4. Feedback on BRD, you missed something

UPMA COE



I'll structure this experience in the STAR format for you. Let me create a polished version.

## STAR Format: Receiving Critical Feedback

### **Situation**
About a year and a half ago, I was leading the launch of the first **stacking use case on the homepage** — a high-TPS (transactions per second) location. Stacking is a retention plan shown to Prime customers in the last 30 days of their membership: if their auto-renewal is off, we offer a ₹100 discount to incentivize them to buy the next cycle. The offer is only meant to show for customers whose auto-renewal is off and who aren't on third-party-paid subscriptions (e.g., Jio bundles, where the third party handles renewals).

A downstream **risk service** owned all the business logic and pre-computed risk values for every customer, which made it possible to serve this offer at high TPS. Our scope was limited to UI changes — replacing the "turn on auto-renewal" prompt with a "stacking" prompt.

### **Task**
I needed to design, align stakeholders, and launch the stacking upsell on the homepage. Because I was excited about working on a high-payoff surface for the first time, I moved fast — I quickly authored the design doc, identified the stakeholders I thought were relevant, got buy-in from the downstream teams I'd be making changes in, and kicked off execution with one other SDE.

### **Action (initial — what went wrong)**
During QA, we discovered a launch-blocking issue: **the risk service wasn't refreshing after a stacking signup.** Once a customer signed up and landed back on the homepage, the stale risk value kept showing the upsell until they manually refreshed once or twice.

The root cause traced back to a gap in my alignment process. Because the downstream risk service had been stable for a long time, I had only reached out to that team for **TPS capacity requirements** — I never walked them through the stacking use case or the design. When the bug surfaced at the eleventh hour, that team had to build full context from scratch to help debug, which slowed the fix and blocked the launch.

My principal engineer gave me direct, critical feedback: even when a downstream service is stable and I'm only "consuming" it, I need to get explicit buy-in on the **use case**, not just the capacity ask. Otherwise, when something breaks, the owning team has zero context and can't move quickly.

To unblock the launch, we added a controller-level check that read from the source of truth instead of the pre-computed cache, ensuring the upsell wouldn't re-show post-signup.

### **Action (applying the feedback)**
I internalized the feedback and changed my approach starting with the very next project — launching retention upsells on other surfaces. Specifically, I:

- **Identified all stakeholders early**, including downstream service owners whose systems I'd merely be consuming, not modifying.
- **Opened tickets/SIMs against each downstream team** at project kickoff, documenting the use case, the integration pattern, and our TPS expectations — so context lived in writing from day one.
- **Walked downstream owners through the use case**, not just the API contract, so they could flag edge cases (like cache-staleness) before we hit QA.

### **Result**
This change has paid off consistently over the last year across multiple retention launches. Alignment cycles are shorter, production and QA issues are caught earlier, and when bugs do surface, downstream teams already have context and help resolve them quickly — instead of starting from zero like they had to on the stacking launch. The principal engineer's feedback fundamentally reshaped how I scope stakeholder alignment on cross-team projects.

---

Want me to tighten this further (e.g., for a promo doc or interview answer), or adjust the tone — more concise, more reflective, etc.?


-------------------------------------------------------------------------------------------------------

# Feedback Reflection: RCA Documentation for Diwali Sale Throttling Incident

*STAR Format*

---

## Situation

During last year's Diwali sale — one of our highest-velocity events — a downstream payment-method service was throttled within the first 10–15 minutes of the event going live. This was a recurring problem that had surfaced during multiple prior high-velocity events, and the impact was significant enough to be escalated to the leadership level.

The investigation was complicated by concurrent issues that occurred in parallel: the same service was hitting scaling limits, and a separate downstream PSP service was also failing. Untangling the actual root cause from these overlapping symptoms required deep, cross-team investigation under a strict 30-day RCA deadline.

## Task

Because I had the most context on the affected downstream service, leadership positioned me to own the root-cause analysis. My responsibilities were to:

- Identify the true root cause and isolate it from the concurrent scaling and PSP failures.
- Author the RCA document within the 30-day window mandated for such incidents.
- Drive the analysis to a conclusion that prevented this recurring pattern from happening again in future high-velocity events.
- Present findings to a cross-functional audience, including engineers unfamiliar with the downstream service.

## Action

I went deep into the downstream service well beyond my existing high-level understanding — reading code, walking through architecture, and combing through logs across multiple services to trace the exact request flow. I coordinated with the team that owned the service to pull additional logs and clarify behavior. Through this, I isolated the UPM/payment-method throttling from the unrelated PSP failures and the scaling issue, and narrowed the cause down to two-to-three specific possibilities, which I documented along with supporting data.

Given the tight 30-day RCA timeline, I prioritized depth of analysis over polish for the first draft. I shared the document and walked the team through it. Senior engineers who already had context could engage productively on the open questions, but engineers new to the service could not contribute meaningfully because the document started from where my own learning had begun (around the 30% mark) rather than from first principles.

After the meeting, my Principal Engineer gave me direct feedback: for documents like this, the structure needed to onboard readers who lacked context, not just present findings to those who already had it. I initially pushed back — explaining the timeline pressure, the cross-team coordination overhead, and how long it had taken just to build the understanding myself. She acknowledged the constraints but held the bar: even within that window, I could have invested more in structure. I accepted the feedback.

I then booked a follow-up review slot for the very next week and rewrote the document. I led with a clear architectural overview of the service, used AI tooling to accelerate drafting the context sections, and reorganized the analysis so a reader with no prior exposure could follow the reasoning end-to-end. I treated the document as serving two audiences at once — those debugging the incident, and those needing to understand the service for the first time.

## Result

The revised document was well received by the broader team. Notably, the team that actually owned the downstream service appreciated the rewrite as well — they felt it explained the architecture clearly enough to function as a reference architectural document, not just an RCA. The open questions were resolved in that follow-up review, and the recurring throttling issue was addressed so it would not repeat in subsequent high-velocity events.

More importantly, I internalized the feedback as a durable lesson. For complex RCAs and design documents going forward, I now structure writing around the reader with the least context, not the most. I front-load the architectural and conceptual grounding before diving into analysis, and I treat "time pressure" as a reason to be more disciplined about structure, not less. This shift has meaningfully improved how I document and communicate complex technical work in subsequent projects.


 