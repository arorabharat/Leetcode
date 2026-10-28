# PhonePe Schemate: launch, rollout, and post-launch story (HYPOTHETICAL)

**Every specific in this document is hypothetical.** The incidents, numbers, names of phases, and timelines are plausible inventions added to round out the story in `schemate-work-narrative.md`. They are not from my real account. Before using any of it in an interview, replace each detail with what actually happened, or drop it. Anything I describe here should still be consistent with the real design: a build-time Maven plugin, annotated classes, a schema registry with compatibility levels, and a change-set approval workflow on each merge request.

**Current status in this story: Schemate is in a gradual rollout.** It has launched for a few clients so far, with more teams being onboarded in waves. It is not yet enforced across the whole ecosystem, so the numbers below are early, small-scale figures, and the rest of the rollout is described as the plan.

---

## 1. The QA launch blocker

**The setup.** In our design, a schema version is registered when the merge request merges, and it is activated when the service deploys. In dev we tested this on a single instance with a successful deploy, and it worked. QA is where we ran it the way production runs: a service with 10 pods, a rolling deploy, and a consumer service running against the same topic.

**The scenario we tested.** A pilot service publishes a `PaymentStatusUpdated` event, currently at version 5. A change adds one new field, `settlementMode`, with a default value. It is backward compatible, passes the compatibility check, gets approved, and merges, so version 6 is registered. The deploy starts. The first pod comes up, and our activation hook, which fired when the first pod started, marks version 6 as ACTIVE in the registry. In the test, we then made that pod fail its health check, so the rolling deploy stopped and rolled back to the old build. All 10 pods were running version 5 code again and emitting version 5 events.

**What went wrong.** The registry still said version 6 was the active schema, and nothing in production had ever emitted a version 6 event. That produced two real problems:

1. **Consumers trusted a schema that did not exist on the wire.** The catalog showed version 6 as current, so a consumer team that built against it treated `settlementMode` as always present. Every event still being published at version 5 failed in their deserializer. In the test, the consumer started rejecting 100 percent of live events from that topic, and in production that would have been a payments-status outage downstream.
2. **A phantom baseline.** The next merge request from another team was compatibility-checked against version 6, a version that had never run. Compatibility checks were being done against a schema nobody was publishing, so their result was meaningless.

**Why dev did not catch it.** It is a distributed-systems problem. It needs multiple instances, a rolling deploy, a deploy that fails partway, and a consumer reading live data. A single-instance happy-path test cannot show it. During any normal rolling deploy there is also a window, a few minutes with 10 pods, where some pods emit version 5 and others version 6. Consumers have to tolerate both, and the topic also retains several days of older events, which is the reason we require backward and forward compatibility in the first place.

**I treated it as a launch blocker.** The root mistake was treating "a deploy started" as "the schema is live", when the registry and the deployment are two separate systems and the deploy can still fail or roll back. We held the pilot date for about a week.

**The fix.**
- **Version lifecycle.** A version now moves through states: REGISTERED when it merges, then ACTIVE only after the deploy is fully healthy (all pods on the new build, health checks passing), and it goes back to its previous state if the deploy rolls back. The activation is triggered by the deployment pipeline's success signal, not by the first pod starting.
- **Compatibility baseline.** Checks run against the last ACTIVE version, and for topics with long retention against all earlier ACTIVE versions, so a version that never shipped can never become the baseline.
- **Consumers read what is live.** The catalog and client lookups show ACTIVE versions only. REGISTERED versions are visible as "pending."
- **Chaos test.** We added a QA test that deploys a service with an injected failing health check and asserts that the registry state afterwards matches what is actually running. We also test a mixed-version window, with half the pods on the old version and half on the new one, with a consumer reading both.

## 2. The gradual dial-up and production launch

I did not want to switch this on for hundreds of services at once, so we planned the launch in stages, with the ability to turn the platform off for a service at any point.

**Stage 0: shadow mode, three pilot services.** We picked three services owned by teams that trusted us, with a mix of simple and complex event classes. In this stage the plugin ran in report-only mode. It generated schemas and ran compatibility checks, and posted the result on the merge request, but it never blocked a merge and never required approval. The aim was to find out how noisy and how accurate the checks were against real code. This stage is also where we confirmed that the QA fix worked: deploys went through the new lifecycle without leaving the registry out of step with what was running.

**Stage 1: enforcement on the pilot services.** Once shadow mode looked clean, we turned on enforcement for the same three services. Compatibility failures now blocked the merge, and breaking changes needed approval from the affected consumers. We watched this for two weeks, looking at how long approvals took and how often people were blocked.

**Stage 2: gradual rollout to more clients (where we are now).** After the pilot, we launched for a few more clients, so that about six services are currently enforcing schema checks, and a few more are in report-only mode. We are deliberately going slowly. The plan is to onboard in small waves of a few services at a time, starting with the services that publish the most widely consumed events, since they carry the most risk and benefit the most. Each service has its own switch between report-only and enforcing, so a team that hits a problem can be moved back to report-only without affecting anyone else, and I can do that from our side without needing their deploy. For each wave we run a short onboarding session, and I make sure the integration is only an annotation and a plugin entry in their build file.

**Checkpoints before each wave.** Before adding clients, we look at a short list: the registry's latency and error rate since the last wave, the number of support questions, and the number of merge requests blocked and for what reason. If any of them looks bad, we pause the next wave. So far we have not needed to pause, but we have slowed the planned wave size once because the approval queue was getting long for one popular event.

**What comes next.** We will widen the waves as confidence grows, and the long-term plan is to cover the hundred-plus services that publish and consume events. That is still ahead of us, so I describe the platform as launched for a few clients and in gradual rollout, not as fully adopted.

## 3. The post-launch production issue

About six weeks after the first services went live with enforcement, we had our first real production-side issue. It happened on a busy day with lots of merges.

Two teams, working on the same shared event, each raised a merge request on the same day. Both changes added an optional field and were independently compatible with the version currently in the registry, version 5. Both change sets were generated against version 5, both passed the compatibility check, and both got approved. The first merge request applied its change set cleanly and created version 6. When the second one tried to apply its change set, which had been built on top of version 5, it did one of two bad things depending on the timing: in one case it failed halfway with a confusing error, and in another it would have created a version 6 that did not include the first team's field, effectively dropping it.

What saved us is that the apply step was atomic, as I had designed it, so the registry was never left half-updated and no consumer saw a corrupt schema. The failure showed up as a blocked merge request, with no production event affected. But it was a design gap: the compatibility check and the approval were done against a snapshot, and by the time the change was applied the world had moved on.

I found it from the alert on failed apply operations, reproduced it in a test environment by running two change sets against the same base version, and fixed it in two steps. First, the apply step now uses an optimistic version check: a change set records the base version it was built on, and the registry rejects it if the latest version is no longer that one. Second, when that happens, the system automatically re-runs the compatibility check against the new latest version, and if it still passes, asks for a quick re-approval rather than starting from scratch. We also improved the error message so the team sees "Your change was built on version 5; version 6 was published in the meantime, rebase and re-run" instead of an internal error. In the following weeks I added a metric for "apply conflicts" so we could see how often this happened. With only a few clients so far it has been rare, two conflicts in the first couple of months, but it confirmed that the fix worked and that the case will become more common as more teams onboard.

I took one lesson from this. Atomicity protects you from partial writes, but it does not protect you from stale decisions. Anything that is checked and approved at one moment and applied at another needs a version check at apply time.

## 4. Metrics and scale (all hypothetical, early rollout)

These are the numbers I would use to describe the platform at this stage, with the caveat that each one needs to be replaced by a real figure. Because we are only in the early part of the rollout, the platform metrics are small, while the scale of the problem it addresses is large.

- **Scale of the problem:** billions of events a day, with around a million events generated at peak moments, and hundreds of downstream consumers. This is the ecosystem Schemate is being rolled out into, not what is covered today.
- **Clients launched:** about 6 services enforcing schema checks, and about 4 more in report-only mode, out of the hundred-plus that publish or consume events. That is under 10 percent of services so far.
- **Event types registered:** about 90 event types and around 250 schema versions across those services, roughly three versions per event on average.
- **Merge requests checked:** about 40 a week at the current scale, of which roughly 10 percent produced a compatibility failure that the author fixed before merge.
- **Breaking changes prevented:** about 6 changes blocked before reaching production so far, each of which would have broken at least one downstream consumer. I would frame this as "changes that would have broken a consumer", and not claim a specific number of incidents prevented.
- **Approval turnaround:** a median of about 3 hours for a change set, and 90 percent within a working day. We track it because slow approvals are the main source of friction.
- **Registry performance:** schema lookups served from a client cache, with a registry p99 latency of a few tens of milliseconds and availability above 99.9 percent. Because registration happens at build time, none of this is on the event path.
- **Onboarding effort per service:** one annotation per event class and one plugin entry in the build file, typically under half a day of work per team.
- **Schema-related incidents on onboarded services:** none since enforcement began. With so few services, this is encouraging but not yet statistically meaningful, and I would say so.
- **Support load:** about 15 questions in the first week of the pilot, falling to about 3 a week now, mostly "why was my change blocked."

## 5. How this connects to the main narrative

The QA blocker shows that I test the platform the way production runs, with multiple instances and failed deploys, and that I'll hold a date for it. The gradual rollout to a few clients, with per-service switches and a report-only mode, shows how I reduce risk for something that sits next to payment traffic. The post-launch issue shows the design in action: the atomic apply stopped it from becoming a production problem, and the fix extended the model with a version check. All three tie back to the same principle in the main story: producers own their changes, failures stay local, and the platform stays out of the hot path.
