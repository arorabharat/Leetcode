# Prime stack-plan and auto-renew upsell

## Summary

You led a team of three SDEs to launch a Prime retention offer: a stack plan and auto-renew upsell presented on high-traffic surfaces, including the Amazon homepage.

## Customer and business problem

The detailed homepage-stacking story describes an offer for Prime customers in the last 30 days of membership whose auto-renewal was off and who were not on a third-party-paid subscription (for example, a Jio bundle). The offer provided a ₹100 discount toward the next cycle. The resume describes the broader program as a stack-plan and auto-renew upsell intended to grow retention sign-ups on high-TPS surfaces.

A downstream risk service precomputed customer risk/eligibility values so the offer could be served at homepage traffic levels. The initial scope was primarily UI work: replacing an auto-renew prompt with a stacking prompt.

## Your documented contribution

- Led three SDEs through delivery.
- Launched the stack-plan and auto-renew upsell on high-TPS surfaces.
- Authored the design and coordinated the launch; the detailed launch story says execution began with one other SDE.
- During QA, found that the risk service did not refresh after stacking signup, so customers could continue seeing the offer after purchasing.
- Unblocked launch by adding a controller-level check against the source of truth rather than relying only on the stale precomputed risk value.
- Applied the principal engineer's feedback on stakeholder alignment to later launches: document the use case and integration pattern, open tickets/SIMs early, and align downstream owners on the use case as well as TPS.

## Reported outcome

The resume reports daily sign-ups increasing from 500 to 2,000, approximately 620K incremental sign-ups per year, and about $6M in annual revenue impact.

## Details to verify

The detailed stacking story identifies the homepage surface and eligibility above; it does not describe experiment design or how the resume's annual impact was calculated. The repository does not establish whether the resume's sign-up/revenue metrics are attributable to this exact first homepage stacking launch or to the broader retention program. The resume says you led three SDEs, while the learning story describes beginning execution with one other SDE; confirm the scope and team relationship before using both details together. The separate bottom-sheet story may be another upsell placement and should not automatically be treated as this launch.

## Sources

- `resume-v1/src/experience.tex`
- `behaviour-round/non-tech-skills/CriticalFeedback.md` (detailed stacking-launch story, QA issue, fix, and learning)
- `interveiw/linkedin/my-answer.md` (mentions stacking upsells across homepage locations in the Galileo context)
- `behaviour-round/non-tech-skills/Summary.md` (story-bank label only)
- Potentially related, but not confirmed as the same launch: `behaviour-round/src/Conflict.md` (Prime upsell in the Amazon Pay-owned homepage bottom sheet)
