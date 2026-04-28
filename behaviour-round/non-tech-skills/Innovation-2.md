Great — for behavioral interviews (especially Amazon-style, given the Prime context), I'll structure this using STAR with a clear focus on **your individual contribution** and tie it to relevant Leadership Principles. Here's a polished, detailed version you can practice delivering.

---

## Innovation Story: Reducing On-Call Resolution Time by 80%+

### Situation (~30 seconds)

"On my team, we own Prime — a core business service that orchestrates requests across a large number of downstream services. Because of this orchestration role, we received a high volume of on-call tickets covering issues like failing requests, missing refunds, and benefits not being minted for customers.

The challenge was that diagnosing any single ticket required pulling context from multiple downstream services. There was no single pane of glass — engineers had to manually open 6 or 7 different internal tools, run queries, and stitch the data together. On average, just *gathering* the information for a ticket took 15 to 20 minutes, and end-to-end resolution was close to an hour. Across a rotation, this was consuming a significant chunk of our team's engineering bandwidth and pulling people away from feature work."

### Task (~20 seconds)

"I wanted to find a way to dramatically reduce this on-call overhead — but I also recognized that building a proper aggregation service or internal tool from scratch would require significant engineering investment, cross-team approvals, and infra work that wasn't going to be prioritized in the near term. So my task was to find a high-leverage, low-cost way to solve this for the team quickly, while keeping the door open for a more durable solution later."

### Action — Phase 1: The Tampermonkey Script (~60 seconds)

"I had recently come across Tampermonkey, a browser extension that lets you inject custom JavaScript into existing web pages to modify their UI and behavior. I saw an opportunity.

I started by reverse-engineering the network calls — I opened the dev tools on each of the internal tools we used and identified the underlying JSON APIs that were powering the UI. Once I understood the data contracts, I picked the most-used internal page as my 'host' UI and wrote a Tampermonkey script that did cross-origin calls to the other downstream services, aggregated all the relevant context, and rendered it directly inline on that page.

The result was that an engineer on-call could open one tool, and instead of navigating across 6 or 7 services, they'd see all the context they needed right there. I rolled it out to my team, iterated based on their feedback, and the script reduced data-gathering time from 15–20 minutes down to under a minute. That script is actually still running today, and it's been adopted broadly — engineers across adjacent teams are still using it for their own workflows."

### Action — Phase 2: Scaling It With AI (~60 seconds)

"That solved the immediate problem, but over the last year, with the advancements in AI tooling internally at Amazon, I saw a chance to take this much further. The Tampermonkey script still required a human to open the page, read the data, and reason about next steps. I wanted to eliminate that step entirely.

So I proactively reached out to other teams who were tackling similar on-call burdens to understand their approaches. Through those conversations, I learned that some teams had started building AI agents that integrated with our internal ticketing system — specifically, there's a ticket-agent framework that can post comments directly on tickets.

I took those learnings and designed an architecture for our use case: an AI-driven on-call assistant that would automatically pull data from all the relevant downstreams when a ticket came in, run it through the SOPs we'd defined for common ticket categories, and post a summary plus suggested next steps directly as a comment on the ticket. For tickets that didn't match an existing SOP, it would classify them as 'unstructured' and flag them so we could create new SOPs over time — meaning the system would get smarter as we used it.

I then partnered with two other SDEs to drive the implementation. I was responsible for the architecture and for unblocking the team, and I made sure we stayed on schedule."

### Result (~30 seconds)

"The impact was significant. Average on-call ticket resolution time dropped from roughly one hour to 5–10 minutes — close to an 85% reduction. Just as importantly, the system isn't just data aggregation; it actively recommends next steps based on our SOPs, which means even less experienced on-call engineers can resolve tickets confidently. And because of the unstructured-ticket classification, we now have a feedback loop for continuously improving coverage.

Beyond the metrics, what I'm most proud of is the trajectory: I started with a scrappy browser script to unblock my team immediately, and then evolved that into a durable, AI-powered solution by collaborating across teams. The original Tampermonkey script bought us roughly two years of relief at near-zero cost, and the AI agent is now the long-term answer."

---

## Tips for Delivering This

**Leadership Principles this maps to** (good to mentally tag for Amazon interviews):
- **Invent and Simplify** — the Tampermonkey hack is a textbook example
- **Bias for Action** — you didn't wait for a sanctioned project; you shipped a scrappy solution
- **Are Right, A Lot** — recognized building from scratch wasn't the right ROI
- **Learn and Be Curious** — picked up Tampermonkey, then later AI agent patterns
- **Earn Trust / Deliver Results** — script still in use, AI system delivered measurable wins

**Things to be ready to answer follow-ups on:**
- *"What were the risks of the Tampermonkey approach?"* — security review, fragility if downstream APIs changed, no formal ownership
- *"How did you handle pushback or skepticism?"* — especially when proposing the AI architecture
- *"What did you specifically own vs. what did the other two SDEs own?"* — interviewers will probe individual contribution
- *"How did you measure the time savings?"* — be ready with how you tracked the 1hr → 5-10min number
- *"What would you do differently?"* — have one honest reflection ready

**Delivery tips:**
- Slow down on the numbers (15-20 min, 1 hour → 5-10 min, 85% reduction) — let them land
- Use "I" for your individual work, "we" only when genuinely collaborative
- The Phase 1 → Phase 2 arc is your strongest narrative beat — it shows you don't just ship and forget; you keep raising the bar

Want me to also prep a **shorter 90-second version** for when interviewers ask you to be brief, or **practice questions** with model answers for likely follow-ups?