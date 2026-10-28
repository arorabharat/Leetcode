# Interview answer: leading through ambiguity and technical trade-offs

## Question

We've talked about systemic operational fixes and guiding other engineers. Now I want to look at how you navigate high-stakes ambiguity and technical trade-offs yourself.

Can you tell me about a time when you were leading a project with vague requirements, conflicting technical directions, or high unknown risks? How did you evaluate the risks, what intelligent technical trade-off or pivot did you make to keep the project moving, and how did you convince stakeholders or leadership that your path was the right one?

## Project used

[Prime renewal payment-method update](11-renewal-payment-method-update.md). The later production-failure story is intentionally left out; keep it for failure/learning questions.

## How to read this note

Details marked **†** are illustrative: they are plausible technical specifics added to make the story concrete, and they are **not** in the repository sources. Replace them with what you actually remember, or drop them, before using this in an interview. Unmarked details come from the source notes.

---

## Answer (about 3 to 4 minutes spoken)

### Situation

"I led the India Prime renewal payment-method update. When a Prime renewal fails for a retriable reason, like insufficient funds, the customer gets a 6-day grace period with scheduled retries. The business wanted customers to be able to add a new payment method during that window, because the feature already existed in other markets. In India that was far from a copy-paste, for three reasons:

- **Two separate services.** New signups were handled by one ordering service and recurring renewals by a different one. A failed renewal left an open renewal order whose invoice was already tied to the old payment method, and the system couldn't charge a different payment method against it.
- **RBI rules.** A recurring debit needs a pre-debit notification at least 24 hours before the charge. A newly registered recurring-payment authorization (a mandate) can't be charged instantly, but the global flow triggered an immediate charge whenever the payment method changed.
- **Unclear ownership.** The change landed in a shared order-management service with no clear owner, and the documentation didn't tell me what a payment-preference update would trigger downstream.

So the requirement was vague ('let customers update payment'), the right architecture was contested, and the riskiest unknowns were duplicate charges and a compliance breach."

### Task

"I owned the design and delivery. I had to decide whether to build it at all and, if so, how to do it without breaking the global flow or violating RBI. Data showed only about 10,000 customers at a time were in this bucket, and a platform consolidation was already on the roadmap. That made a big investment hard to justify."

### Action 1: turn the unknowns into a list and de-risk the biggest ones first

"Before proposing a design I wrote down what I didn't know and ranked each unknown by blast radius.

1. **Does a payment-method update fire an 'instant charge' event into the order-management service?** † I traced the event path end to end in code and in a test stack, because the documentation was silent.
2. **How does the retry schedule actually behave?** † I mapped the retry attempts across the 6-day window and when each pre-debit notification goes out, so I knew whether a later retry would always fall at least 24 hours after the update.
3. **What could go wrong in production?** † I listed the failure modes: a duplicate charge, a debit inside the 24-hour window (a compliance breach), a new mandate that never becomes chargeable, and the open renewal order and its invoice drifting out of sync.

† I spent a short time-boxed spike answering the first two by tracing the flow and replaying scenarios in a test environment, instead of debating architecture in the abstract. The rest of the design followed from what that spike showed."

### Action 2: compare the options and make the trade-off explicit

"I compared three options:

| Option | What it needs | Why I rejected or chose it |
|---|---|---|
| A. Cancel the open renewal and create a fresh signup order | An atomic operation across the signup and renewal services, owned by different teams | Not feasible without a multi-month rewrite, for a small customer segment |
| B. Charge the new instrument immediately | Skip the notification window | Violates the RBI 24-hour rule |
| C. Update the payment and mandate setup, suppress the instant-charge trigger, and let the existing scheduled retry run | A config change plus a small India-specific layer | Smallest blast radius, compliant, no core workflow changes |

I first pushed back on building anything, using the numbers: a small segment, high complexity, and a revamp coming anyway. The business pushed back because the feature was standard elsewhere. A flat 'no' would have just stalled the project, so I pivoted."

### Action 3: the pivot and the design

"My first instinct was to build our own scheduler that would fire a charge at +24 hours. † I dropped that after the spike, because it would have duplicated the retry logic and created a second path that could double-charge alongside the existing retries.

Instead I reused the retry mechanism that already existed. When a customer updates their payment method:

1. We update the payment preference and mandate, and the customer gets the pre-debit notification.
2. Working with senior engineers who knew the order-management service, I configured it to suppress the 'instant charge' event that the update would normally send.
3. The open renewal order stays untouched, and the scheduled retries continue. A retry that fires too soon, say two hours later, fails because the notification window hasn't elapsed. A later retry succeeds once the 24 hours have passed.

† I added safeguards around it: only offering the update flow when enough of the grace window remained for at least one valid retry, a config switch to turn the behavior off quickly, and a metric that would alert on any debit that happened less than 24 hours after a notification."

### Action 4: how I convinced stakeholders

"I framed it as a decision rather than an opinion:

- **To the business:** I didn't block the feature. I showed the customer numbers, explained why a full atomic solution was a multi-month effort, and offered a version that shipped sooner and stayed compliant.
- **To engineering and leadership:** † I wrote a short design doc with the three options, the risk list, and what each option would cost. I reviewed it with the senior engineers who knew the shared order-management behavior, so the riskiest assumption was validated by the people who knew the system best.
- **To manage risk:** † I proposed a staged rollout behind a flag, starting with a small percentage of eligible customers. I defined the success and stop criteria up front: recovered renewals going up, with zero debits inside the notification window and no duplicate charges. That let leadership say yes to a reversible bet instead of an open-ended one."

### Result

"The feature launched without a rewrite or changes to the core renewal flow, stayed within RBI rules, and let customers recover failed renewals during the grace period. Thousands of customers could update their payment method and keep Prime. It also worked as a bridge, because the planned consolidation of the two services could later support a truly atomic version.

I'm also clear about the trade-off. Suppressing an event couples us to platform behavior, so I recorded that dependency and the risks, and I kept the platform owners aligned so they'd know about it when they changed things."

### One-line summary if interrupted

"The requirements were vague, the architecture was contested, and the risk was RBI compliance, so I de-risked the unknowns with a short spike, picked the smallest compliant design that reused existing retries, and got leadership to back it with a staged, reversible rollout."

---

## Likely follow-up questions

- **Why not the cleaner atomic solution?** It needed a cross-team rewrite for about 10,000 customers at a time, and a consolidation was already planned. I treated this as a bridge.
- **What was your biggest unknown, and how did you resolve it?** Whether a payment-preference update fired an instant charge downstream. † I resolved it by tracing the flow and replaying scenarios in a test environment.
- **How did you handle disagreement?** I used data to push back first. When the business still wanted the feature, I proposed a lower-risk path rather than a refusal.
- **How would you do it differently?** Document the suppressed-event dependency in a shared risk registry from day one, and involve the platform owners earlier.
- **Did it cause problems later?** A later platform-state change caused renewal failures. I traced it with the owning team and fixed it, and it taught me to treat shared APIs as state machines and to document workarounds (see the project note for the conflicting accounts).

## Before using in an interview

- Confirm the ~10,000-customer estimate and your exact role; both come from interview notes, not production records.
- Remove or replace every **†** detail you can't honestly stand behind. An invented detail that an interviewer probes is worse than a shorter, accurate answer.
- Keep the later-failure details out unless asked.

## Sources

- `behaviour-round/amazon-office-work/update-payment-method.md`
- `behaviour-round/non-tech-skills/BiggestFailure.md`
- `behaviour-round/non-tech-skills/Time-constraints.md`
- `behaviour-round/non-tech-skills/Priortize-short-term-over-long-term.md`
- `amazon-work/11-renewal-payment-method-update.md`
