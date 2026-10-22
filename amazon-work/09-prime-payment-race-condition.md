# Prime signup payment race-condition fix

## Summary

You fixed a timing race in the India Prime first-subscription payment flow that affected never-Prime customers. The payment path could redirect a customer to OTP before the subscription ID had been created.

## Problem

The story estimates that around 10% of roughly 850 daily UPI customers were failing in the affected flow. The resulting cancellations and stale mandates hurt Prime acquisition. The issue was intermittent and crossed AIPS, PFW, APX, and Prime system boundaries.

## Your documented contribution

- Added a `GetSubscriptionIdStage` to the AIPS backend execution flow so the subscription ID was written before advancing to OTP.
- Added a configurable delay and used a Weblab experiment with 1-, 3-, and 5-second treatments to understand the consistency/latency trade-off.
- Added logic to identify never-Prime customers.
- Refactored payment-source handling to update client references safely.
- Added fail-safes to abort the flow cleanly when the required subscription data was still unavailable.
- Wrote more than 300 lines of unit tests and coordinated sequence changes with partner teams.

## Reported outcome

The story says the race was eliminated for UPI and card flows, stale-mandate problems and escalations dropped near zero, and the stage became reusable in future flows. It says payment success improved, but gives no final percentage.

## Details to verify

Confirm the exact baseline/failure rate, experiment outcome, latency cost, launch date, and whether “eliminated” is based on a measured post-launch interval.

## Sources

- `behaviour-round/src/hardestChallenge.md`

