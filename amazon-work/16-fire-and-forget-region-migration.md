# Cross-region Fire-and-Forget service migration

## Summary

An interview story describes your ownership of a three-week cross-region migration for a business-critical asynchronous event-processing pipeline: SNS → SQS → Lambda → persistence.

## Migration challenge

The infrastructure was only partially managed through CDK; some resources had been created manually in the AWS console. Simply deploying a new regional stack risked configuration drift. The migration also needed to avoid message loss, limit latency impact, preserve operational visibility, and support rollback.

## Your documented contribution

- Compared declared CDK resources with actual resources, including Lambda configuration, SNS subscriptions, SQS settings, DLQs, IAM roles, event-source mappings, and CloudWatch alarms.
- Traced stack synthesis and cross-stack references to extend the deployment model rather than patching ad hoc.
- Deployed the target-region stack and ran both regions side by side before traffic movement.
- Recalibrated alarms for cross-region latency and validated region-specific IAM and service permissions.
- Split work into infrastructure, validation, alarms, and traffic-readiness tracks; established rollback checkpoints.

## Reported outcome

The story says migration finished within three weeks with no production incidents and zero message loss, while documenting unmanaged resources and clarifying the migration pattern for future use.

## Relationship to resume entry

The resume separately mentions a Prime Tier-2 cross-region migration. The source material does not explicitly confirm that the Fire-and-Forget service is that Tier-2 service. Keep the identities separate unless you can verify they are the same.

## Sources

- `behaviour-round/non-tech-skills/ManagingPressure.md`
- `resume-v1/src/experience.tex`

