We’ve talked about systemic operational fixes and guiding other engineers. Now I want to look at how you navigate high-stakes ambiguity and technical trade-offs yourself.   Can you tell me about a time when you were leading a project with vague requirements, conflicting technical directions, or high unknown risks?   How did you evaluate the risks, what intelligent technical trade-off or pivot did you make to keep the project moving, and how did you convince stakeholders or leadership that your path was the right one?

---

While I was working on Prime at Amazon, product leadership gave me a very open-ended requirement: our overall payment success rate was hovering around 50% to 60%, and they wanted me to increase it to 75%.

The mandate was completely ambiguous. There was no single major bug or architectural flaw causing this. It was a mix of multiple smaller edge cases across different services, and some were simply customers intentionally dropping off.

I realized that looking at a single global number wouldn’t help us fix anything. If we didn't know where the failures were actually coming from, we couldn't take targeted action.

So, I tackled this in three steps:

First, I saw that our telemetry was missing critical dimensions. I spent about a week adding metrics at various cardinalities across our services and downstream dependencies. I built a detailed dashboard to split transactions by payment method, subscription plan, and customer entry points.

Once the data came in, a major gap became obvious: the global number was getting skewed. In certain promotional places on the Amazon app, customers had zero intent to buy—they just clicked on a banner, landed on the page, and closed it. I sat down with the product team and explained that these intentional drop-offs shouldn't be counted as payment failures. We separated customer drop-offs from actual technical payment failures so we had an accurate baseline to work with.

Second, looking at the real technical failures, I identified the top issues. The biggest one was with our asynchronous background workflows.

The original design allowed only one workflow instance to remain open per customer at a time. The issue was that the unique ID associated with the workflow was just using the Subscription ID—it was not including the Contract ID. Because of that, the workflow had no context about which specific billing contract it was opened for. If a workflow failed or got stuck on an older contract, it stayed open and blocked all future cycles for that subscription.

This was causing two big issues:

1. It stopped mandatory pre-charge notifications from going out, and without sending those notifications, we legally couldn't trigger the payment charge.
2. It blocked cache refreshes, which meant customers were seeing stale plan data or wrong pricing, causing signups to fail.

This stuck workflow issue alone was responsible for roughly 10% of our failures.

To fix this, I changed the design so that each workflow opened using a compound key of the Subscription ID and the Contract ID. That way, any failure or stuck state on a previous contract would never impact the next billing cycle. On top of that, I added alarms directly on the step workflow stages so that if any step stalled, on-call engineers were alerted immediately to unblock it and keep cache refreshes healthy.

Finally, I picked the top five technical contributors and resolved them one by one. Because we had the new dashboard in place, product and engineering could clearly see the incremental increase in the success rate after every deployment.

By breaking down an unclear target with proper data and fixing the contract-level workflow isolation, we successfully pushed our technical payment success rate past the 75% goal.