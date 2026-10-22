# RBI card-on-file tokenization for Prime

## Summary

You were the Prime point of contact for adapting Prime recurring payments to India's card-on-file tokenization requirements. The goal was to use network tokens rather than merchant-stored raw card details while preserving recurring-payment continuity.

## Customer/business risk

Prime depends on stored payment credentials for renewals. The notes describe risk of recurring charge failures, involuntary churn, and customer friction if existing payment details could not continue to support mandates. New customers also needed a compliant tokenization/consent journey.

## Your documented contribution

- Coordinated Prime integration with Amazon Payments and external/partner teams including BillDesk.
- Helped evaluate payment-platform tokenization capabilities against Prime recurring requirements.
- Supported redesign of the Prime plan-buying journey for new customers, including tokenization consent.
- Worked on migration of existing customer payment credentials to tokenized references while preserving mandate continuity.
- Coordinated across checkout, payments, subscription, and partner teams to manage regulatory ambiguity and rollout risks.

## Architecture notes versus confirmed delivery

The separate HLD draft models client/checkout, tokenization service, payment orchestrator, recurring billing, consent, gateway/network, and token-reference/mandate data. It also discusses batch migration, fallback to tokenization at next checkout, retries, monitoring, and failure cases. Treat these as design material unless you confirm they match the deployed design; some are generic architecture proposals.

## Reported outcome and metric conflict

The resume says 4–5M existing customers were migrated. The project summary and HLD say 10–15M customers. Do not present one number as settled until you reconcile the scope, date, and whether it means eligible, targeted, or successfully migrated customers. The write-up claims business continuity and RBI compliance but does not provide a migration completion rate.

## Sources

- `resume-v1/src/experience.tex`
- `behaviour-round/card-on-file-tokenisation.md`
- `behaviour-round/card-on-file-tokenisation-HLD-style.md`
- `behaviour-round/amazon-office-work/intro.md` (role context mentions tokenized Prime purchases and RBI/NPCI compliance)
- `behaviour-round/non-tech-skills/Summary.md` (story-bank references tokenization; no additional implementation details)
