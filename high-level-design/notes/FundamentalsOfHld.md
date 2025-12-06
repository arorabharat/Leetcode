1. Write the business requirement or functional requirement ?
2. Write non-functional requirement
3. Write some sample customer journey to understand the process.
4. Understand number fo users and latency requirement
5. Understand what is out of scope ?
6. Try to do some dry run to ensure the correct data flow.
7. 

Here is a **clean, structured template** in the **exact style and progression** you want.
Each section is written as a **form page**, with **questions, descriptions, and example values**, so it becomes a reusable *requirements-capture engine* for HLD/LLD.

This is intentionally designed as a **5-page foundation form** for architecture discovery.

---

# ✅ **HLD Requirements Discovery Form (5-Page Structured Template)**

*(Based on the structure you requested)*

---

# **📄 Page 1 — Understand Request Pattern**

### **1. What types of requests does your system receive?**

*Checkboxes*
*Example: Read requests, Write requests, Search queries, Upload requests, Event triggers.*

### **2. Describe how frequently each request type happens.**

*Short answer*
*Example: “Search = 50% of traffic, Writes = 10%, Reads = 40%.”*

### **3. What is your expected Requests Per Second (RPS) / Transactions Per Second (TPS)?**

*Short answer*
*Example: “Peak = 3000 RPS, Average = 400 RPS.”*

### **4. Does traffic follow a predictable pattern?**

*Multiple choice*

* Steady throughout the day
* Morning/evening spikes
* Highly bursty
* Seasonal spikes (sales, events)

*Example: “Bursty, mostly during 7–10 PM.”*

### **5. What is the peak traffic multiplier compared to normal?**

*Short answer*
*Example: “Peak = 5× normal load.”*

### **6. What is the read/write ratio?**

*Multiple choice*

* Mostly reads (80–99%)
* Balanced (50/50)
* Mostly writes
* Write-heavy (e.g., logging)

---

# **📄 Page 2 — Understand the Critical Path**

### **7. Describe the core user journey that must be fast and reliable.**

*Paragraph*
*Example: “User adds item → Checkout → Payment → Confirmation.”*

### **8. Identify the critical system operations involved.**

*Checkboxes*

* Authentication
* Database writes
* File upload
* Payment call
* Feed generation
* Cache lookup
* Search index update

*Example: “Authentication + DB Write + External Payment API.”*

### **9. What steps cannot fail without breaking user experience?**

*Paragraph*
*Example: “Payment confirmation must succeed or rollback.”*

### **10. What steps can tolerate minor delays or retries?**

*Paragraph*
*Example: “Email notifications can retry; analytics can lag.”*

### **11. What components participate in your critical path?**

*Paragraph*
*Example: “API Gateway → Auth Service → Cart Service → Payment Service → Database.”*

### **12. What is the acceptable end-to-end latency for the critical path?**

*Short answer*
*Example: “Under 200ms for search; under 1s for checkout.”*

---

# **📄 Page 3 — Understand Latency & Daily Active Usage**

### **13. Expected Daily Active Users (DAU)**

*Short answer*
*Example: “DAU ≈ 1 million.”*

### **14. Expected Monthly Active Users (MAU)**

*Short answer*
*Example: “MAU ≈ 10 million.”*

### **15. Required latency for key operations**

*Short answers with hints*

* Read latency target (example: “< 50ms”)
* Write latency target (example: “< 100ms”)
* Search latency (example: “< 200ms”)
* Upload latency (example: “< 1s”)

### **16. Do different user journeys have different latency requirements?**

*Multiple choice*

* Yes
* No
* Not sure

*Example: “Search must be fast (<150 ms), feed can be slower (~300 ms).”*

### **17. Max acceptable P99 latency**

*Short answer*
*Example: “P99 should be under 800ms.”*

### **18. Max acceptable API timeout**

*Short answer*
*Example: “Client timeout = 3 seconds.”*

---

# **📄 Page 4 — Understand Real-Time vs. Batch Processing**

### **19. Which processes must run in real time?**

*Checkboxes*

* Auth / Login
* Payments
* Search results
* Feed generation
* Recommendation updates
* Notifications
* Analytics updates
* Fraud detection
* None (pure batch system)

*Example: “Payments + Search must be real-time.”*

### **20. Which operations can run in batches?**

*Checkboxes*

* Analytics aggregation
* Email digests
* Data cleanup
* Image/video processing
* ETL pipeline
* ML model training

*Example: “Daily revenue reports run at midnight.”*

### **21. Batch frequency (if any)**

*Multiple choice*

* Every minute
* Every 5 mins
* Hourly
* Daily
* Weekly
* Custom schedule

### **22. Does batching affect correctness or user experience?**

*Paragraph*
*Example: “Analytics can lag by 1 hour; user-facing data cannot lag.”*

### **23. Data freshness requirement**

*Short answer*
*Example: “User feed should be fresh within 2–3 seconds.”*

---

# **📄 Page 5 — Can Some Processes Be Asynchronous?**

### **24. Which tasks can be moved off the critical path?**

*Checkboxes*

* Email sending
* Report generation
* Cache warming
* Media transcoding
* Notification fan-out
* Search indexing
* Billing/reporting
* Webhook delivery
* Database cleanup
* ML inference
* None → fully synchronous system

### **25. What’s the acceptable processing delay for async tasks?**

*Short answer*
*Example: “Notifications should be under 5 seconds; indexing can be 10–15 seconds.”*

### **26. Will asynchronous processing require queuing?**

*Multiple choice*

* Yes, using message queues (SQS/Kafka/RabbitMQ)
* Yes, but only lightweight async (in-memory tasks)
* No, must be synchronous

### **27. Does async processing require guaranteed delivery?**

*Multiple choice*

* Yes → at-least-once
* Yes → exactly-once
* No → best-effort okay

### **28. Can the system retry failures automatically?**

*Multiple choice*

* Yes, with exponential backoff
* Yes, simple retries
* No retries allowed
* Depends on the task

### **29. What events should trigger async flows?**

*Paragraph*
*Example: “User signup triggers welcome email + analytics event.”*

### **30. How will async failures be monitored?**

*Paragraph*
*Example: “DLQ + alerts on retry failures.”*

---

# ⭐ Want the full 6-page version?

If you want, I can now expand this into a **massive 6-page master framework** covering:

* System boundaries
* API contracts
* Data modeling
* Storage selection
* Consistency design
* Scalability
* Availability
* Security
* Observability
* Tech stack

Just tell me:
**“Build the full 6-page HLD/LLD form”**

And I’ll turn this into the definitive architecture template.
