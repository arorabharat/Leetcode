# Project Summary (1–2 pager)

## Card-on-File Tokenization Compliance (RBI) – Prime Recurring Payments

### 1. Executive Summary

In **2022**, the **Reserve Bank of India (RBI)** mandated **Card-on-File (CoF) tokenization**, prohibiting merchants from storing raw card details (PAN, expiry, CVV). The objective was to **improve security of digital payments** and reduce risk of card data breaches.

As **Prime is a subscription-based recurring business**, this regulation posed a **major continuity risk**. Recurring payments depend on stored payment credentials. Without tokenization, **millions of recurring subscriptions would fail**, leading to:

* involuntary churn
* revenue loss
* degraded customer experience
* regulatory non-compliance risk

At the time of rollout, **guidelines for handling existing recurring mandates were still evolving**, creating ambiguity across payment processors, gateways, and merchant systems.

As **Prime POC**, I drove technical and cross-team efforts to ensure:

* compliance with RBI mandate
* continuity of recurring payments
* minimal disruption to existing customers
* seamless onboarding for new customers

We successfully ensured **business continuity for ~10–15 million existing customers** and enabled compliant recurring payment flows for future customers.

---

### 2. Regulatory Context

RBI introduced CoF tokenization to ensure merchants do not store raw card details. Instead, card networks (Visa/Mastercard/RuPay) issue a **token**, which is a surrogate identifier mapped to the underlying card.

Key requirements:

* merchants cannot store card number (PAN)
* token must be generated via customer consent
* Additional Factor Authentication (AFA) required during token creation
* tokens are merchant-specific and secure
* recurring payments must use tokenized cards

Deadline for full compliance: **Oct 1, 2022**

---

### 3. Problem Statement

Prime relies heavily on **recurring card payments**.

Before tokenization:

* merchants stored card details securely (PCI compliant)
* recurring charges executed without requiring user interaction

After RBI mandate:

* stored card data had to be deleted
* recurring charges could not continue without tokenized credentials
* existing customers needed migration to tokenized cards

Risk exposure:

| Risk                                   | Impact                                      |
| -------------------------------------- | ------------------------------------------- |
| loss of stored card credentials        | recurring payments fail                     |
| user friction in re-authentication     | drop in renewal rates                       |
| lack of clarity in ecosystem readiness | inconsistent implementation across gateways |
| tight regulatory deadline              | high operational urgency                    |

Approximately **10–15 million existing Prime customers** had stored cards linked to recurring billing.

Without migration:

* recurring payments would fail
* large revenue impact
* high involuntary churn

---

### 4. Scope of Ownership

As Prime POC, responsibilities included:

* evaluating tokenization solutions from Payments org
* ensuring compatibility with Prime recurring systems
* identifying risks to business continuity
* defining customer experience strategy
* coordinating across multiple partner teams
* ensuring scalable rollout within regulatory timeline

Key partner teams:

* Amazon Payments
* BillDesk
* card networks
* Subscribe & Save
* Kindle subscriptions
* Audible subscriptions
* checkout platform teams

---

### 5. Key Technical Challenges

#### Challenge 1: Migration of existing stored cards

Existing customers had cards stored in legacy format.

Key question:
How to migrate stored cards → tokenized cards without requiring manual re-entry for millions of users?

Constraints:

* tokenization requires customer consent
* recurring mandates already configured on legacy cards
* avoid breaking renewal cycle
* minimize customer friction

---

#### Challenge 2: Ensuring continuity of recurring payments

Recurring transactions require:

* valid payment credential
* valid mandate
* token mapping continuity

Any break in payment credential mapping would:

* cancel auto-renewal
* require customer re-onboarding
* cause churn

---

#### Challenge 3: Customer experience design

Need to ensure:

* customers provide consent for tokenization
* friction minimized
* conversion impact minimal
* compliance messaging clear

Key design question:
Where in the journey should token consent be collected?

---

#### Challenge 4: High scale rollout

Prime operates on high TPS surfaces (eg homepage surfaces).

Tokenization integration had to handle:

* high concurrency
* low latency
* reliability of token provisioning
* dependency on external payment gateways

---

### 6. Solutions Implemented

We executed a multi-pronged strategy.

---

#### Solution A: Tokenization during checkout flows

Introduced UI components across Prime purchase journeys:

* consent capture for tokenization
* clear messaging on recurring charge setup
* enforcement of token creation before completing subscription purchase

Impact:

* ensured all new customers were compliant
* recurring mandate setup supported tokenized cards

---

#### Solution B: Migration strategy for existing customers

Collaborated with Payments org and partners to migrate stored cards to tokens.

Key initiatives:

* token creation campaigns
* migration workflows to map legacy card → token
* minimize need for customers to re-enter card details
* preserve continuity of existing mandates

Impact:

* preserved recurring setup for large portion of customer base
* reduced churn risk

---

#### Solution C: UI enforcement layers

Introduced guardrails in Prime signup journey:

* customers informed when tokenization required
* prevented progression without valid token setup
* ensured recurring charge compatibility

Impact:

* prevented invalid recurring configurations
* improved compliance consistency

---

#### Solution D: Cross-team alignment

Worked closely with:

* Payments platform teams
* partner gateway teams
* subscription platform teams

Activities:

* design reviews
* integration validation
* rollout planning
* risk mitigation strategy

---

### 7. Impact Delivered

#### Business continuity preserved

* ensured recurring setup for ~10–15 million customers
* prevented large-scale subscription churn

#### Regulatory compliance achieved

* Prime recurring flows aligned with RBI tokenization mandate

#### Customer experience preserved

* minimized need for manual card re-entry
* seamless token consent integration

#### Scalable architecture enabled

* token-based recurring payment compatible with future regulatory evolution

---

### 8. Key Learnings

#### operating under regulatory ambiguity

payment ecosystem participants often interpret guidelines differently initially.

#### importance of customer experience in compliance projects

regulatory changes can introduce friction if UX not carefully designed.

#### cross-org collaboration critical for payment systems

recurring billing involves multiple dependencies across org boundaries.

#### phased rollout reduces risk

fallback strategies ensure continuity even if primary approach encounters issues.

---

### 9. How to explain in interview (short version)

"I worked on RBI Card-on-File tokenization compliance, which required merchants to stop storing raw card details and instead use tokenized cards. This posed a major risk for Prime’s recurring business, as millions of customers had stored cards configured for auto-renewal.

As Prime POC, I worked with Payments and partner teams to design solutions for both new and existing customers. We introduced tokenization consent flows in checkout journeys and supported migration of legacy stored cards to tokens to ensure continuity of recurring mandates.

Through cross-team coordination and scalable integration, we ensured compliance with RBI regulations while preserving recurring setup for roughly 10–15 million customers, minimizing churn and maintaining business continuity."

---

If useful, I can also provide:

* 2 minute version
* STAR format version
* interviewer follow-up questions
* system design angle for this project
* diagram of architecture changes
