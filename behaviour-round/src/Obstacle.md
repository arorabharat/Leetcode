 > Give an example of a situation where you had to overcome a significant setback or obstacle to achieve a goal

There was a moment during the launch of our “payment instrument update” feature that really tested my ability to work through a setback. The feature allowed customers to retry payments after a soft decline. India has RBI-specific rules that require a 24-hour notification window before retrying a recurring charge, so I adjusted our workflow to avoid retrying immediately when a customer updated their payment method.

The launch went smoothly at first, but a month later we started seeing an odd pattern: 5–10% of transactions were failing. Not all, just a stubborn minority. The API logs insisted that the payment preference was set correctly, but downstream systems behaved as if no preference existed. I spent several days tracing the workflow end-to-end, instrumenting logs, and replaying failed transactions, but nothing in our code path explained the inconsistency.

The real breakthrough came only after deeper back-and-forth with the Payment Preference team. Eventually we uncovered the actual root cause: they had introduced a new internal flag that marked a payment preference as “deleted.” The tricky part was that the API still returned a seemingly valid response even when this internal “dirty bit” was set. Since my workflow wasn’t checking that flag, we were accepting a preference payload that looked correct but wasn’t usable during transaction processing.

Once we understood the hidden failure mode, I updated our logic to read and validate that flag. After deploying the fix, the failure rate dropped back to zero and we were able to safely dial up the feature again.

The experience reminded me that obstacles in distributed systems are rarely loud — they hide in mismatched assumptions between teams and services. That project strengthened my debugging approach and my patience for deep cross-team investigation, both of which now serve me well whenever I’m working on payments logic or compliance-driven workflows.
