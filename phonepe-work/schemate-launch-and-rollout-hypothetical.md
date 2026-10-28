# PhonePe Schemate: launch, rollout, and post-launch story (HYPOTHETICAL)

**Every specific in this document is hypothetical.** The incidents, numbers, names of phases, and timelines are plausible inventions added to round out the story in `schemate-work-narrative.md`. They are not from my real account. Before using any of it in an interview, replace each detail with what actually happened, or drop it. Anything I describe here should still be consistent with the real design: a build-time Maven plugin, annotated classes, a schema registry with compatibility levels, and a change-set approval workflow on each merge request.

**Current status in this story: Schemate is in a gradual rollout.** It has launched for a few clients so far, with more teams being onboarded in waves. It is not yet enforced across the whole ecosystem, so the numbers below are early, small-scale figures, and the rest of the rollout is described as the plan.

---

## 1. The QA launch blocker

**The irony we had to face.** I had argued against runtime validation because it would put a system we own in the path of every event. Build-time capture moved our dependency from the event path to the build path: every merge request in every onboarded service now calls the registry to extract, check and register schemas. In QA, the load test showed that we had not removed the central dependency, only moved it, and that it could be just as damaging. If the registry is slow or down, every team's builds go red, including a hotfix for a payments incident.

**The load test.** Functional tests had passed with a handful of builds. Before the pilot we load-tested the registry at the volume we expect once all hundred-plus services are onboarded: about 200 builds hitting it concurrently, which is what a busy mid-morning merge window looks like, with the compatibility check running in transitive mode. Three things broke, and they compounded:

1. **The compatibility check was too expensive.** Every call loaded all prior versions of the subject from the database, parsed each schema from scratch, and ran the checks. With transitive checks across a dozen versions, p99 latency climbed to about 18 seconds at 200 concurrent builds, and the database connection pool, sized at 50, was exhausted.
2. **Our own plugin turned slowness into an outage.** On timeout the plugin retried five times with a fixed one-second delay and no jitter. Once the registry slowed down, all 200 builds retried in lockstep, which multiplied the load by roughly five. Health checks started failing, instances restarted, their in-memory caches were cold, and the recovery made it worse. This is a classic retry storm leading to a cascading failure.
3. **There was no defined behaviour when the registry was unavailable.** The plugin failed the build whenever it could not reach the registry. That is the right default for governance, but it meant a registry blip blocked deploys everywhere, with no way for an engineer to ship an urgent fix.

**Why it was a blocker.** Teams would have experienced Schemate as the thing that randomly breaks their builds. A governance platform that reduces availability of the services it is supposed to protect would have lost the argument I had made to leadership about risk, and rightly so. We held the pilot date for about two weeks.

**The fix, at the platform level.**
- **Most builds should not call the registry at all.** The plugin hashes the schemas it generates and compares them with the exact versions pinned in the manifest. In roughly nine out of ten builds nothing about the event classes changes, so the registry is never called. Only a build that actually changes a schema makes the compatibility and registration calls. This cut the steady-state load by an order of magnitude.
- **Make the check cheap.** The registry keeps parsed schemas and a precomputed compatibility state per subject in memory, invalidated only when a new version is written. Reads and compatibility checks are served from replicas, and writes go through a single primary per subject with a per-subject lock, so one busy subject cannot block another.
- **Make the clients well behaved.** The plugin now uses exponential backoff with jitter, a small bounded number of retries, a request timeout, and a circuit breaker, so a slow registry is not hit harder by its own clients.
- **Protect the platform from its tenants.** Compatibility checks, registration and catalog queries run in separate thread pools, so a spike in one cannot starve the others. Each team has a rate limit, and the registry sheds load with a clear "retry later" response instead of falling over.
- **Decide the failure policy deliberately.** If the registry is unreachable, builds with unchanged schemas proceed, since they never needed it. Builds that change a schema stay blocked, because approving an unchecked change is exactly the risk we exist to prevent. For genuine emergencies, an on-call engineer can use an audited override that lets the build proceed, and a follow-up job checks the change afterwards and alerts the owners if it was incompatible.
- **Make it a release gate.** The load test, including a registry-down test, now runs before every registry release, and we set SLOs, for example a p99 of under 500 milliseconds for a check, which we alert on.

After the fix, the same 200-build test brought p99 down from about 18 seconds to about 300 milliseconds, with no retry amplification. When we killed the registry mid-test, unchanged builds were unaffected and changed builds failed fast with a clear message.

## 2. The gradual dial-up and production launch

I did not want to switch this on for hundreds of services at once, so we planned the launch in stages, with the ability to turn the platform off for a service at any point.

**Stage 0: shadow mode, three pilot services.** We picked three services owned by teams that trusted us, with a mix of simple and complex event classes. In this stage the plugin ran in report-only mode. It generated schemas and ran compatibility checks, and posted the result on the merge request, but it never blocked a merge and never required approval. The aim was to find out how noisy and how accurate the checks were against real code. This stage is also where we confirmed that the QA fix worked: the load test and the registry-down test kept passing as we added clients.

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

The QA blocker shows that I treat the platform's own availability as a launch requirement, since I had argued about the risk of a central dependency, and that I'll hold a date for it. The gradual rollout to a few clients, with per-service switches and a report-only mode, shows how I reduce risk for something that sits next to payment traffic. The post-launch issue shows the design in action: the atomic apply stopped it from becoming a production problem, and the fix extended the model with a version check. All three tie back to the same principle in the main story: producers own their changes, failures stay local, and the platform stays out of the hot path.
