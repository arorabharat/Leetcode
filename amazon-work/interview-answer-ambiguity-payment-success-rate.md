# Interview answer 2: leading through ambiguity (payment success rate)

## Question

We've talked about systemic operational fixes and guiding other engineers. Now I want to look at how you navigate high-stakes ambiguity and technical trade-offs yourself.

Can you tell me about a time when you were leading a project with vague requirements, conflicting technical directions, or high unknown risks? How did you evaluate the risks, what intelligent technical trade-off or pivot did you make to keep the project moving, and how did you convince stakeholders or leadership that your path was the right one?

## Project used

Prime payment success-rate improvement. Source: your own account in `voice-notes/33-pulling-up-payment-sucess-rate.md`. This is a second story for the same question; the first is [the renewal payment-method update](interview-answer-ambiguity-and-tradeoffs.md).

## How to read this note

Details marked **†** are illustrative additions, not from your voice note. Keep them only if true for you. Unmarked details come from your account.

## Answer (about 3 to 4 minutes spoken)

### Situation

"On Prime, product leadership gave me a very open-ended goal: our overall payment success rate was around 50 to 60 percent, and they wanted 75 percent. There was no single bug or design flaw to point at. The failures were a mix of small edge cases across several services, plus customers who simply chose to drop off. The requirement was vague, the causes were unknown, and the risk was that I'd spend weeks on fixes that barely moved the number."

### Task

"I owned finding out where the failures actually came from and getting us to the target. My first decision was not to start fixing anything: a single global number can't tell you where to act."

### Action 1: make the problem measurable

"Our telemetry was missing key dimensions. I spent about a week adding metrics at different levels of detail across our services and their downstream dependencies. I built a dashboard that split transactions by payment method, subscription plan and customer entry point.

That showed something the global number hid. On some promotional placements in the Amazon app, customers had no intent to buy: they tapped a banner, landed on the page and left. Those drop-offs were counted as payment failures and dragging the number down.

I took this to the product team and made the case that intentional drop-offs shouldn't count as payment failures. We separated customer drop-offs from real technical failures, which gave us an honest baseline. That was the trade-off I pushed for: a less flattering but accurate metric, so we'd aim at the right problem."

### Action 2: find the biggest technical cause

"With real failures isolated, I ranked the causes. The biggest was in our asynchronous background workflows.

The design allowed only one open workflow per customer at a time, and its unique ID was just the subscription ID, without the billing contract ID. So a workflow had no idea which billing contract it belonged to. If one failed or got stuck on an older contract, it stayed open and blocked every later billing cycle for that subscription.

That caused two problems:
1. It stopped the mandatory pre-charge notifications from going out. Without them we legally couldn't trigger the charge.
2. It blocked cache refreshes, so customers saw stale plan data or wrong pricing, and signups failed.

This one issue accounted for roughly 10 percent of our failures."

### Action 3: the fix and how I kept it safe

"I changed the design so each workflow is opened with a compound key of subscription ID and contract ID. A failure on an old contract can no longer affect the next billing cycle. I also added alarms on the individual workflow steps, so on-call is alerted straight away if a step stalls.

† For safety, I enforced uniqueness on the compound key at the data layer so two workflows can't run for the same contract cycle, and I ran a one-time clean-up to close workflows that were already stuck so existing customers' next cycles started cleanly."

### Action 4: bring everyone along

"I didn't try to fix everything at once. I picked the top five technical contributors and fixed them one at a time. Because the new dashboard showed the success rate after each release, product and engineering could see the improvement step by step. That kept stakeholders confident that the path was working, and it turned an open-ended goal into five concrete, trackable changes."

### Result

"We pushed the technical payment success rate past the 75 percent goal. Beyond the number, the system was easier to run: the team had a dashboard that separated customer intent from technical failure, and alarms on workflow steps so a stuck workflow gets caught immediately."

### One-line summary if interrupted

"Leadership gave me a vague 'raise payment success from about 55 to 75 percent', so I first made the problem measurable, separated customers who never meant to buy from real failures, then fixed the biggest cause, a workflow keyed only by subscription instead of subscription plus contract, and showed progress after each fix until we passed 75 percent."

## Likely follow-up questions

- **Why start with metrics instead of fixes?** A single number couldn't show where to act, and part of it wasn't even a failure.
- **How did you convince product to change the metric?** I showed data from the new dashboard that some entry points had no purchase intent, so counting them hid the real problem.
- **What happened to workflows already stuck?** † A one-time clean-up closed them so the next billing cycle started under the new key.
- **Could the new key cause duplicates or race conditions?** † I enforced uniqueness on the compound key at the data layer so two workflows can't run for the same contract cycle.
- **What did you not fix?** Customer drop-offs. They aren't technical failures, so we excluded them from the metric rather than "fixing" them.
- **What would you do differently?** Add the finer-grained metrics from the start so the first diagnosis doesn't take a week.

## Before using in an interview

- Confirm the 50 to 60 percent baseline, the 75 percent target, the roughly 10 percent share for stuck workflows, and the "top five" count; all come from your voice note.
- Keep or drop every **†** detail depending on what you actually did.
- Say "workflow" and "billing contract" rather than internal service names.
- Do not use the "9.2 / 10" rating in the voice note as evidence; it was written for a different company's round.

## Sources

- `amazon-work/voice-notes/33-pulling-up-payment-sucess-rate.md`
