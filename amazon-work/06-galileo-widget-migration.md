# Prime retention-widget migration to Galileo

## Summary

You migrated Prime retention widgets on the Amazon retail website from the legacy MS3 technology to Galileo, a newer rendering architecture.

## Context and technical motivations

The notes describe Galileo as a framework for a unified customer experience across UI placements, with request coalescing, asynchronous/parallel downstream calls, bulkhead-style isolation, caching, and more marketer-controlled UI experimentation. This mattered for high-traffic retail surfaces and campaigns such as Prime Day, where marketers wanted to adjust banners or themes without an engineering change.

## Your documented contribution

- Expanded from backend-focused Prime fulfillment and payments work into UI architecture and widget integration.
- Learned an evolving framework with incomplete documentation and collaborated with the team building Galileo to understand its patterns and limitations.
- Migrated Prime retention widgets, paying attention to interactions between frontend components and backend services, peak traffic, dependency scaling, race conditions, and distributed state.
- A separate interview account says you initially proposed a single Prime-service entry point to reduce repeated downstream integrations and scattered business logic. A Principal Engineer pointed out that a broader framework would unify backend aggregation and customer experience across UI placements. You accepted that direction, learned the framework, and later led migration of most widgets to it.

## Reported outcome

The story says all Prime retention widgets were successfully migrated and that almost all retention widgets on the retail website were later rendered through Galileo, handling approximately 2,000–3,000 TPS at peak. The LinkedIn interview draft says you migrated “most” widgets about a year after the initial architecture proposal was not accepted; the notes therefore differ slightly on the migration scope and timeline.

## Details to verify

The document does not identify the precise number of widgets, rollout phases, latency or reliability results, or which optimizations you personally implemented. Confirm the “all” versus “almost all” scope and whether the 2,000–3,000 TPS figure refers to the widgets, the page, or the architecture.

## Sources

- `behaviour-round/non-tech-skills/AdaptivePersonality.md`
- `interveiw/linkedin/my-answer.md` (architecture proposal, feedback, Galileo learning, and later widget migration)
- `behaviour-round/non-tech-skills/Summary.md` (story-bank label “Retention Galielo migration”; no additional implementation details)
