# RBI card-on-file tokenization for Prime


Back in 2022, when the Reserve Bank of India mandated Card-on-File Tokenization (CoFT), merchants were legally required to capture explicit customer consent before storing card details for subsequent transactions. This put our recurring Prime subscription charges directly at risk, and as the payments owner for Prime, I had to urgently design and lead a compliance rescue plan.

Because we were racing against a hard regulatory deadline, the pressure was intense. I moved quickly to put together the design, got alignment from our senior engineers, and jumped straight into implementation to hit our target launch date.

However, during QA testing, we hit a critical issue: the card consent that customers were explicitly giving on the checkout UI was intermittently missing in our Prime backend services.

I spent a couple of days digging into distributed logs and tracing the transaction path across downstream services. What I discovered was an architectural assumption that proved to be wrong: the Purchase Document service we were relying on was not an immediate source of truth. The downstream call to persist the customer consent was intentionally designed as asynchronous. Because of that async boundary, there was an eventual consistency lag—the checkout completed before the consent record landed, leaving the UI and backend states out of sync for a chunk of requests.

I set up a review with a Principal Engineer to understand why it was built that way. He explained the broader design philosophy: if that consent storage call had been synchronous, any blip or latency in that single service would have taken down the entire Amazon retail checkout flow. From a platform availability perspective, making it asynchronous was completely the right call to protect the core checkout blast radius.

The realization hit me: in my rush to deliver fast, I had verified the API interface on paper, but I hadn't deeply validated the underlying consistency guarantees and timing assumptions across service boundaries.

I took immediate ownership of the problem and resolved it in two steps:

1. **Transparent Impact Communication:** I sat down with our Product Manager and laid out the data plainly. I explained that while the front-end transaction wouldn't fail for the customer, 20% to 30% of new tokenized cards would end up in an inconsistent state, which would subsequently cause up to 15% of our future recurring billing cycles to fail.
2. **Targeted Dynamic Flow Fix:** For recurring e-mandates, valid consent wasn't optional—it was legally mandatory. I spent an additional day modifying our downstream integration to dynamically set a dedicated e-mandate synchronization flag. This allowed the payment orchestrator to handle the e-mandate consent path with explicit verification before finalizing the subscription setup, isolating recurring cards from the standard retail async delay.

The biggest takeaway for me from that project was that under intense timeline pressure, speed can easily blind you to cross-service consistency contracts. Ever since that incident, whenever I design integrations with distributed downstream services, I don't just rely on API contract schemas—I explicitly map out the synchronization model, race conditions, and eventual consistency boundaries during the initial RFC phase before writing any code.

## Sources

- `resume-v1/src/experience.tex`
- `behaviour-round/card-on-file-tokenisation.md`
- `behaviour-round/card-on-file-tokenisation-HLD-style.md`
- `behaviour-round/amazon-office-work/intro.md` (role context mentions tokenized Prime purchases and RBI/NPCI compliance)
- `behaviour-round/non-tech-skills/Summary.md` (story-bank references tokenization; no additional implementation details)
