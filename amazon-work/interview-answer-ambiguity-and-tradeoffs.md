# Interview answer: leading through ambiguity and technical trade-offs

## Question

We've talked about systemic operational fixes and guiding other engineers. Now I want to look at how you navigate high-stakes ambiguity and technical trade-offs yourself.

Can you tell me about a time when you were leading a project with vague requirements, conflicting technical directions, or high unknown risks? How did you evaluate the risks, what intelligent technical trade-off or pivot did you make to keep the project moving, and how did you convince stakeholders or leadership that your path was the right one?

## Project used

[Prime renewal payment-method update](11-renewal-payment-method-update.md). The later production-failure story is intentionally left out; keep it for failure/learning questions.

## Answer (about 3 to 4 minutes spoken)

### Situation

"I led the India Prime feature that lets a customer fix a failed renewal by adding a new payment method. When a Prime renewal fails for a retriable reason, like insufficient funds, the digital ordering service keeps the renewal order open for a 6-day grace period and retries the charge on a fixed schedule against the existing payment method. Customers were losing Prime during that window even though they could have paid with another method.

The difficulty was that I couldn't just close that order. Closing the workflow cancels the order. So there were only two ways to charge a new payment method, and both were problematic:

- **Cancel and re-create.** Cancel the open order, create a new one, and charge instantly. Done properly, this has to be one atomic step, and that wasn't possible across our signup and renewal services without months of re-architecture. That re-architecture was already planned, so spending months on it now wasn't justified.
- **Swap the payment method on the open order.** This is what the worldwide marketplaces already did. The order stays open, the payment method is swapped behind the scenes, and the swap service sends an 'instant charge' notification to the digital ordering service, which retries the charge immediately.

The worldwide approach didn't work in India. For recurring charges, India requires us to generate the invoice first and charge only after a 24-hour notice period. An instant charge on the new method would break that rule."

### Task

"I owned the design and delivery. I needed to give India the benefit of the swap approach without the instant charge, and without disturbing a workflow that over a hundred marketplaces and several clients besides Prime depend on. Prime was not the only client of that service, so a careless change could break other use cases."

### Action 1: evaluate the options

"I presented two options to product and leadership:

| Option | What it needs | Trade-off |
|---|---|---|
| A. Atomic cancel, re-create and instant charge | Months of re-architecture across the signup and renewal services | Cleanest result, but too slow, and that re-architecture was already planned |
| B. Swap the payment method and let the existing retries charge it | Suppress the instant-charge notification and adjust the retry schedule for India Prime only | Small change, compliant, ships quickly, but needs careful risk control |

Option B also had a risk I called out up front. If a retry fired instantly instead of after the 24-hour notice, it could cancel the existing open order. That order might still have succeeded on a later retry once the customer had balance or the retriable issue cleared. So updating the payment method could make things worse for a customer who was about to recover anyway. Mitigating that was the core of the design."

### Action 2: the pivot and the design

"I chose option B, and made it narrow:

1. **Suppress the instant-charge notification for India Prime only.** Nothing changes for any other marketplace or client.
2. **Adjust the scheduled-retry window.** If a retry was due within the next two hours, I pushed it out so the charge always lands after the notice period. The existing retry mechanism then does the charge.
3. **Cap the retries.** I put a hard cap of four retries so the flow can't be misused.
4. **Confirm immediately to the customer.** As soon as they add the payment method, they see that we will try the charge again in the background."

### Action 3: evaluate and contain the risks

"I worked through three risks:

- **Breaking the global service.** The workflows are used worldwide, and a failure could have affected the wider ecosystem. So I scoped the change to India Prime only, and I checked every service that consumed that notification to confirm it wasn't used for anything other than this case.
- **Misuse of the retries.** The hard cap of four retries addressed this.
- **Cancelling an order that was about to succeed.** Making sure the charge never retried instantly addressed this."

### Action 4: how I convinced stakeholders

"I explained the compliance reason to the worldwide digital ordering service team: India needs the 24-hour notice, so an instant charge isn't allowed for us. That got their agreement to allow the change specifically for India Prime. I walked the Principal Engineers through each change and the dependency audit and got their alignment before I made it. For product and leadership, I framed it as two options with a clear cost for each: months of work on the atomic approach versus a narrow change that delivers now and doesn't block the planned re-architecture."

### Result

"The feature launched without a re-architecture, stayed within the India recurring-charge rules, and let customers recover failed renewals within the grace period. It also bridged the gap until the planned re-architecture, which can later make this a single atomic operation. The trade-off is that the solution relies on the existing retry behavior, so I recorded that dependency and kept the owning teams informed."

### One-line summary if interrupted

"The only clean way to charge a new payment method needed months of re-architecture, so I adapted the worldwide swap approach for India: I suppressed the instant charge for India Prime only, adjusted and capped the scheduled retries, audited the dependent services, and got the owning team and Principal Engineers to agree."

## Likely follow-up questions

- **Why not the atomic solution?** It needed months of cross-service re-architecture that was already planned, for a small customer segment.
- **What was the biggest risk?** Breaking a global service used by many clients, and a retry firing too early and cancelling an order that was about to succeed.
- **How did you limit the blast radius?** The change applied to India Prime only, and I audited every consumer of the notification.
- **How did you handle disagreement?** I explained the compliance reason to the owning team and walked the Principal Engineers through the plan until they agreed.
- **How would you do it differently?** Record the suppressed-notification dependency in a shared risk list from day one.
- **Did it cause problems later?** A later platform-state change caused renewal failures. I traced and fixed it, and it taught me to treat shared APIs as state machines. See the project note for the conflicting accounts.

## Before using in an interview

- Confirm the exact retry cap (four) and your role in the design.
- Keep the later-failure details out unless asked.
- Say "digital ordering service" for renewals and "retail ordering service" for signups, so an outsider can follow.

## Sources

- User's own account of the design and risks (this conversation).
- `behaviour-round/amazon-office-work/update-payment-method.md`
- `behaviour-round/non-tech-skills/BiggestFailure.md`
- `behaviour-round/non-tech-skills/Time-constraints.md`
- `amazon-work/11-renewal-payment-method-update.md`
