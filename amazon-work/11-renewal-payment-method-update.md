# Prime renewal payment-method update

## Summary

You worked on allowing India Prime customers whose recurring renewal failed for a retriable reason (for example, insufficient funds) to update their payment method during the six-day grace period. The goal was to recover the renewal and avoid losing the subscription, while complying with India’s recurring-payment requirements.

One failure-story estimate says roughly 10,000 customers at a time were in this bucket. The source frames that as a relatively small population compared with the implementation complexity; it is an interview-note estimate, not a validated metric.

## Existing system and constraints

- First-time Prime signups and recurring renewals ran through different payment/order paths: the retail pipeline handled signup, while the digital pipeline handled renewals.
- After a retriable renewal failure, the digital order stayed open during the grace period and was associated with the failed renewal/payment attempt. The source says the system could not simply charge a different instrument against the already-created invoice.
- The standard global behavior retried immediately after a payment method was updated. India required a pre-debit notification at least 24 hours before a recurring debit, so an immediate charge against a newly configured mandate was not appropriate.
- Canceling the open renewal and starting a new signup was not a straightforward alternative: it crossed service-ownership boundaries and would have required coordinated changes across separate stacks. One story describes making that transition atomic as a substantial architectural problem.
- A full convergence/revamp was already a longer-term direction in some accounts, while the business wanted the feature because it was available in other markets. The failure-story notes say you initially pushed back using the small affected population and the architectural cost, then agreed to a lower-impact workaround.

## Design and implementation described in the notes

Rather than rebuild the retail and digital flows or replace the core renewal retry mechanism, you proposed updating the payment/mandate setup while leaving the existing open digital order and scheduled retries in place.

The implementation account says you worked with Principal Engineers on a configuration that suppressed the “Instant Charge” retry-trigger event. That prevented the payment-method update from launching an immediate charge and let the normal retry schedule proceed after the required notification lead time. Another explanation gives the example of a retry due only a couple of hours later: that attempt would not satisfy the 24-hour window, while a later scheduled attempt could.

This was a pragmatic bridge around the existing system, not a clean unification of signup and renewal. The notes characterize it as avoiding a large cross-pipeline rewrite and preserving the existing retry workflow; they also acknowledge that the configuration created coupling to platform behavior.

## Production failure accounts and response

The follow-up stories agree that renewal failures appeared when the India-specific flow interacted with changed or hidden platform state, but they do not give one consistent root cause:

- **Deleted payment-preference status:** `update-payment-method.md`, `Obstacle.md`, and `BiggestFailure.md` describe a platform migration that introduced a “Deleted” status instead of removing or replacing a preference. The India-specific check looked for a preference but did not validate that status, so an API response could look valid while downstream processing treated the preference as unusable. The obstacle account says the affected failure pattern was around 5–10%, and that validating the flag brought it back to zero; it also says the feature could then be dialed up again.
- **Dirty parameter state:** `judgement-error.md` describes a parameter that had a value but was internally marked “dirty.” Based on the documentation, the implementation treated an already-set value as a no-op to avoid the immediate retry. Because it skipped an explicit refresh, downstream processing did not schedule the retry in dirty-state cases. The account says you changed the India flow to refresh the parameter regardless of its current value, validated the state transitions end to end, owned the miss in a team retro, and recovered affected customers.
- **Mandate-reset rebuild:** `BiggestFailure.md` describes a later Ordering-team change that required mandate-reset flows to be rebuilt and says the response took multiple days. It presents this as the fallout from the workaround but does not establish whether it is the same failure mode as the “Deleted” flag or dirty-state account.

The repository does not prove whether these are different descriptions of one incident, successive failures, or related but distinct failure modes. Keep the accounts separate unless you can verify the production timeline and root cause.

## Results and learning

The sources report that the customer-facing update flow launched and worked for an extended period. One says 18 months; another says nearly two years. The failure narratives also disagree on when the later failures began: one places them about a month after launch, while others describe a much longer period of operation before the platform-state issue. Treat the exact timeline as unresolved.

The quantified result belongs specifically to the “Deleted” status account: a 5–10% failure pattern reportedly returned to zero after the flag check was corrected. The dirty-state account reports recovery of affected customers but does not give a failure-rate or customer-count metric. Avoid presenting either metric as the result of both fixes.

The retrospective in the sources is that the workaround solved the immediate business need but left hidden fragility and technical debt. The learning is to make the trade-off and platform dependency explicit, align with platform owners, and consider whether a phased or longer-term architectural solution is preferable. The dirty-state story adds a specific technical lesson: model shared APIs as state machines, not just values, and test idempotency and implicit states such as “dirty” rather than assuming that repeating an update is a no-op. It says you later applied that lesson to another integration where resending a payload cleared an internal processing flag.

## Sources and caveats

- `behaviour-round/amazon-office-work/update-payment-method.md` — feature goal, RBI constraint, event-suppression workaround, and deleted-status failure account.
- `behaviour-round/src/Obstacle.md` — cross-team investigation and the reported 5–10%-to-zero recovery.
- `behaviour-round/non-tech-skills/BiggestFailure.md` — customer-population estimate, business/architecture trade-off, platform change, and technical-debt reflection.
- `behaviour-round/non-tech-skills/Time-constraints.md` — split retail/digital pipelines, open-order and atomicity constraints, and scheduled-retry explanation.
- `behaviour-round/non-tech-skills/Priortize-short-term-over-long-term.md` — workaround rationale, timeline, and long-term maintainability lessons.
- `behaviour-round/non-tech-skills/judgement-error.md` — dirty-state failure account, refresh/state-transition fix, recovery, and idempotency lesson.
- `behaviour-round/non-tech-skills/Summary.md` — brief story-bank reference to a renewal failure involving a missing payment preference.
- `behaviour-round/amazon-office-work/intro.md` — general role context on Prime recurring charges and payment instruments.

These are interview-preparation accounts rather than independent production records. Verify your precise role, sequence of incidents, failure timeline, affected-customer count, and measured outcomes before using them as one interview story.
