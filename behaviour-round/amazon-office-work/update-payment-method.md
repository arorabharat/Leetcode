These notes summarize the technical and project management challenges encountered during the **Prime India Standardization and Convergence** project.

---

## **Project Overview**
* **Goal:** Converge India Prime and Worldwide (WW) Prime subscription services to a standardized architecture.
* **Target Feature:** Implement "Update Payment Method" for customers facing renewal failures (e.g., due to insufficient funds).
* **Objective:** Allow customers to update their payment instrument and receive an instant charge to avoid subscription cancellation during the **6-day grace period**.

---

## **Technical Challenges & Constraints**
Implementation in India was complicated by local regulations and legacy architecture:

* **Regulatory Hurdles (RBI/India):**
    * **24-Hour Notification:** Regulations require a pre-debit notification 24 hours before a recurring charge.
    * **Mandate Setup:** An instant charge cannot occur on a new mandate without this 24-hour lead time.
* **Invoicing Logic:**
    * Once a retryable failure occurs, an invoice is already generated for the *old* payment instrument.
    * System architecture prevented charging a new payment method against an existing invoice.
* **Workflow Discrepancies:** * First-time charges and recurring charges in India use different workflows compared to WW marketplaces.
    * "Cancel and Re-subscribe" was not viable due to ownership conflicts between different backend services.

---

## **The Initial Solution (Technical Workaround)**
To launch the feature without a massive overhaul of the renewal workflow:
* **Event Suppression:** Collaborated with Principal Engineers to implement a configuration that suppressed the "Instant Charge" event sent to the ordering team.
* **Result:** Instead of an instant charge (which failed due to mandates), the system saved the new payment info and waited for the **next scheduled retry window**.
* **Outcome:** Successfully launched and functioned correctly for 18 months.

---

## **The Failure Point (18 Months Later)**
A production issue arose during a migration by the Subscription Team:
* **The Change:** The Subscription Team migrated to a system where, instead of deleting or updating configurations, they introduced a **"Deleted" flag**.
* **The Bug:** The India-specific logic checked if a configuration *existed* but did not check the status of this new "Deleted" flag.
* **Root Cause:** Lack of cross-team communication regarding the schema change/new flag.
* **Impact:** The system assumed no mandate existed, leading to widespread renewal failures and high operational costs to debug logs and identify the flag discrepancy.

---

## **Key Learnings & Future Strategy**
* **Prioritize Long-Term Solves:** Avoid "band-aid" configurations if a deeper architectural alignment is possible, even if it requires pushing back on business timelines.
* **Stakeholder Alignment:** Explicitly highlight and document technical risks to business partners, explaining the trade-offs of shortcut solutions.
* **Better Documentation:** Maintain a "Risk Registry" for specialized logic (like suppressed events) so that migrating teams are aware of edge cases.
* **Improved Communication:** Ensure tighter integration with platform teams (like the Subscription Team) to catch schema changes before they impact localized workflows.