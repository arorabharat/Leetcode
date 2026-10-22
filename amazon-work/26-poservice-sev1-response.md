# POService Sev-1 response

## Summary

During the DoR project, you helped respond to a weekend Sev-1 outage in POService, a Tier-1 dependency for inbound, transshipment, and outbound fulfillment-center processes. The service was down across US fulfillment centers, with CPU saturation, brownouts, and client timeouts.

## Your documented contribution

- Took over analysis after an SDE3 contacted you, despite not being on call.
- Used service metrics and alarms to identify CPU saturation, helped scale the service, and implemented an immediate input-limit change after traffic analysis (the notes cite 500 versus 5,000+ POs).
- Traced a longer failure chain involving an RPOS API timeout reduction, Watson workflow failures, more than 2,000 failed workflows, mass retries, and increased DynamoDB capacity that removed prior throttling protection.
- Wrote a detailed CoE/RCA with impact analysis and short-, medium-, and long-term prevention actions.
- Kept the DoR regulatory work moving by onboarding an SDE2 and separating work that could continue during incident response.

## Reported outcome

The story says service stability was restored, the CoE received praise from three Principal Engineers and a Director, and the DoR timeline was maintained during a month of incident follow-up.

## Details to verify

Confirm your formal incident role, outage duration/customer or operational impact, the exact immediate mitigation, and which long-term actions were implemented. Avoid presenting a draft causal chain as final unless the RCA confirmed it.

## Sources

- `behaviour-round/non-tech-skills/AbhirajInterviewQuestion.md`

