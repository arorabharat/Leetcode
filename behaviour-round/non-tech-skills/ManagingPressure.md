1. Multiple deadline hitting once.
2. Manage expectation
3. Stay Organised and improve productivity
4. Certain tech changes or business changes which put you under pressure.
5. keep everyone motivated,


# Managing Pressure During a Critical Multi-Region AWS Migration

## Situation

I owned the migration of the *Fire and Forget Service* — a business-critical asynchronous event-processing pipeline (SNS → SQS → Lambda → persistence layer) — from one AWS region to another, with a hard three-week deadline.

The core challenge wasn't the migration itself, but the state of the infrastructure. The service had been built rapidly under earlier operational pressure, leaving it partially managed: some resources lived in CDK stacks, others had been created manually in the AWS console. A naive "deploy the stack in the new region" approach would have caused configuration drift in production.

On top of that, the migration had to be done live — no message loss, minimal latency impact, and full operational visibility throughout the cutover.

## Task

Deliver the migration in three weeks with zero customer impact. Concretely, that meant: reconciling the CDK-vs-manual infrastructure gap, replicating the stack in the target region, migrating live traffic safely, and ensuring rollback was possible at every step.

## Action

I ran the work in parallel tracks rather than sequentially, which is what made the deadline feasible.

**1. Infrastructure audit.** Before touching anything, I built a resource comparison matrix between what CDK declared and what actually existed in AWS — Lambda configs, SNS subscriptions, SQS settings, DLQs, IAM roles, event source mappings, CloudWatch alarms. This surfaced every manually provisioned resource that would otherwise have been missed.

**2. Understanding the existing CDK model.** Rather than patching ad hoc, I traced how the current stack synthesized — IAM generation, SNS/SQS wiring, cross-stack references, environment bootstrapping. This let me extend the deployment model cleanly for the second region.

**3. Phased dual-region deployment.** Instead of a hard cutover, I stood up the full stack in the target region and ran both regions simultaneously. This gave us side-by-side metrics on latency, queue depth, and processing behavior before shifting traffic.

**4. Operational readiness.** Cross-region latency meant CloudWatch thresholds and alarms needed recalibration to avoid false positives. I also worked through IAM carefully — some permissions are region-independent, others needed explicit duplication — validating Lambda execution roles, SNS/SQS policies, and cross-service trust before any traffic moved.

**5. Execution under time pressure.** I split the work into independent workstreams (infra setup, validation, alarm migration, traffic readiness) so multiple things could progress at once, coordinated with other engineers for parallel validation, and used AI tooling to speed up infrastructure analysis and doc review. Every migration phase had a defined rollback checkpoint.

## Result

Migration completed on time, with no production incidents and zero message loss.

Beyond hitting the deadline, the project left the service in a better state than it started: previously unmanaged resources are now documented, alarms and IAM are correctly scoped for the new region, and the team has a clearer picture of where infrastructure drift exists. The dual-region approach also became a reusable pattern for future migrations.

---

A few notes on what I changed and why, in case useful for an EM audience:

- **Cut the repetition.** The original repeated the SNS/SQS/Lambda/IAM list in several places — once is enough.
- **Led with the *real* problem.** For an EM, the headline isn't "I migrated a service," it's "infrastructure was partially IaC-managed, so the migration was actually a reconciliation problem." That's the judgment call they'll care about.
- **Tightened the Result.** The original had eight bullets that mostly restated the Action. EMs scan for: did it ship, what was the impact, what's better now.
- **Kept the parallelization point.** That's the strongest signal of how you manage pressure — worth keeping prominent.

Want me to push it shorter (1-minute spoken version), or tailor it to a specific leadership principle like "Deliver Results" or "Ownership"?