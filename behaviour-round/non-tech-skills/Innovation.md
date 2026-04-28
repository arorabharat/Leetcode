1. on-call AI assistant tool


Below is a structured, interview-ready **innovation story** using your content. It is tightened, outcome-focused, and highlights initiative, technical depth, and impact.

---

## **Innovation Story (STAR Format — ~2–3 minutes)**

### **Situation**

In my team, on-call was highly operationally heavy. A large portion of time—often **15–20 minutes per ticket**—was spent manually gathering data from multiple downstream services because our system acts as an orchestrator. There was **no single place** to view all the required debugging information.

### **Task**

I wanted to significantly **reduce the time and effort required for on-call debugging** without waiting for a large, resource-heavy platform investment.

### **Action (Phase 1: Lightweight Innovation)**

Instead of building a full system from scratch, I explored a lightweight approach using **Tampermonkey**.

* Reverse-engineered network/API calls made by different service UIs
* Aggregated responses from multiple downstream services
* Injected a unified view directly into an existing internal UI using a browser script

This allowed engineers to **see all relevant data in one place**, eliminating repetitive manual lookups.

### **Result (Phase 1)**

* Reduced data-gathering time from **15–20 minutes to ~2–3 minutes**
* Improved on-call efficiency and reduced fatigue
* Achieved rapid adoption across the team with **zero backend changes**

---

### **Action (Phase 2: Scaled Innovation with AI)**

As AI tooling matured, I extended this idea further:

* Collaborated with multiple teams facing the same problem
* Designed an architecture leveraging an **internal AI ticket agent**
* Enabled automatic:

    * Data aggregation from downstream systems
    * Context-aware summaries
    * Suggested next steps based on SOPs

Worked with 2 engineers to productionize this solution.

### **Result (Phase 2)**

* Reduced **end-to-end ticket resolution time from ~1 hour to 5–10 minutes**
* Standardized debugging workflows across teams
* Enabled handling of new/unstructured issues by feeding learnings back into SOPs

---

## **Why this works well in interviews**

This story demonstrates:

* **Innovation under constraints** (no heavy system build initially)
* **Pragmatic engineering thinking** (browser script → AI system evolution)
* **End-to-end ownership** (idea → prototype → adoption → scaling)
* **Measurable impact** (clear time reduction)
* **Collaboration and influence**

---

## **If interviewer probes deeper**

### 1. *Why not build a backend system directly?*

Because it would require cross-team alignment and significant investment. I optimized for **speed and impact first**, then scaled once value was proven.

### 2. *Technical challenges?*

* Handling auth/session across multiple services
* Normalizing heterogeneous API responses
* Ensuring script reliability despite UI changes

### 3. *What made this innovative?*

Not the tool itself, but:

* Identifying a **high-friction workflow**
* Using an **unconventional but effective approach**
* Evolving it into a **scalable AI-driven system**

---

## **One-line summary (for quick answers)**

“I reduced on-call debugging time from an hour to under 10 minutes by first building a lightweight browser-based data aggregation tool and later scaling it into an AI-driven ticket assistant.”

---

If needed, this can be shortened to a **60–90 second version** or adapted specifically for companies like Airbnb or Confluent.

