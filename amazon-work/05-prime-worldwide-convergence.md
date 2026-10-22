# India and Worldwide Prime convergence

## Summary

You initiated a cross-organization effort to reduce divergence between India Prime and Worldwide (WW) Prime, while retaining India-specific regulatory and customer requirements.

## Problem

The interview notes say India used some foundational Worldwide services but had diverged in its subscription and retention flows. Conflicting requirements made it difficult to adopt Worldwide features, slowed delivery, and led to leadership escalation. India-specific examples included OTP collection through a bank page, while Worldwide could activate a subscription asynchronously.

## Your documented contribution

- Identified pain points across Prime signup layers: UI/upsell, controller/data model, data providers, and fulfillment.
- Proposed two architecture principles: treat requirements as features rather than marketplace-specific customizations, and implement features as plug-and-play modules rather than scattering marketplace-specific logic.
- Proposed modular APIs to support India-only steps such as OTP while retaining a compatible shared architecture.
- Initiated a bi-weekly collaboration forum between India and Worldwide product teams to surface upcoming features and align earlier.
- Proposed a configuration store as a source of truth for marketplace feature enablement.
- Advocated an experiment-driven model so a feature could be validated in one marketplace and considered elsewhere based on results.

## Reported outcome

The notes say product/engineering alignment previously took around two weeks and could be reached in one or two meetings after the collaboration changes. The resume describes a leadership-approved roadmap for a pluggable marketplace solution intended to enable future Worldwide launches in India without marketplace-specific customizations.

## Status and caveats

The source describes a roadmap and operating-model improvements; it does not clearly state which proposed services or modules were fully implemented. Be precise about separating delivered process changes from planned architecture.

## Sources

- `behaviour-round/non-tech-skills/CurrentProject.md`
- `resume-v1/src/experience.tex`

