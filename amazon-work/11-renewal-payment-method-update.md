# Prime renewal payment-method update

## Summary

You worked on allowing India Prime customers with a retriable renewal failure to update their payment method during the six-day grace period, while respecting recurring-payment rules.

## Constraints

- Signup payments used a retail pipeline, while recurring renewals used a separate digital pipeline.
- A failed renewal left an open digital order for the grace period.
- RBI rules required a 24-hour pre-debit notification before a recurring charge.
- The notes say an immediate charge against the new payment instrument was not straightforward and that cancel/re-subscribe crossed ownership boundaries.

## Your documented solution

Rather than rebuilding both pipelines or changing the core retry workflow, you proposed changing the payment/mandate setup while allowing the existing scheduled retry mechanism to run. The notes describe working with Principal Engineers to suppress an “Instant Charge” retry-trigger event, effectively waiting for a later retry that satisfied the notification timing requirement.

## Later failure and your response

After the workaround had been operating for an extended period, a platform team introduced a “Deleted” status flag for payment preferences. The India-specific logic checked whether a configuration existed but did not validate that status. API responses looked valid even when the preference was not usable. You traced the issue with the Payment Preference team and updated the logic to check the flag. The obstacle story says the observed 5–10% failure pattern returned to zero after the fix.

## Reflection

The failure-story notes acknowledge that the original workaround introduced fragility and coupling. They emphasize documenting risks, aligning with platform owners, and balancing the short-term feature request against long-term maintainability.

## Sources and caveats

- `behaviour-round/amazon-office-work/update-payment-method.md`
- `behaviour-round/src/Obstacle.md`
- `behaviour-round/non-tech-skills/BiggestFailure.md`
- `behaviour-round/non-tech-skills/Time-constraints.md`
- `behaviour-round/non-tech-skills/Priortize-short-term-over-long-term.md`
- `behaviour-round/non-tech-skills/Summary.md` (brief story-bank reference to a renewal failure caused by a missing payment preference)
- `behaviour-round/amazon-office-work/intro.md` (general role context on Prime recurring charges and payment instruments)

The notes vary on how long the workaround ran (“18 months” versus “nearly two years”) and describe the later issue in different ways. Verify dates and impact before using them.
