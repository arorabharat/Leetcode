# Prime notification scheduler

## Summary

The resume says you designed and built a communication-channel-agnostic Prime notification scheduler.

## Documented scope

The scheduler orchestrated approximately 150K notifications per day. The resume associates the program with 1.2K–1.5K sign-ups per day. The broader context in the resume is Prime retention and subscription acquisition.

## Your documented contribution

- Designed the scheduler.
- Built the scheduler.
- Made it independent of a specific communication channel, so the scheduling/orchestration capability was not tied to one channel.

## What the repository does not specify

There is no detailed design or implementation narrative in the repository describing event triggers, scheduling semantics, delivery guarantees, deduplication, customer eligibility, channel adapters, retry policy, observability, or how sign-ups were attributed to the notifications. Do not infer these details from the phrase “channel agnostic.”

## Interview preparation

Before using this as a deep technical story, fill in the event lifecycle, service boundaries, scale/latency constraints, failure handling, your individual design decisions, and how the sign-up metric was measured.

## Sources

- `resume-v1/src/experience.tex`
- `behaviour-round/system-thinking.md` (contains only a prompt to prepare this story)

