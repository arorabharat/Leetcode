# DoR Project - Structured Interview Stories
## Story 1: Owning Outcome - Stakeholder Management & Project Leadership
**Situation:**
Amazon was mandated by EU and French laws to surface inventory information to vendors within 3-10 days
of receiving shipments. This addressed a critical vendor pain point where vendors would ship 100 units but
Amazon would only record receiving 80, leading to payment disputes and cashflow issues for vendors. The
project had regulatory deadlines (end of 2024) and needed to scale from a dozen pilot vendors to 2000+
eligible vendors.
**Task:**
As the most experienced SDE (5 years domain experience), I needed to lead the technical solution for
automatically resolving vendor disputes during the receive process. The system needed to handle 95% of
disputes automatically, saving Amazon ~$1M per quarter. I had to coordinate across 8 different teams with
varying priorities and technical constraints.
**Action:**
- **Stakeholder Management with Conflict Resolution:**
- *SCOT Team Pushback:* When dashboard team displayed incorrect vendor data from our system, I
  proposed we own the data views since we understand it best. They declined due to existing work. I conducted
  example analysis, documented major data issues, and worked collaboratively to fix critical problems while
  respecting their timeline constraints
- *Finance Team Pushback:* Finance PM wanted 100% automation to eliminate manual roles. I pushed back
  citing vendor impact and delivery timelines, negotiating a phased approach with future SKU/quantity
  confidence algorithms and stolen goods detection
- *Resolution Speed Concerns:* When pushed for faster dispute resolution, I presented metrics showing high
  inventory settlement times, explaining how premature resolution could negatively impact other vendors
- *Competing Team Relations:* Had to re-engage the team we initially beat for the project to implement a
  bridge feature. Faced resistance questioning our solution's superiority. Collaborated with my manager to
  appreciate their strengths and proposed a partnership model for next year's expansion
- **Technical Leadership & Coordination:** Led technical direction for 8-month project (Feb-Oct 2024),
  coordinating with SDE3 (Finance/TransferX discussions) while I guided 1 SDE2 and directed task allocation
  for 3 SDE1s on complex Inventory/Adjustment integration. Responsible for work decomposition, quality
  execution, and delivery timelines across the team
- **Technical Strategy:** Developed multi-tiered algorithm using confidence signals from OCR, ASN data, and
  associate inputs, with escalation paths for edge cases
- **Risk Management:** When dependencies failed (Receive team signals, TransferX API), quickly re-
  architected solution, conducted shadow mode testing, and delivered stripped-down but effective version
  **Result:**
- Successfully launched 8-month regulatory compliance project on time (Feb-Oct 2024)
- Achieved $1M quarterly savings, $10M annualized
- 90%
+ dispute auto-resolution rate across 2000+ vendors
- Gained significant leadership visibility and praise
- Delivered critical EU/French regulatory compliance
---
## Story 14: Leadership - Relationship Building and Conflict Resolution
**Situation:**
During the PO Centralization project, I needed to gather metrics from other teams to present potential
improvements to leadership. When I set up a meeting with one team's PM and Manager to request their help,
they rudely turned me away without offering any assistance. This left me confused about their hostile
response to what seemed like a reasonable request.
**Task:**
I needed to understand the root cause of their rejection, find alternative ways to gather the required metrics,
and ensure our project could proceed despite the inter-team conflict.
**Action:**
- **Root Cause Analysis:** Reached out to my manager and skip-level to understand the context behind their
  behavior. Learned that our org had previously declined/ignored their project proposals, creating resentment
- **Relationship Leverage:** Identified that I had built a good relationship with one of their engineers by
  regularly helping him with domain knowledge whenever he reached out
- **Alternative Approach:** Leveraged this existing relationship to identify and gather the required metrics
  without going through hostile management
- **Long-term Thinking:** Continued maintaining cordial relationships across teams without expecting
  immediate returns
- **Successful Outcome:** Completed the comprehensive report for leadership presentation despite the initial
  roadblock
  **Result:**
- Successfully gathered all required metrics and completed leadership presentation
- Leadership was convinced by our solution and approved the PO Centralization proposal
- The initially hostile team returned 2 months later and accepted our proposal after seeing leadership buy-in
- Demonstrated that building genuine relationships early pays dividends during challenging situations
- Established principle of investing in cross-team relationships proactively rather than reactively
---
## Story 12: Organizational Impact - Customer Data Support
**Situation:**
Our team owned the "speed dating" service that translates expiration dates to standard format based on
locale. A customer team urgently requested data around ingested and translated dates for their analysis.
While I could have redirected them to another team that calls our service and might have the data, I
recognized the urgency of their need and the potential delays that redirection would cause.
**Task:**
I needed to quickly provide the customer team with the specific dataset they required while maintaining focus
on my primary responsibilities.
**Action:**
- **Customer-First Approach:** Rather than redirecting to another team, chose to directly help the customer
  despite it not being my primary responsibility
- **Rapid Solution Development:** Wrote a quick database scraping script to extract the required ingested
  and translated date information
- **Fast Delivery:** Provided the complete dataset within one day, significantly faster than the multi-team
  coordination alternative would have allowed
- **Knowledge Sharing:** Documented the approach so similar requests could be handled eﬃciently in the
  future
  **Result:**
- Customer received critical data within 24 hours instead of potentially days of coordination
- Demonstrated customer-obsessed approach that built strong cross-team relationships
- Created reusable solution for similar future data requests
- Strengthened team's reputation for customer service and technical agility
---
## Story 13: Organizational Impact - Japan FBA Premium Inbound Onboarding
**Situation:**
Japan was launching FBA Premium Inbound project to improve seller experience and compete with local
online marketplaces. The Japan team was working in an away-team model and needed to understand our
domain to implement their requirements. They needed comprehensive onboarding to our complex workflow
and potential implementation gotchas.
**Task:**
I needed to effectively onboard the Japan team to our domain expertise, ensuring they could successfully
implement their seller experience improvements without falling into common pitfalls.
**Action:**
- **Comprehensive Knowledge Transfer:** Conducted 4-5 detailed KT sessions covering our current workflow,
  their specific requirements, and potential implementation challenges
- **Proactive Guidance:** Identified and shared potential gotchas based on my domain experience to prevent
  future issues
- **Design Review Support:** Reviewed their technical designs and code changes to ensure alignment with
  our system architecture
- **Continuous Support:** Made myself available for follow-up questions and guidance throughout their
  implementation
  **Result:**
- Japan team successfully implemented FBA Premium Inbound improvements
- Received excellent feedback from both their manager and team members
- Enabled successful seller experience improvements in competitive Japanese market
- Established strong cross-regional collaboration model for future projects
- Demonstrated ability to effectively transfer complex domain knowledge
---
## Story 11: Leadership - Configuration Management Code Quality Recovery
**Situation:**
A configuration management project was being developed by support engineers under tight deadline
pressure. During code review, I identified significant issues with code organization, quality, and architecture.
The configuration system was completely hard-coded and brittle - any new configuration type would require
at least a week of development effort. The support engineers were struggling to address the technical debt
comments, and we were approaching the delivery deadline.
**Task:**
The TPM requested that I take over the code fixes to ensure timely delivery. I needed to rapidly improve code
quality while meeting the deadline and creating a sustainable solution for future configuration needs.
**Action:**
- **Technical Assessment:** Identified that the hard-coded configuration parsing mechanism was the core
  architectural flaw limiting scalability
- **Rapid Prototyping:** Created POC using industry standards (JSON schema for configuration declaration,
  open source library for parsing and form rendering)
- **Architecture Redesign:** Demonstrated how the new approach would reduce code size by 10X and enable
  new configuration types to be added in 10 minutes instead of a week
- **Full Implementation:** After team approval, completely rewrote the Angular application in one week,
  transforming the architecture from hard-coded to schema-driven
- **Knowledge Transfer:** Ensured the support engineers could maintain and extend the new system
  **Result:**
- Delivered project on deadline despite significant code quality issues
- Reduced future configuration development time from 1 week to 10 minutes
- 10X code size reduction improved maintainability and reduced technical debt
- Became a major highlight contributing to my promotion
- Established scalable pattern for configuration management across the team
---
## Story 8: Operational Excellence - Peak Traﬃc Automation Tool
**Situation:**
Every peak period (Prime Day, Black Friday), all teams had to dedicate 2-3 weeks of SDE bandwidth for peak
readiness preparation. This involved manually identifying current traﬃc levels, estimating peak traﬃc for all
APIs, gathering client peak requirements, communicating with dependencies, and documenting everything in
wikis. The traﬃc analysis alone was a manual, error-prone process taking 3-4 days per team.
**Task:**
I needed to streamline this critical but repetitive process that was consuming significant engineering
bandwidth across multiple teams during already high-pressure periods.
**Action:**
- **Problem Analysis:** Identified that traﬃc analysis was the biggest time sink and most error-prone step in
  peak preparation
- **Automation Development:** Built Python script that automated the entire process by integrating with
  monitor portal and shipment forecast APIs
- **Cross-team Impact:** Shared the tool with sister teams facing the same challenges
- **Process Transformation:** Reduced 3-4 days of manual work to 10 minutes of automated analysis for all
  APIs and services
  **Result:**
- Saved multiple teams significant bandwidth during critical peak periods
- Eliminated human errors in traﬃc estimation and dependency communication
- Earned Innovation Maven award for organizational impact
- Tool became standard practice across related teams for peak readiness
---
## Story 9: Operational Excellence - Dashboard Threshold Management
**Situation:**
Maintaining accurate monitoring thresholds and keeping dashboards updated with current thresholds was an
ongoing operational challenge. Teams frequently had outdated thresholds on dashboards, leading to false
alarms or missed alerts during critical periods.
**Task:**
I needed to solve the operational overhead of keeping multiple dashboards synchronized with the most
current monitoring thresholds across our services.
**Action:**
- **Root Cause Analysis:** Identified that manual threshold updates across multiple dashboards was the
  bottleneck
- **Solution Development:** Created automated script that updates all dashboards with current thresholds in
  a single click
- **Knowledge Sharing:** Shared the solution across teams facing similar dashboard management issues
- **Standardization:** Helped establish this as a best practice for dashboard maintenance
  **Result:**
- Eliminated manual dashboard maintenance overhead
- Reduced false alerts and improved monitoring accuracy
- Earned second Innovation Maven award for operational excellence
- Improved team's ability to respond quickly during incidents with accurate dashboards
---
## Story 10: Leadership - PO Allocation Algorithm Experiment
**Situation:**
Our PO allocation algorithm was too static and relied on limited signals, leading to suboptimal purchase order
assignments. An SDE3 had proposed a new approach using multiple signals to improve PO allocation
confidence, but leadership was hesitant about implementing such a massive change worldwide without proof
of concept.
**Task:**
Drawing from my PONYS project failure experience, I needed to design a risk-mitigation approach that would
validate the proposed algorithm changes before full implementation.
**Action:**
- **Applied Learning:** Used lessons from PONYS failure to insist on validation before full rollout
- **Experiment Design:** Proposed shadow mode testing in a couple of fulfillment centers to measure
  potential gains and build leadership confidence
- **Technical Leadership:** Designed both HLD and LLD for the shadow mode implementation
- **Implementation Management:** Worked with SDE1 for development while ensuring zero impact to
  production systems
- **Data-Driven Validation:** Collected and analyzed metrics to demonstrate algorithm improvements
  **Result:**
- Shadow mode experiment confirmed minimum 60% improvement in PO allocation confidence
- Zero production impact or downtime during experiment
- Gained leadership buy-in for worldwide implementation based on concrete data
- Earned Master of Delivery award for risk-conscious approach to major system changes
- Demonstrated successful application of failure learnings to prevent repeat issues
---
## Story 6: Organizational Impact - Mentoring & Knowledge Sharing
**Situation:**
Throughout my tenure, I've mentored ~10 interns and new hires, including informal mentoring of 1 SDE2 and
2 SDE1s who reached out personally for guidance. Additionally, I regularly encountered complex internal
frameworks with poor documentation that teams avoided working with.
**Task:**
I needed to help struggling team members grow while also democratizing knowledge about complex systems
across the organization.
**Action - Mentoring Examples:**
- **Intern Success:** When an intern struggled with Java and delivery timelines, I worked with manager to
  restructure his projects, starting with simpler, less deadline-driven tasks. Provided consistent encouragement
  focusing on growth rather than starting point. Result: intern delivered impeccably on a big task and converted
  to full-time
- **SDE2 Guidance:** When a recently promoted SDE2 reached out privately about deadline anxiety, I
  identified root cause: lack of sprint mechanism leading to overcommitment. Guided them on work tracking,
  expectation management, and appropriate pushback strategies
- **Comprehensive Onboarding:** Created wiki and curated video playlists for new hires to understand our
  tools and field usage before FC visits
  **Action - Knowledge Sharing:**
- **Framework Documentation:** Took on avoided internal framework project, read entire codebase, created
  comprehensive documentation, and delivered KT sessions to all India teams. Discovered hidden hooks and
  built visual debugger tool
- **Multiple Technical Sessions:** Led 10+ sessions on build systems, Angular, internal PO system brownbag,
  and production service one-box setup without downtime
- **Innovation Recognition:** Awarded "Innovation Maven" for framework contributions and tooling
  **Result:**
- Successfully converted struggling intern to full-time employee
- Helped SDE2 overcome promotion challenges and establish healthy work practices
- Enabled multiple teams to work with previously avoided framework
- Created reusable onboarding resources reducing ramp-up time for new hires
- Established reputation as go-to person for technical guidance and knowledge transfer
---
## Story 7: Owning Outcome - Incremental Delivery & Risk Management (DoR Project)
**Situation:**
The 8-month DoR project (Feb-Oct 2024) had hard regulatory deadlines, multiple dependencies, and complex
integration requirements across 8 teams serving 2000+ vendors.
**Task:**
I needed to structure delivery to manage dependencies, reduce risk, and ensure regulatory compliance while
maintaining quality and enabling early feedback.
**Action - Incremental Delivery Strategy:**
- **Phase 1 (Feb-Apr):** Algorithm development and validation using dry-run simulators against historical
  data. Validated 90% inventory availability assumption before committing to full implementation
- **Phase 2 (Apr-Jun):** Core dispute ingestion and resolution service development. Built modular
  architecture allowing parallel work on different resolution paths
- **Phase 3 (Jun-Aug):** Integration testing with shadow mode deployment. Identified and resolved
  dependency failures (Receive team signals, TransferX APIs) early
- **Phase 4 (Aug-Oct):** Production rollout with graduated traﬃc increase. Real-time monitoring and quick
  iteration based on actual dispute patterns
  **Risk Management:**
- **Dependency Validation:** Created multiple validation scripts early to surface integration issues before full
  development
- **Fallback Planning:** When dependencies failed, pivoted to stripped-down solutions without compromising
  core functionality
- **Parallel Workstreams:** During Sev-1 crisis response, structured work so other team members could
  continue DoR development
- **Stakeholder Alignment:** Regular milestone reviews with all 8 teams to surface conflicts early and adjust
  timelines
  **Result:**
- Delivered regulatory compliance exactly on deadline despite dependency failures
- Early validation prevented potential project failure (learned from PONYS experience)
- Modular delivery enabled quick adaptation to changing requirements
- Shadow mode caught integration issues before production impact
- Achieved 90%
+ auto-resolution rate from day one due to thorough incremental testing
---
## Story 5: Learning from Failure - PONYS Project (2022)
**Situation:**
Sellers and vendors were complaining that POs they hadn't shipped were showing as "received" by Amazon,
preventing them from shipping through the portal. They had to contact support to proceed, causing delays
and payment inaccuracies. This was impacting seller experience and operational eﬃciency.
**Task:**
Working with a senior PM, I needed to understand the root cause and develop an automated solution to
identify and handle these "PONYS" (PO Not Yet Shipped) scenarios without manual intervention.
**Action:**
- **Problem Analysis:** Conducted deep-dive with seller experience team, analyzed complaint tickets to
  understand pain points and impact patterns
- **Solution Development:** Theorized potential algorithmic solutions and ran month-long simulation against
  1 year of historical data in our data lake
- **Parameter Optimization:** Tested different parameter values to maximize accuracy of PONYS
  identification
- **Implementation Strategy:** Designed solution to sideline detected items for virtual resolution, modeling on
  existing NO_PO flow processes
- **Phased Rollout:** Worked with ICQA team on SOP formulation, implemented with SDE1, launched in EU
  FCs first, then expanded to US after smooth EU launch
  **Result - The Failure:**
- EU launch went smoothly, but US expansion completely failed
- US FCs operated with fundamentally different processes that broke all our assumptions
- Even the existing NO_PO flow (our reference model) didn't work properly in US
- Had to completely roll back the project after 5 months of development (3 months analysis + 2 months
  implementation)
- Major learning: Code, documentation, and even PM knowledge may not reflect ground reality
  **Key Lessons Applied to Future Projects:**
- **Assumption Validation:** Always validate assumptions directly with operations teams across all regions
- **Fail Fast Methodology:** Test solutions with minimal viable implementations before full development
- **Ground Truth Priority:** Floor processes trump documentation/code understanding
- **Applied in DoR Project:** Created multiple dry-run scripts and simulators to validate logic against live data
  before implementation, preventing similar failure
---
## Story 2: Operational Excellence - Innovation & Process Improvement
**Situation:**
During the DoR project, manual dispute analysis was taking 30-40 minutes per example, significantly slowing
down our validation process. Additionally, the PM was concerned about potential inventory shortage issues
when resolving disputes, which could impact our success metrics.
**Task:**
I needed to accelerate our analysis process and validate our algorithm assumptions before full
implementation to avoid repeating a previous project failure where ground reality differed from
documentation, leading to complete rollback after 5 months of work.
**Action:**
- **Innovation with Tools:** Leveraged my prior experience with data lake platform to create an automated
  script that pulled manual examples and generated analysis in minutes instead of 30-40 minutes
- **Proactive Validation:** While PM assigned a BI for inventory analysis, I independently built a dry-run
  simulator working with inventory team contacts to test our logic against live data
- **Knowledge Sharing:** Shared the analysis tools with SDE3 and SDE2, enabling faster team-wide analysis
- **Applied Learning:** Used lessons from previous project failure to validate assumptions early rather than
  discovering issues post-implementation
  **Result:**
- Reduced analysis time from 30-40 minutes to 5 minutes per example
- Validated that 90% of disputes would have available inventory for adjustment
- Enabled rapid iteration and validation before full service development
- Prevented potential project failure by validating ground reality early
- Saved significant engineering time across the team
---
## Story 3: Leadership - Crisis Management & Strategic Thinking
**Situation:**
Mid-DoR project, our team faced a critical Sev-1 incident. POService (recently acquired from a reorganized
team <3 months prior) - a Tier 1 service critical for all inbound, transshipment, and outbound processes - went
down across all US fulfillment centers on a weekend. CPU was saturated, services were browning out, and
clients were timing out. This threatened both immediate operations and our DoR project timelines.
**Task:**
Despite not being on-call, I needed to lead the immediate crisis response, identify root cause, and develop
comprehensive prevention measures while maintaining DoR project momentum with regulatory deadlines
approaching.
**Action:**
- **Immediate Crisis Response:** Took over when SDE3 called me, analyzed metrics/alarms, identified CPU
  saturation cause, scaled service with support engineers, and implemented immediate input limit fix (500 POs
  vs 5000+) after traﬃc analysis
- **Root Cause Analysis:** Conducted deep-dive revealing 5-month chain reaction: RPOS API timeout
  reduction → Watson service workflow failures → 2000+ failed workflows → on-call mass retry + increased
  DDB capacity (for Prime Day) eliminated previous throttling protection
- **Comprehensive Documentation:** Wrote detailed CoE document with blow-by-blow analysis, impact
  assessment ($-value, unit processing, delivery misses), what went well/poorly during incident response, and
  categorized short/medium/long-term prevention action items
- **Strategic Project Defense:** Separately analyzed competing team's DoR proposal, identified technical
  flaws, drafted counter-proposal leveraging our domain expertise
- **Parallel Execution:** Onboarded SDE2 with DoR project context, identified workstreams that could
  continue during crisis response to minimize regulatory timeline impact
  **Result:**
- Restored service stability and prevented future cascading failures
- CoE document praised by 3 Principal Engineers and Director for analytical depth and comprehensive
  prevention strategy
- Successfully defended DoR project ownership, gained leadership confidence
- Completed 1-month crisis response while maintaining DoR project timeline
- Established new service ownership best practices for team transitions
---
## Story 4: Organizational Impact - Cross-Team Collaboration & Knowledge Sharing
**Situation:**
The DoR project required deep integration across Amazon's supply chain ecosystem, with multiple teams
having overlapping PO identification logic causing ineﬃciencies. This built on my earlier proposal for
centralizing PO allocation logic across the organization.
**Task:**
I needed to understand and optimize the broader supply chain process beyond just our team's scope, while
sharing knowledge and tools that could benefit the wider organization.
**Action:**
- **Cross-Org Investigation:** Deep-dived into Inventory Adjustment team's processes (different L8/director),
  identified overlaps and unique approaches in their PO identification algorithm
- **Knowledge Synthesis:** Connected insights from PO centralization project with DoR requirements to
  create more comprehensive solution
- **Tool Creation & Sharing:** Developed analysis tools and dry-run simulator that I shared across teams,
  enabling faster validation for similar projects
- **Mentoring & Growth:** Guided multiple engineers through complex technical decisions and deep-dive
  analysis processes
- **Documentation:** Created comprehensive design documents that were used by compliance teams and
  became reference for similar projects
  **Result:**
- Enabled better cross-team coordination and reduced PO allocation conflicts
- Created reusable tools and processes adopted by other teams
- Established expertise that positioned team as go-to for similar supply chain challenges
- Contributed to broader IMR (Invoice Mismatch Rate) reduction initiative
- Enhanced organizational capability for handling complex multi-team projects
---
## Key Gaps to Address with Other Stories
Based on Atlassian's criteria, you might need additional examples for:
1. **Mentoring/Coaching Specific Examples** - Do you have stories about formal mentoring relationships?
2. **Brownbag Sessions/Knowledge Sharing** - Any presentations or knowledge-sharing sessions you've led?
3. **Incremental Delivery & Milestones** - More specific examples of how you broke down this or other
   projects?
4. **Customer Focus** - Any examples where you directly interacted with external customers or end-users?
5. **Failure/Learning Examples** - The story mentions a previous project that was rolled back - can you
   elaborate on that for failure/learning questions?
   Would you like to develop any of these areas further, or do you have other projects that could fill these gaps?