# Me Tab Prime upsell and reusable components

## Summary

An interview-preparation note describes work to launch a Prime retention upsell on a new page (the Me Tab) and standardize the components required to build upsells, reducing repeated developer effort.

## Intended customer experience

The proposed widget targets customers in the final 30 days before plan expiry who do not have autopay enabled. It offers a discounted plan for the next billing cycle, with retention as the primary objective.

## Architecture described in the notes

The notes break the experience into four functional layers:

1. **Widget rendering:** show the offer and its customer-facing content.
2. **Eligibility:** evaluate subscription status, expiry timing, and related signals to determine whether and which offer applies.
3. **Data aggregation/controller:** provide the UI with a dynamic model containing plan price, savings, discount, and other offer details.
4. **Action and payment integration:** enable purchase through supported payment methods. The existing payment API was available, but integration effort was high; the work included stakeholder alignment on standard parameters and an adapter library to transform requests into the required format for reuse across placements.

## Your documented contribution

The notes describe you as working on the system design/integration and on standardizing the process across India and Worldwide Prime product teams. The intended organizational benefit was reusable, plug-and-play upsell features rather than repeated marketplace-specific implementation.

## Status and caveats

This is a current-project description, not a final launch report. The notes do not establish which layers were completed, whether the Me Tab placement launched, or any measured conversion or retention outcome. It may be a specific workstream within the broader India/Worldwide convergence effort.

## Sources

- `behaviour-round/non-tech-skills/CurrentProject.md`

