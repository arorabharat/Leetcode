### Interview Round — Summary

* This round is a **repo-based implementation + system design** exercise.
* You will receive an existing codebase/repository with multiple files. Since you have only **45 minutes**, you cannot manually read everything.
* You are expected to use **AI extensively but appropriately**.

### Expected approach

1. **Codebase Orientation**

    * Start by exploring the repository.
    * Give AI a well-structured prompt asking for:

        * Comprehensive breakdown of the codebase
        * Existing architecture
        * Important components/classes
        * How the pieces interact
        * Relevant files for the requested feature
        * Specific areas that need implementation/modification
    * Do not guide AI too much initially; let it independently understand the codebase.

2. **Plan Mode**

    * Use AI's **Plan Mode** to understand the requirements and formulate an implementation approach.
    * As you clarify requirements with the interviewer, continuously refine the plan.
    * Eventually, the solution should cover the **complete product**, including frontend and backend where applicable.

3. **Directive Prompts**

    * Give AI clear, specific instructions rather than vague prompts.
    * If you know certain errors may occur, explicitly tell AI to **silently ignore those errors and continue with the relevant analysis** where appropriate.

4. **Probe / Question AI**

    * Don't blindly accept AI's output.
    * Ask follow-up questions such as:

        * Why did you choose this approach?
        * What are the alternatives?
        * Is this logic correct?
        * What are the trade-offs?
        * What happens in edge cases?
    * Use AI to **gain understanding**, not just generate code.

5. **Verify Against Requirements**

    * Take the AI-generated design/code and verify it against the actual requirements.
    * Check whether all requirements and edge cases are covered.
    * Validate the logic before implementation.

6. **Implementation**

    * Once the approach is validated, implement the changes in the existing codebase.
    * The core **problem-solving logic should come from you**; AI should accelerate implementation.

### If the language is unfamiliar

* If the repository is in a language you don't know well, you can:

    * Develop the core logic/pseudocode in a language you know.
    * Validate the logic/output.
    * Ask AI to translate it into the repository's language.
    * Carefully verify the translated implementation against the requirements.

### Areas they will evaluate

**1. Problem Solving**

* How you explore and break down a large problem.
* How you divide it into manageable pieces.
* How you move from understanding → design → implementation.
* They want to see you **drive the problem**, rather than letting AI drive it.

**2. AI Fluency**

* How effectively you use AI throughout the exercise.
* Whether you know when to use AI and when not to.
* Whether you use different AI modes appropriately:

    * Explore/codebase orientation
    * Plan
    * Ask
    * Agent
* Avoid both **over-dependence and under-utilization** of AI.

**3. Verification**

* How well you validate AI's output.
* Whether you question incorrect or questionable suggestions.
* Whether you verify the design and implementation against requirements.
* Whether you understand *why* the generated solution works.

**4. Adaptability**

* How well you work with an **existing codebase**.
* How effectively you add new functionality without unnecessarily disrupting the existing architecture.

### System/design aspects they expect awareness of

While implementing, consider:

* Frontend + backend
* Asynchronous/distributed workflows
* Authentication and authorization
* OAuth
* JWT configuration
* Sensitive-data protection
* Authentication/authorization checks at the executor/service layer
* Secure handling of sensitive fields
* Telemetry/observability
* Service-layer design
* Storage requirements and implications
* Security considerations
* Existing architectural patterns and conventions

### Trade-offs

When AI provides multiple approaches:

* Don't simply select one.
* Explore what each option does.
* Discuss the **trade-offs**.
* Explain why you selected one approach over the alternatives.

### Overall sequence to follow

**Explore → Plan → Clarify requirements → Direct AI → Probe AI → Verify against requirements → Implement → Validate**

The key message from the interviewer was:

> **Use AI to accelerate your problem solving, but you should remain the person driving the solution.**

### Interview scheduling

* Interview can be scheduled **next week**.
* Monday, Tuesday, or Wednesday were mentioned as available options.
* You said you would confirm the timing before Thursday.

Prepare for the interview

* Draft reusable AI prompts
* Create a 45-minute execution plan
