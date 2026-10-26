## Never Prime race condition

A while back, when I was owning Prime payments for India, we noticed a weird issue: customers who were trying to buy Prime for the very first time were failing on their first transaction.

It wasn’t completely blocking them—if they hit retry, the payment would go through. But for me, that was just unacceptable. A customer’s first-ever experience with Prime shouldn't be a broken payment screen.

So, I started digging through the customer journey and checking the logs across our services. What I found was a classic timing race condition.

To keep checkout fast, we were generating the Subscription ID asynchronously in the background instead of blocking the user redirect. This worked totally fine for credit or debit cards because the user had to wait for an SMS OTP, type it in, and submit it. That gave our backend plenty of time—usually 15 to 30 seconds—to create the subscription entity.

With UPI, though, it was completely different. Customers were just jumping into their UPI app, entering their mPIN in a couple of seconds, and coming right back. Because people were so fast, their payment callback was reaching our systems before the backend had even finished creating the Subscription ID. So for cards, failure was almost zero. But for UPI, it was failing around 30% to 40% of the time on that first attempt.

I needed to fix this right away, but rebuilding the whole cross-service flow would take weeks. So I broke the solution into two parts: an immediate mitigation, and a proper long-term architectural fix.

For the quick mitigation, I added a small delay stage specifically for new Prime customers paying via UPI—leaving card users and existing customers completely untouched. I checked the telemetry for our subscription creation service and saw its p95 latency was roughly 3 seconds. I experimented with different delays from 1 to 5 seconds, and 3 seconds hit the sweet spot: about 95% of those first-time UPI transactions started passing cleanly. That immediately solved the drop-offs for that 30% to 40% chunk of users.

But obviously, adding a 3-second artificial pause wasn't something I wanted to keep forever.

For the long-term fix, I proposed a pre-reservation pattern. The idea was simple: instead of waiting for the user to hit "Pay" to start creating the subscription, we triggered a lightweight "reserve request" the moment a high-intent user entered the Prime checkout page. By the time they selected UPI and clicked pay, the Subscription ID was already pre-generated and waiting. We just had to claim it.

Claiming an existing reserved ID took only about 200 to 500 milliseconds, compared to the 3 seconds it used to take to create one from scratch.

Once we rolled that out, we got rid of the 3-second delay entirely, kept the checkout snappy, and permanently wiped out that 30% to 40% failure spike for first-time UPI subscribers.