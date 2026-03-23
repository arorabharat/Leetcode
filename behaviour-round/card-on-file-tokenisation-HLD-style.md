# High-Level Design: Card-on-File Tokenization for Recurring Payments (RBI Compliance)

## 1. Problem Statement

Design a system to support **Card-on-File (CoF) tokenization** for a subscription product (e.g., Prime) in response to **RBI regulatory requirements** that prohibit merchants from storing raw card details.

The system must:

* ensure compliance with RBI guidelines
* support recurring payments using tokenized cards
* migrate existing stored cards to tokens
* minimize customer friction
* maintain business continuity for large user base
* operate reliably at high scale

Scale considerations:

* 10–15 million existing users with stored cards
* high TPS checkout surfaces
* millions of recurring transactions per month

---

## 2. Background: Why Tokenization was Required

Previously:

* merchants stored card details (PCI compliant vault)
* recurring payments charged using stored card credentials

RBI mandate:

* merchants cannot store PAN/CVV
* card networks issue tokens representing cards
* tokens are merchant-specific
* token creation requires customer consent (AFA authentication)

Without tokenization:

* recurring transactions fail
* subscriptions churn
* regulatory violation risk

---

## 3. Functional Requirements

### Core requirements

1. tokenize cards for new customers
2. migrate existing stored cards to tokens
3. enable recurring payments using tokens
4. ensure customer consent capture
5. maintain payment success rate
6. minimize checkout friction

---

### Recurring payment requirements

System must support:

* mandate creation
* token-based recurring charge
* retry logic for failures
* renewal lifecycle management

---

### Compliance requirements

* card data cannot be stored in merchant systems
* tokens must be provisioned via network token service
* consent must be explicitly captured
* audit trail must exist

---

## 4. Non Functional Requirements

### Scalability

* support millions of tokenization requests
* high TPS checkout flows
* handle peak events (sales, campaigns)

### Reliability

* token provisioning failures must not break checkout
* recurring payments must maintain high success rate

### Low latency

* tokenization should not significantly increase checkout latency

### Observability

* monitor token success rate
* monitor recurring payment success rate

---

## 5. High Level Architecture

### Key Components

1. Client (Web / App)
2. Checkout Service
3. Tokenization Service
4. Payment Orchestrator
5. Recurring Billing Service
6. Token Vault (Network provided)
7. Consent Management Service
8. Payment Gateway (BillDesk etc)
9. Card Network (Visa/Mastercard/RuPay)
10. Notification Service

---

## 6. Architecture Flow

### A. New Customer Tokenization Flow

Step 1: customer enters card details
Step 2: consent captured for tokenization
Step 3: token request sent to payment gateway
Step 4: gateway interacts with card network token service
Step 5: token returned to merchant
Step 6: merchant stores token reference
Step 7: recurring mandate created

---

### Sequence Diagram (simplified)

Client → Checkout Service
Checkout Service → Tokenization Service
Tokenization Service → Payment Gateway
Payment Gateway → Card Network Token Service

Card Network → Payment Gateway → Tokenization Service

Tokenization Service → Recurring Billing Service

Recurring Billing Service stores:

customer_id
token_reference
mandate_id

---

### B. Recurring Payment Flow

Scheduler triggers renewal

Recurring Service → Payment Orchestrator

Payment Orchestrator → Payment Gateway

Payment Gateway → Card Network

Card Network authorizes transaction

Response returned

status updated

---

### C. Existing Customer Migration Flow

Problem:
existing cards stored in legacy vault must be converted to tokens

Approach:

batch migration strategy

Steps:

1. identify customers with stored cards
2. request token creation via network
3. map existing card reference → token reference
4. update recurring billing records
5. notify customer if consent required

Fallback:
prompt tokenization during next checkout

---

## 7. Data Model (simplified)

### Token Mapping Table

customer_id
merchant_id
token_reference
card_network
expiry
status

---

### Mandate Table

mandate_id
customer_id
token_reference
frequency
amount
status

---

### Consent Table

customer_id
consent_timestamp
consent_version

---

## 8. Key Design Decisions

### Decision 1: token as primary payment reference

store token instead of card

benefits:

* regulatory compliance
* reduced PCI scope
* lower breach risk

---

### Decision 2: enforce tokenization during checkout

prevent invalid recurring setup

ensures:

all new subscriptions token compliant

---

### Decision 3: phased migration

support:

existing customers
new customers

reduces churn risk

---

### Decision 4: UI level enforcement

introduce:

token consent prompts

ensures:

high conversion rate

---

## 9. Failure Scenarios

### tokenization failure

fallback:
retry token request
allow alternate payment method

---

### recurring charge failure

retry strategy:

T+1 retry
customer notification
payment method update flow

---

### consent not provided

prevent recurring setup

allow one time purchase

---

## 10. Scaling Considerations

### horizontal scaling for token service

stateless services

load balancer distribution

---

### async processing for migration

batch jobs

queue based processing

Kafka / SQS

---

### caching token metadata

reduce gateway calls

improve latency

---

## 11. Monitoring Metrics

tokenization success rate

recurring success rate

mandate creation rate

checkout drop off rate

latency impact

migration coverage %

---

## 12. Tradeoffs

### friction vs compliance

more consent prompts increases friction

but required for regulation

---

### migration complexity vs customer experience

forcing re-entry of card simpler technically

but poor UX

migration preferred

---

## 13. Interview Summary (2–3 minute version)

"RBI mandated that merchants cannot store raw card details and must use network tokens instead. This posed risk for subscription businesses because recurring payments rely on stored credentials.

We designed a token-based payment architecture where card tokens replaced stored card details. We integrated tokenization into checkout flows to ensure all new customers were compliant and implemented migration strategies to convert existing stored cards to tokens.

The system included token provisioning via payment gateways, consent capture flows, mandate management, and recurring billing integration. We ensured high availability and minimal checkout latency while preserving recurring setup for 10–15 million customers.

This allowed us to maintain business continuity while meeting regulatory compliance."

---

If useful, I can also provide:

architecture diagram
sequence diagram
mock interview questions
deep dive on recurring mandate design
discussion of edge cases
comparison with UPI autopay design
