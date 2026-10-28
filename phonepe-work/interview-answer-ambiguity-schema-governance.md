# Interview answer: leading through ambiguity and technical trade-offs (PhonePe Schemate)

## Question

We've talked about systemic operational fixes and guiding other engineers. Now I want to look at how you navigate high-stakes ambiguity and technical trade-offs yourself.

Can you tell me about a time when you were leading a project with vague requirements, conflicting technical directions, or high unknown risks? How did you evaluate the risks, what intelligent technical trade-off or pivot did you make to keep the project moving, and how did you convince stakeholders or leadership that your path was the right one?

## Project used

Schemate, the schema and data governance platform you are building at PhonePe in the observability pod. Source: your own account in this conversation (no repository file).

## How to read this note

Details marked **†** are illustrative additions that were not in your account. Keep them only if true for you. Unmarked details come from what you told me. Your account did not include results or metrics, so the Result section has placeholders for you to fill in.

## Answer (about 3 to 4 minutes spoken)

### Situation

"At PhonePe, billions of events flow through our systems every day, around a million being generated at any moment, and hundreds of downstream services depend on them. For a payments app availability standards are strict, so one incorrect schema, meaning one malformed event, can start failing consumers downstream.

I was building Schemate, a schema and data-governance platform in the observability pod. The goal was that every published event is validated against a schema, that producers and consumers can evolve their schemas independently, and that teams can find the right event for a use case. It also had to be self-service so our infra team wouldn't become a bottleneck.

The requirements were open and the constraints conflicted:
- **Hundreds of existing services.** Industry practice is schema-first: write schema files and generate code models from them. But our services already had their code. Rewriting schemas by hand for hundreds of services, or migrating them to a new toolchain, was unrealistic, and convincing every team to take that risk was hard.
- **Different needs.** Beyond producer-consumer compatibility, we needed to mask critical data before sending events to external parties, and to validate marketing and recommendation data, such as banner widget content, that sometimes contained invalid values."

### Task

"I owned the technical direction. I had to find a way to generate and govern schemas from existing code with almost no onboarding effort for teams, without putting the platform or the payment path at risk."

### Action 1: reduce the unknowns

"This was new territory for us, so I studied the closest existing solution first. Confluent's Schema Registry is the standard for the Kafka ecosystem. I spent about a month understanding how it works end to end and took the lessons that applied, instead of designing in a vacuum."

### Action 2: two conflicting directions, and the trade-off

"We had two options for getting schemas from existing code.

| | A. Runtime capture (leadership's preference) | B. Build-time capture (my proposal) |
|---|---|---|
| How | When an event is published, infer the schema at runtime, cache it, and validate every event | A build plugin reads annotated classes and registers the schema before deployment |
| Client effort | Close to zero: just call our API | Add an annotation to the class |
| Failure mode | We become a central bottleneck for the whole ecosystem, and runtime problems are hard to mitigate | A bad schema fails the build, so it never reaches production |
| Load | Every event is validated by a central system that must scale to peak traffic | Each team does its own work at build time, so no central scaling |
| Blast radius | A problem in the central system hits every service | A team's bad schema affects only that team |

Leadership leaned towards A because it was the least effort for client teams. I was against it because it would put one system in the path of every event on a payments platform."

### Action 3: the design I proposed

"I proposed shifting left, so that producers own their schemas and any problem fails fast:

1. Teams add an annotation to their classes.
2. A Maven plugin scans the code at build time, extracts the schemas and registers them.
3. On each merge request, the plugin checks the new schema against the existing ones. Teams choose a compatibility level (backward, forward or full), which guarantees that no existing consumer breaks.
4. A change set is generated and goes through an approval workflow. The owners of affected schemas review and approve it.
5. Once approved, the change set is applied atomically, and only then can the merge request merge.
6. When the service deploys, its schemas are activated.

For the other use cases, I built shared rules: schema checks and data-masking and transformation rules before data goes to external parties, and validation for marketing-widget data."

### Action 4: convincing leadership

"I didn't argue that the runtime approach was bad in the abstract. I made the trade-off concrete:

- **Risk.** Runtime capture would make us the single point of failure for payment events. Build-time capture isolates failures to the team that made the change.
- **Cost.** Runtime validation needs central capacity for peak traffic. Build-time work uses each team's own build resources.
- **Time to feedback.** The earliest moment a team can learn that a change breaks a consumer is at build time.
- **The objection I took seriously.** The annotation is a small onboarding cost. † I addressed it with a working prototype on one real service so leadership could see how little effort teams had, and by offering to keep the annotation as the only change needed."

### Result

"[Fill in: how many services are onboarded, how many incompatible changes were blocked before reaching production, and any incident reduction.] The approach gave us governed, compatible schemas without rewriting existing services, kept the platform off the event hot path, and let teams own their changes without waiting on us."

### One-line summary if interrupted

"Leadership preferred runtime schema capture because it needed no client effort, but it would have made us a central bottleneck for a payments platform. I proposed capturing schemas at build time with a Maven plugin, compatibility checks and an approval workflow, and I made the case with risk, scale and fail-fast arguments."

## Likely follow-up questions

- **Why not require teams to write schema files first?** Hundreds of existing services, so migration risk and effort were too high.
- **What if leadership still insisted on runtime?** † I'd offer a hybrid: build-time as the default, with runtime validation as a later option for teams that can't use the plugin.
- **How do you stop a bad change shipping?** Compatibility checks on the merge request and a mandatory approval workflow before the change set is applied.
- **What happens if the registry is down?** † The schemas are already registered at build time, so running services aren't affected; only new builds wait.
- **Why study Confluent Schema Registry?** To reuse a proven model and avoid known mistakes, before choosing what to build ourselves.
- **How do you handle sensitive data?** Masking and transformation rules apply before data goes to external parties.

## Before using in an interview

- Fill in the Result with real numbers.
- Keep or drop each **†** detail depending on what happened.
- Check the event-volume figures (billions per day, about one million at a time).

## Sources

- User's own account of the Schemate design and decisions (this conversation).
