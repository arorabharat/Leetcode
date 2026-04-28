***

# Behavioral Interview Example: Influencing a Team to Change Their Roadmap

**Interview Question:** *"Give an example of a time you had to influence another team to adopt your solution or change their roadmap to unblock your project."*

### **Situation (S)**
I was leading a project to launch new Prime subscription and payment features in additional geographic locations. However, we faced a major operational bottleneck: our team was constantly bogged down by manual investigations from Product Managers regarding drops in business metrics (like payment success rates or upsell clicks).

The root cause was a severe lack of real-time technical metrics. The Tier-1 service that rendered our features operated on the high-traffic Amazon homepage, and the team owning that service had explicitly refused to emit technical metrics for our use cases to save on massive infrastructure costs. Consequently, we were relying on delayed weekly business reports, making proactive incident response impossible.

### **Task (T)**
I realized that launching my project in new locations without technical observability would just replicate this massive operational burden. I decided that before proceeding, I needed to influence the Tier-1 service team to prioritize our observability needs and change their upcoming roadmap to emit our metrics, despite their strict infrastructure cost constraints.

### **Action (A)**
*   **Data-Backed Narrative Building:** I knew that simply requesting a roadmap change wouldn't work. I partnered with my PM and gathered data across past on-call tickets to quantify the business impact. I mapped out the sheer volume of engineering hours (3 to 4 days per query) wasted manually digging through logs due to delayed incident detection.
*   **Designing a Compromise Solution:** To address their primary concern (infrastructure cost at high TPS), I audited our requirements and pared them down. I proposed a highly restricted but critical set of 15 to 20 technical metrics that would resolve 90% of our observability blind spots without spiking their infrastructure bills.
*   **Cross-Team Negotiation:** Armed with this data, I approached the manager of the Tier-1 service. I presented the disparity between the massive operational drain on our team versus the relatively minimal infrastructure cost of my curated metrics list. After multiple follow-up discussions and aligning on the long-term risk to Prime metrics, I successfully convinced them to prioritize this integration in their immediate sprint roadmap.
*   **End-to-End Ownership:** Once they enabled the metrics, I immediately built a comprehensive dashboard with real-time anomaly alarms to ensure the new data was utilized effectively.

### **Result (R)**
*   **Roadmap Alignment:** The Tier-1 team adopted the proposed compromise and altered their sprint to emit the metrics without breaking their budget.
*   **Drastic Efficiency Gains:** The time required to investigate an issue plummeted from **5–10 days to just 5–10 minutes**.
*   **Self-Service Enablement:** The real-time dashboard allowed PMs to answer their own questions without engineering support, completely unblocking my team to launch the new locations with robust, day-one observability.

***

### 💡 **Tips for delivering this in an interview:**
*   **Emphasize the friction:** Highlight that the Tier-1 team had a very valid reason (high infrastructure costs on Amazon's homepage) to say "no." This makes your negotiation skills shine.
*   **Highlight the compromise:** Showing that you didn't just demand a change, but actively curated a list of 15-20 metrics to respect their budget constraints, demonstrates high emotional intelligence and strong architectural pragmatism.