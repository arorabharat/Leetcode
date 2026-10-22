# PONYS: PO Not Yet Shipped

## Summary

PONYS addressed cases where purchase orders that sellers had not shipped appeared as received in Amazon systems, preventing sellers from shipping through the portal and creating support, delay, and payment-accuracy problems.

## Your documented contribution

- Worked with a senior PM and seller-experience stakeholders to understand complaints and root causes.
- Analyzed seller complaints and explored algorithmic ways to identify PONYS cases.
- Simulated candidate parameters against one year of historical data in the data lake.
- Designed a virtual-resolution/sidelining approach modeled on an existing NO_PO flow.
- Worked with ICQA on SOPs and an SDE1 on implementation.
- Rolled out to EU fulfillment centers first, then attempted expansion to the US.

## Failure and learning

The EU launch was smooth, but US fulfillment-center operations differed from assumptions; even the NO_PO reference flow did not behave consistently there. After five months of analysis and implementation, the US expansion failed and the project was rolled back.

The documented lesson was to validate ground-truth operational workflows directly across regions, even when documentation, code, or existing processes suggest a shared model. The notes say this learning influenced the later DoR project's dry runs and simulators.

## Details to verify

The sources do not quantify the number of sellers/orders affected, false-positive rates, or final operational impact in EU. Keep the rollback and learning central rather than presenting this as a successful launch.

## Sources

- `behaviour-round/non-tech-skills/AbhirajInterviewQuestion.md`

