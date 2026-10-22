# Diwali payment-method throttling RCA

## Summary

You owned the root-cause analysis for a payment-method service throttling incident during a Diwali sale, a high-velocity event. Throttling began within roughly 10–15 minutes of launch and had appeared during previous high-traffic events.

## Investigation complexity

The incident overlapped with a service scaling issue and a separate payment-service-provider failure. The task was to separate the payment-method throttling cause from concurrent symptoms, trace the request path, and produce a useful RCA within a 30-day deadline.

## Your documented contribution

- Went beyond your prior high-level understanding by reading code, tracing architecture, and correlating logs across services.
- Worked with the owning team to obtain logs and clarify system behavior.
- Isolated the throttling issue from the scaling and PSP failures and documented two or three remaining root-cause possibilities with evidence.
- Presented an initial RCA and received feedback that readers unfamiliar with the service could not follow the document.
- Accepted the feedback, booked a follow-up review, and rewrote the RCA with service architecture and first-principles context. You used AI tooling to accelerate drafting contextual sections.

## Outcome and caveat

The revised document was well received and reportedly became a useful architecture reference. The story says the recurring throttling issue was addressed in subsequent events, but does not specify the confirmed root cause or the production fix. Do not claim a specific fix without confirming it.

## Sources

- `behaviour-round/non-tech-skills/CriticalFeedback.md`

