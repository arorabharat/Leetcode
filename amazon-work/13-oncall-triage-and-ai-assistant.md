# On-call triage tool and AI assistant

## Summary

You first created a lightweight tool to aggregate on-call investigation data in an existing internal UI, then designed a more automated ticket-assistant approach with other engineers.

## Problem

Prime orchestration incidents required engineers to consult six or seven downstream tools and manually assemble context. One story says collecting data took 15–20 minutes per ticket and end-to-end resolution approached an hour.

## Phase 1: browser-based aggregation

- Used Tampermonkey to add a unified view to a frequently used internal page.
- Investigated the underlying JSON/API calls used by internal tools.
- Aggregated relevant information from downstream services and rendered it in one place.
- Rolled the script out to the team and iterated based on feedback.

The purpose was a quick, low-cost improvement without waiting for a backend platform investment. The notes claim data gathering dropped to under a minute in one account and to 2–3 minutes in another; treat those as conflicting draft measurements.

## Phase 2: AI ticket assistant

- Spoke with teams working on similar on-call problems.
- Designed an architecture using an internal ticket-agent framework.
- Proposed gathering downstream context when a ticket arrived, applying documented SOPs, and posting a summary and suggested next steps as a ticket comment.
- Included a path to classify tickets without an existing SOP as unstructured so the team could expand coverage.
- Partnered with two SDEs on implementation; the notes say you owned architecture and unblocking.

## Reported outcome and caveats

One account reports resolution time falling from about one hour to 5–10 minutes. The documents do not clearly establish the AI assistant's rollout status, evaluation accuracy, human-approval model, or broad adoption. Describe the script and AI assistant as separate maturity phases, and verify which results were measured in production.

## Sources

- `behaviour-round/non-tech-skills/Innovation.md`
- `behaviour-round/non-tech-skills/Innovation-2.md`

