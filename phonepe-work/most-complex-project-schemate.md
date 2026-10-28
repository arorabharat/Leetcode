# Most complex project: Schemate, schema and data governance platform (PhonePe)

## Question

What is the most complex project you have worked on?

## How to read this note

- Unmarked details come from your own description of Schemate.
- **[C]** marks a concept taken from Confluent Schema Registry's documentation (docs.confluent.io), used as an industry reference for how such a platform works.
- **†** marks a plausible design detail I added to make the story concrete. These are not from your account. Keep a † detail only if it is true for you, or drop it. An interviewer will probe the technical details, so only claim what you can defend.
- Your account gave no results. Fill in the Result placeholders with real numbers.

---

## Answer (about 4 minutes spoken)

### Context: why it is complex

"The most complex project I have worked on is Schemate, a schema and data-governance platform I am building at PhonePe in the observability pod.

PhonePe pushes billions of events a day, with around a million being generated at any moment, and hundreds of downstream services depend on them. For a payments app, availability standards are strict, so one incorrect event can start failing consumers downstream. The platform's goal is that every event published is validated against a schema, that producers and consumers can evolve their schemas independently, and that teams can discover the right event for a use case. It also had to be self-service, so our infra team wouldn't become the bottleneck.

It was complex for four reasons:

1. **Scale and criticality.** It sits next to a payments platform, so the platform must not become a new point of failure.
2. **Hundreds of existing services.** Industry practice is schema-first: you write schema files and generate code models from them. Our services already had their code, and asking hundreds of teams to rewrite schemas or move toolchains was not realistic.
3. **Correctness over time.** Schemas keep changing, and every change must keep existing producers and consumers working.
4. **More than schemas.** We also needed data rules, such as masking critical fields before sending events to external parties and validating marketing widget data."

### Learning from the industry before designing

"I started by studying Confluent Schema Registry, the standard in the Kafka ecosystem. I spent about a month on it. These were the ideas I took from it:

- **Subjects and versions [C].** A subject is the scope in which a schema evolves. It holds an ordered list of versions, and each subject has its own compatibility setting.
- **Compatibility modes [C].** Backward means consumers on the new schema can read old data. Forward means consumers on the old schema can read new data. Full means both. Transitive variants check against all earlier versions, not only the latest. Confluent's default is backward.
- **Globally unique schema IDs [C].** Producers embed the schema ID in each message, and consumers use it to fetch the schema. Identical definitions share an ID.
- **A registry outside the data path [C].** The registry sits next to the message broker, not in front of it, and clients cache schemas.

I deliberately did not copy the whole model. Confluent's serializers fetch and validate at runtime, and that was the decision I had to get right for us."

### The key decision: where to validate

"Getting a schema from existing code, with minimal effort from teams, forced a real decision.

| | A. Runtime capture | B. Build-time capture |
|---|---|---|
| Idea | Infer the schema when an event is published, cache it, and validate every event centrally | A build plugin reads annotated classes, registers the schema, and checks compatibility before deployment |
| Client effort | Almost none | One annotation per class |
| Risk | We become a central bottleneck for all events, and runtime failures are hard to mitigate | A bad schema fails the build, so it never reaches production |
| Load | Central validation must scale for peak traffic | Each team spends its own build resources |
| Blast radius | A registry problem can affect every service | A team's mistake affects only that team |

Leadership leaned towards runtime capture because it needed zero client effort. I argued against it, because on a payments platform I did not want any system I own in the path of every event. I proposed shift-left: producers own their schemas, and any incompatible change fails fast, at the earliest moment, which is the build."

### Architecture

"Schemate has four parts:

1. **Annotation and Maven plugin.** Teams annotate their event classes. At build time the plugin scans the classes, generates the schemas, and prepares them for registration. No hand-written schema files and no migration off their existing code.
2. **Schema registry.** It stores schemas by subject, versions each change, and applies a compatibility level chosen by the owner (backward, forward or full) **[C]**. † Schemas are identified by a globally unique ID that is embedded in published messages **[C]**, and clients cache them so the registry is not on the hot path.
3. **Governance workflow.** On every merge request, the plugin generates a change set and checks it against the existing schemas. The change then goes through an approval workflow, where the owners of the affected schemas review and approve it. Once approved, the change set is applied atomically, and only then can the merge request merge. When the service deploys, its schemas are activated.
4. **Data rules and discovery.** A shared rules service for masking and transformation before events go to external parties, validation for marketing and recommendation widget data, and a catalog so teams can find the right event for a use case."

### Hard problems and how I handled them

- **Backward compatibility across hundreds of consumers.** A new schema can break a consumer you don't know about. The compatibility check against existing versions, plus owner approval, catches that before merge. † For events consumed by many teams, I'd use a transitive check so a change is validated against all earlier versions, not just the last **[C]**.
- **Atomicity of a change.** A merge must not leave the registry half updated. Applying the change set atomically only after approval keeps the registry and the code in step.
- **Failure isolation.** Because schemas are registered at build time, a registry problem blocks only new builds. † Running services keep working because they have the schemas already.
- **Zero-effort onboarding.** One annotation was the whole integration cost, and no existing service had to adopt a new modelling toolchain.
- **Sensitive data.** Masking and transformation rules are applied before data leaves for an external party.

### Convincing leadership

"I made the trade-off concrete rather than arguing in principle: risk (a central bottleneck on payment events), cost (central capacity for peak traffic), blast radius, and time to feedback. † I also offered a small prototype on one real service so the annotation effort was something leadership could see rather than imagine."

### Result

"[Fill in: number of services onboarded, number of incompatible changes blocked before production, change in schema-related incidents, time saved for teams.] The platform gave us governed, compatible schemas without rewriting hundreds of services, kept the platform off the event hot path, and gave teams ownership of their changes without waiting on us."

### One-line summary if interrupted

"I'm building a schema and data-governance platform for PhonePe's event ecosystem: a build-time Maven plugin captures schemas from existing code, a registry enforces compatibility, and an approval workflow gates every change, all designed so the platform is never in the hot path of payment events."

---

## Deep-dive answers to expect

- **Why not Confluent Schema Registry directly?** † Our services already had code, not schema files, and we needed governance, approvals, data masking and discovery, not only storage. I used Confluent's model as a reference rather than adopting it wholesale.
- **What are backward, forward and full compatibility?** Backward: consumers on the new schema can read old data. Forward: consumers on the old schema can read new data. Full: both **[C]**. The owner picks the level per subject.
- **Why compatibility checks at build time and not runtime?** Build time is the earliest point where a team can learn that a change breaks a consumer, and failures stay inside the team that made the change.
- **What if the registry is down?** † Only new builds and approvals are affected, because running services already have their schemas.
- **How do you handle a breaking change that is truly necessary?** † Register a new version of the event, run both in parallel, and migrate consumers before removing the old one.
- **How does a consumer find an event?** Through the catalog, searching by use case.
- **How do you protect sensitive data?** Masking and transformation rules are applied before events go to external parties.

## Before using in an interview

- Replace every placeholder in Result with real numbers.
- Check each † detail and remove anything you didn't actually build or decide.
- Check the event-volume figures (billions per day, around a million at a time).
- Be ready to explain the Confluent concepts in your own words; interviewers often ask about them.

## Sources

- Your description of Schemate (this conversation).
- Confluent documentation: Schema Registry concepts, https://docs.confluent.io/platform/current/schema-registry/fundamentals/index.html
- Confluent documentation: Schema evolution and compatibility, https://docs.confluent.io/platform/current/schema-registry/fundamentals/schema-evolution.html
