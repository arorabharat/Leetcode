# Revised-payment recovery journey

## Summary

You improved the Prime payment-failure recovery journey so customers could discover and reach the revised-payment page sooner.

## Customer problem

After a Prime signup payment failed, customers reportedly had to navigate three or four additional clicks to find the page where they could correct payment details. The existing upsell flow relied on the ordering system to detect failures, but that state could take 5–10 minutes to update, leaving a gap where no recovery prompt appeared.

## Your documented contribution

- Compared the India recovery journey with Worldwide Prime and Checkout Prime experiences to identify the customer-experience gap.
- Built a precomputed REST state that fetched payment status from the Payments service rather than waiting for the eventually consistent ordering state.
- Integrated that state into upsell workflows so failure-recovery prompts could appear closer to the actual payment failure.
- Changed the customer journey to direct failed-payment customers to the revised-payment page, eliminating the multi-click discovery path.

## Reported outcome

The story reports a 20–30% reduction in revised-payment failure drop-off and a 2% improvement in overall Prime signup success.

## Details to verify

Confirm the measurement windows, baseline definitions, whether the two result metrics are experimentally attributed, and which teams owned the source payment-status API and redirect flow.

## Sources

- `behaviour-round/non-tech-skills/CustomerObession.md`

