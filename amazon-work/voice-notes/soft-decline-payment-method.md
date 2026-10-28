I led the India Prime feature that allowed customers to recover from a failed renewal by adding a new payment method.

To give you some context, whenever a Prime renewal failed for a retriable reason, such as insufficient funds, the digital ordering service kept the renewal order open for a six-day grace period and retried the charge on a fixed schedule using the existing payment method. The problem was that customers were losing Prime during this window even when they were willing to pay using a different payment method.

The challenge was that I couldn't simply close the existing order, because closing that workflow would cancel the order. So I evaluated two possible approaches.

The first option was to cancel the existing order, create a new one, and charge the customer immediately. However, doing this correctly required an atomic operation across our signup and renewal services. That would have needed months of re-architecture, which was already planned for the future, so I didn't think it made sense to take on that effort just for this feature.

The second option was to use the existing payment-method swap mechanism. This was already used by our worldwide marketplaces. The payment method would be swapped on the existing open order, and the swap service would notify the digital ordering service to charge the new method immediately.

However, that approach had a problem in India. For recurring payments, we were required to generate an invoice and provide a 24-hour notice period before charging the customer. So, triggering an immediate charge on the new payment method would violate that requirement.

I owned the design and delivery of this feature, so I presented both options to product and leadership, along with their trade-offs. We could either invest months in the atomic solution or make a smaller change to the existing swap mechanism, suppress the immediate charge, and let the scheduled retries handle the payment.

I recommended the second option because it would deliver the customer benefit much sooner without blocking the planned re-architecture. However, I also called out a significant risk. If we triggered a retry too early, it could cancel the existing open order, even though the original payment might have succeeded on a later retry. In other words, changing the payment method could actually make things worse for a customer whose original payment was about to recover.

So, I designed the solution around controlling that risk.

First, I made sure that the immediate-charge notification was suppressed only for India Prime. This was important because the digital ordering service was a shared platform used by more than a hundred marketplaces and several other clients. I didn't want a change for one use case to affect the global ecosystem.

Second, I adjusted the scheduled retry window. If a retry was due within the next two hours, I pushed it out to ensure that the charge would happen only after the required notice period. The existing retry mechanism would then handle the payment instead of introducing a new charging flow.

Third, I added a hard cap of four to five retries to prevent the mechanism from being misused. And finally, I made sure the customer received immediate confirmation after adding the new payment method, so they knew that we would retry the payment in the background.

I also spent time validating the impact of the change. I audited the services consuming the notification to understand whether suppressing it could break any other workflows, and I kept the change strictly scoped to India Prime.

For stakeholder alignment, I explained the regulatory requirement to the worldwide digital ordering service team and why the existing immediate-charge behavior wouldn't work for India. Once they understood the constraint, they agreed to the India Prime-specific change. I also walked the Principal Engineers through the design, the dependency audit, and the risks before proceeding. With product and leadership, I framed the decision around the trade-off between a months-long re-architecture and a narrower solution that could deliver the benefit immediately.

As a result, we launched the feature without waiting for the re-architecture, stayed within India's recurring-payment requirements, and enabled customers to recover failed renewals within the grace period.

The trade-off was that our solution continued to depend on the existing retry mechanism rather than making the entire operation atomic. I documented that dependency and kept the owning teams informed. The planned re-architecture could address that limitation later.

The biggest learning for me was that when you're modifying a shared platform, solving the immediate customer problem isn't enough. You also need to understand the existing state transitions, audit downstream dependencies, and deliberately limit the blast radius of your changes.