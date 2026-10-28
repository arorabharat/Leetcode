# Interview answer: leading through ambiguity and technical trade-offs

## Question

We've talked about systemic operational fixes and guiding other engineers. Now I want to look at how you navigate high-stakes ambiguity and technical trade-offs yourself.

Can you tell me about a time when you were leading a project with vague requirements, conflicting technical directions, or high unknown risks? How did you evaluate the risks, what intelligent technical trade-off or pivot did you make to keep the project moving, and how did you convince stakeholders or leadership that your path was the right one?

## Project used

[Prime renewal payment-method update](11-renewal-payment-method-update.md). Deliberately excludes the later production-failure story, which is kept for failure/learning questions.

## Answer

### Situation

The business wanted India Prime customers whose renewal failed for a retriable reason, such as insufficient funds, to update their payment method during the 6-day grace period. The feature already existed in other markets, but its requirements didn't carry over to India. Three things made it ambiguous and risky:

- **Two separate pipelines.** Signups ran on a retail pipeline and renewals on a digital pipeline. A failed renewal left an open digital order, and the system couldn't charge a new instrument against the existing invoice.
- **RBI rules.** A recurring debit requires a 24-hour pre-debit notification, so the global behavior of retrying immediately after a payment-method update wasn't allowed.
- **Shared system, unclear ownership.** The change touched a shared ordering system whose behavior I couldn't confirm quickly.

### Task

I had to decide whether the feature was worth building and how to build it safely. Only about 10,000 customers at a time were affected, and a platform convergence was already planned. I needed a solution that stayed compliant and didn't destabilize shared systems.

### Action: evaluating risk and trade-offs

I compared three options:

1. **Cancel the open renewal and re-subscribe.** This would need atomicity across the retail and digital stacks, crossed service-ownership boundaries, and implied a multi-month rewrite.
2. **Charge immediately on the new instrument.** This violated the RBI notification requirement.
3. **Update the payment and mandate setup, then let the existing scheduled retry run.** This was the smallest change and left the core retry workflow untouched.

I first pushed back with data: a small affected population, high complexity, and a platform revamp coming anyway. The business still wanted the feature because it was standard elsewhere, so I pivoted to option 3 rather than blocking it.

With Principal Engineers, I suppressed the "Instant Charge" retry-trigger event. A retry that fired too soon would fail the notification window, and a later scheduled retry would succeed once the 24 hours had passed. That gave compliance without a rewrite and without touching the core renewal flow.

### Convincing stakeholders

I didn't argue against the business; I made the trade-offs visible and offered a lower-risk path:

- It met the RBI requirement.
- It avoided a risky cross-stack rewrite.
- It worked as a bridge until the planned pipeline convergence.

I validated the design with Principal Engineers, which gave leadership confidence that it was safe for the shared system. The business got the feature without waiting for the revamp.

### Result and reflection

The feature launched without a rewrite, stayed compliant with RBI, and let customers recover failed renewals during the grace period. The trade-off was coupling to platform behavior. I made that explicit by documenting the suppressed-event dependency and the associated risks, and by aligning early with the platform owners so that later changes would be caught sooner.

## Short fallback if asked "Did it cause problems later?"

A later platform-state change caused renewal failures. I traced it with the owning team and fixed the logic. It taught me to treat shared APIs as state machines and to document workarounds in a risk registry. (Details and conflicting root-cause accounts are in the linked project note.)

## Before using in an interview

- Confirm the ~10,000-customer estimate and your exact role; both come from interview notes, not production records.
- Be ready to explain why a retry that fails the 24-hour window is acceptable: the later scheduled retry succeeds.
- Keep the later-failure details out unless asked; the source accounts disagree on the root cause.

## Sources

- `behaviour-round/amazon-office-work/update-payment-method.md`
- `behaviour-round/non-tech-skills/BiggestFailure.md`
- `behaviour-round/non-tech-skills/Time-constraints.md`
- `behaviour-round/non-tech-skills/Priortize-short-term-over-long-term.md`
- `amazon-work/11-renewal-payment-method-update.md`
