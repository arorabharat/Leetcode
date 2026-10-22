# Amazon work: project dossier

This folder gathers the Amazon-related work described in the repository into one Markdown file per project or distinct work story. The notes are based on existing resume and interview-preparation material, not independent verification of production systems. First-person wording reflects those source notes.

## Projects and work stories

### Prime retention, payments, and customer experience

1. [Prime stack-plan and auto-renew upsell](01-prime-stack-plan-auto-renew-upsell.md)
2. [Prime homepage bottom-sheet upsell](02-homepage-bottom-sheet-upsell.md)
3. [Me Tab upsell and reusable components](03-me-tab-upsell.md)
4. [Prime notification scheduler](04-prime-notification-scheduler.md)
5. [India and Worldwide Prime convergence](05-prime-worldwide-convergence.md)
6. [Prime retention-widget migration to Galileo](06-galileo-widget-migration.md)
7. [RBI card-on-file tokenization](07-card-on-file-tokenization.md)
8. [Standing Instruction to E-Mandate migration](08-si-to-e-mandate-migration.md)
9. [Prime signup payment race-condition fix](09-prime-payment-race-condition.md)
10. [Revised-payment recovery journey](10-revised-payment-recovery.md)
11. [Prime renewal payment-method update](11-renewal-payment-method-update.md)
12. [Prime homepage observability](12-prime-homepage-observability.md)
13. [On-call triage tool and AI assistant](13-oncall-triage-and-ai-assistant.md)
14. [Prime upgrade refunds and 180-day processor limit](14-prime-upgrade-refunds.md)
15. [UPI QR / CVV-less experience](15-upi-qr-cvv-less.md)

### Supply chain, infrastructure, and operational excellence

16. [Cross-region Fire-and-Forget service migration](16-fire-and-forget-region-migration.md)
17. [DoR vendor-dispute resolution](17-dor-vendor-dispute-resolution.md)
18. [PONYS: PO Not Yet Shipped](18-ponys-project.md)
19. [PO Centralization](19-po-centralization.md)
20. [PO-allocation algorithm shadow experiment](20-po-allocation-shadow-experiment.md)
21. [Peak-readiness automation](21-peak-readiness-automation.md)
22. [Dashboard-threshold automation](22-dashboard-threshold-automation.md)
23. [Configuration-management application recovery](23-configuration-management-recovery.md)
24. [Japan FBA Premium Inbound onboarding](24-japan-fba-onboarding.md)
25. [Speed-dating date-standardization data support](25-speed-dating-data-support.md)
26. [POService Sev-1 response](26-poservice-sev1-response.md)
27. [Diwali payment-method throttling RCA](27-diwali-payment-throttling-rca.md)
28. [Hiring, mentoring, and onboarding](28-team-growth-and-mentoring.md) — additional resume responsibility, not a standalone project.

## Reading and interview-use notes

- The detailed stories are drafts in an interview-preparation repository. Treat them as a starting point and verify your exact role, dates, scope, launch status, and metrics before using them externally.
- Some documents describe the same initiative from different interview angles. In particular, the renewal payment-method update appears in multiple stories; it is presented here once.
- There are conflicting figures in source notes. Card-tokenization scale is stated as 4–5M in the resume and 10–15M in the project/HLD write-ups. Observability and on-call time savings also vary by story. The relevant project pages call these out.
- Some files are architecture or interview-design drafts, rather than proof that every described component was shipped. Those pages distinguish documented implementation from proposed or illustrative design.
- The Fire-and-Forget migration story resembles the resume's Prime Tier-2 cross-region migration, but the source material does not conclusively establish that they are the same service.
- The older `resume/src/exp.json` lists OneNote projects under an Amazon employer entry. Its team and dates are inconsistent with `resume-v1/src/experience.tex`; those projects are deliberately not treated as confirmed Amazon work here.

