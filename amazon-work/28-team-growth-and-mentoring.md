# Hiring, mentoring, and onboarding

While working on Prime payments, a campus hire joined our team. He was naturally sharp and great at hands-on debugging, but he had a habit common among early-career engineers: the moment he was handed a problem, he’d jump straight into writing code without taking a step back to think through the architecture.

Whenever things were smooth, he moved quickly. But the moment he hit an edge case or got blocked, his velocity crashed. He would walk up to senior engineers’ desks, try to explain the issue verbally on the fly, and ask for advice. Because distributed systems context is rarely communicated accurately in a two-minute chat, people often pointed him in the wrong direction. In one instance, someone suggested a configuration change that completely missed the real root cause, and he lost nearly two weeks chasing down a dead end.

I realized the issue wasn't his technical capability; it was that nothing was written down.

I set up a dedicated 1-on-1 with him. Instead of giving him top-down feedback, I opened with empathy—I told him I’d fallen into the exact same trap early in my career. I explained that writing things down does two critical things:

1. **Asynchronous Clarity:** It eliminates the friction of verbally pitching an incomplete problem to busy seniors. If you put your hypothesis, logs, and attempted fixes in a Jira or short doc, people can review it with full context and give you precise guidance.
2. **Self-Review:** The act of writing forces you to slow down and exposes the blind spots in your own mental model before you type a single line of code.

Around that time, we were tasked with launching **UPI QR codes for Prime payments**. Other Amazon businesses already supported it, but Prime was lagging behind. I deliberately used this project as a live coaching ground.

I had him lead the execution, but with a strict ground rule: before touching the codebase, he had to map out an end-to-end configuration and dependency doc across every touchpoint.

As he began documenting the cross-service touchpoints, the value clicked for him immediately. Walking through the doc with downstream payment teams revealed several critical cross-service configurations that had to be synchronized across multiple backend engines. If he had relied on his old habit of jumping straight to coding, those missing configurations would have slipped past development and resulted in broken customer checkouts on launch day.

By shifting his approach, he caught every single edge case before production. The UPI QR feature rolled out smoothly, he significantly cut down his reliance on impromptu desk stops, and he became much more self-sufficient in driving cross-team designs. It taught him early on that in large-scale distributed systems, slowing down to document and design upfront is actually what allows you to move fast.

---

### **Why Interviewers Will Rate This Highly**

1. **Atlassian Value Match:** Fits directly with *"Play as a team"* and *"Open company, no bullshit"*. You gave candid, constructive feedback with zero ego.


2. **Defensive Systems Thinking:** Proves you understand that the cost of catching a bug during the design/RFC phase is 100x cheaper than catching it after production deployment.


3. **Behavioral Shift:** You didn't just solve his bug for him; you permanently upgraded his way of working, raising the operational bar for the entire team.
 
--------------------------------------------

## Summary

This is an additional Amazon leadership responsibility documented in the resume, rather than a standalone technical project.

## Resume-level contribution

The resume says you:

- Conducted more than 25 SDE interviews.
- Mentored multiple interns.
- Onboarded new engineers to support team integration and delivery velocity.

## Additional examples in interview notes

The DoR/project-story document adds detail:

- Mentored about ten interns and new hires, plus informal mentoring of one SDE2 and two SDE1s.
- Helped an intern who was struggling with Java and timelines by working with the manager to re-scope work into manageable tasks; the intern later delivered a significant task and converted to full-time.
- Guided a recently promoted SDE2 on tracking work, managing expectations, and avoiding overcommitment.
- Created onboarding materials and curated videos for new hires.
- Led technical sessions on build systems, Angular, internal PO systems, and production service setup.

### Coaching a junior engineer on documenting technical work

A separate feedback example describes a technically strong teammate who had joined straight out of college and was good at deep dives and debugging. He frequently became blocked on complex tasks and asked the narrator for help. The account distinguishes between issues that genuinely required broader architectural context and cases where the engineer could likely have found the next step independently.

The identified behavior was jumping from problem-solving directly into implementation without writing down the problem, approach, assumptions, architecture context, or debugging progress. When the teammate then asked senior engineers for help, they lacked the context of the work; several conversations were needed to reconstruct it, and context was lost between discussions.

You arranged a private one-on-one rather than raising the issue in stand-up. You acknowledged the teammate’s technical strengths, then explained how documenting his reasoning and progress could make the work visible, help senior engineers give targeted help faster, and reveal gaps he might resolve himself. You agreed to observe and review documentation on his next couple of tasks.

The story says he began documenting his work and became better able to identify issues and unblock himself. It does not quantify a reduction in help requests or cycle time. This source does not name the employer, so use it as a related coaching example, not as confirmed Amazon-specific evidence.

## Outcome and caveats

The notes describe improved onboarding, knowledge sharing, and individual growth, but provide no aggregate hiring-quality, ramp-time, or retention metrics. Treat the “about ten” and “25+ interviews” figures as resume/story claims to verify. This dossier does not include individual employee details.

## Sources

- `resume-v1/src/experience.tex`
- `behaviour-round/non-tech-skills/AbhirajInterviewQuestion.md`
- `behaviour-round/non-tech-skills/Summary.md` (separate brief mentoring example; no further detail)
- `behaviour-round/non-tech-skills/PeerFeedbackGivingSkills.md` (detailed junior-teammate feedback account; employer is not specified)
