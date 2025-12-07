Sure — here’s the same framework, but stripped down into simple markdown without heavy formatting.

---

Start by anchoring the product to a category. The category gives you a plausible MAU range.

Some rough anchors:

* Global social network: 1–3B MAU
* Country-scale social app: 50–300M MAU
* Niche community app: 5–20M MAU
* Enterprise SaaS: 100k–5M MAU
* Internal company tool: 5k–200k MAU

Next, convert MAU to DAU using a typical DAU/MAU ratio. Apps tend to have characteristic engagement ratios:

* Messaging apps: 0.7–0.9
* General social networks: 0.5–0.7
* Content consumption apps: 0.4–0.6
* Utilities like banking or payments: 0.1–0.3
* E-commerce: 0.05–0.2

A simple mnemonic: C → R → P → S
(Category → Ratios → Peak → Split)

1. Category gives you MAU.
2. Ratio gives DAU.
3. Peak window converts DAU into TPS.
4. Split the traffic into reads vs writes.

Example: building an Instagram-lite.

* Category: global social app → assume 300M MAU.
* Ratio: 0.6 → DAU = 180M.
* Assume 40 user actions per day.
* Peak usage window = 2 hours.

Peak TPS = (180M × 40) / 7200 ≈ 1,000,000 requests/sec.

Split by 95% reads and 5% writes:

* Reads ≈ 950k/s
* Writes ≈ 50k/s

With caching absorbing 70–90% of reads, backend origin might only handle 100k–200k RPS.

This reasoning chain is easy to adjust during an interview and signals strong estimation skills.




### More grounded estimation

MAU assumption:  
Use something like 50M MAU instead of 300M.  
DAU/MAU ratio:  
Social photo apps often have 30–50% engagement, not 60–70%.  
Example: 0.4 → 20M DAU.  
Actions per user per day:  
Realistic range is 10–20 actions, not 40–50.  
Example: 15 actions/day.  
Daily traffic:  
20M × 15 = 300M requests/day.  
Peak-hour rule:  
Peak hour ≈ 10% of daily traffic.  
300M × 0.1 = 30M requests in peak hour.  
Peak RPS:  
30M / 3600 ≈ 8,300 RPS.  
This number fits normal large-scale web systems (e-commerce, social-lite apps, job portals, etc.).  