# Atlassian tagging service design

Archived chat transcript. Message text is preserved as stored in the session.

Session: `cf1ccdba-a6e9-4719-81ab-ef871650e0b9`

## Turn 7 (2026-10-12T13:31:32.54Z)

### User

Atlassian has multiple products like Jira, Confluence, and Bitbucket. We want to build a centralized, unified Tagging Service that all Atlassian products can use. Design a scalable tagging system that allows users to tag any entity (Jira issues, Confluence pages, pull requests), supports both simple and hierarchical tags, tracks metadata, and allows for lightning-fast search.

Help me prepare this interview quesiton. Help me first  identiofy the problems and sub problems before jumping into solution. Assume I am in brain storming mode and you and me are having discussion to first identify  the problems which need to be solved, why interviewer would ask such quesiotn and what is the intent of asking such question , what does interviewer is trying to judge , what are the larning from this proeblem statement

### Assistant

# Unified Tagging Service: Brainstorm (Problem Discovery Phase)

## 1. Why would an interviewer ask this?

**What it looks like:** a "simple" CRUD feature (labels on things).
**What it really is:** a **platform/shared-service design** question hiding several hard problems.

**What they're judging**
| Signal | How it shows up |
|---|---|
| **Requirement clarification** | Do you ask before designing, or jump to "Postgres + Elasticsearch"? |
| **Platform thinking** | Multi-product, multi-tenant, no coupling to Jira/Confluence internals |
| **Data modeling** | Entity-agnostic tagging, hierarchy, metadata |
| **Read vs write tradeoffs** | Tagging is write-light, search is read-heavy |
| **Consistency vs latency** | Entity deleted → tags? Tag renamed → search index? |
| **Security awareness** | Tags leaking existence of private issues (Atlassian-critical) |
| **Scale reasoning** | Numbers, hot keys, big tenants |
| **Tradeoff communication** | Defending choices, not reciting the "right" answer |

**Hidden intent:** can you see that the *hard part is not tagging, it's search + permissions + decoupling across products*?

## 2. Problem decomposition

### A. Clarify the scope (do this first in the interview)
- Is a tag **global**, **per-tenant (site/org)**, or **per-project/space**?
- Are tags **free-form** (user creates on the fly) or **governed** (admin-managed vocabulary)?
- Does the service **own** entities? (No, only references: `product + entityType + entityId`.)
- What queries matter? "All items with tag X", "X AND Y", "under subtree Engineering/Backend", cross-product?
- Scale: tenants, entities, tags per entity, QPS read vs write, latency SLA.

### B. Core sub-problems

1. **Entity-agnostic identity**
   How do you reference a Jira issue, Confluence page, and PR uniformly? (Composite key: tenant + product + type + id.) Entity IDs aren't globally unique and the service can't trust them blindly.

2. **Tag modeling: flat vs hierarchical**
   - Tag identity: by name or by ID? (Rename problem.)
   - Hierarchy: `Engineering/Backend/Payments`. Path string vs parent pointer vs closure table vs materialized path.
   - Does tagging `Backend` imply matching a search for `Engineering`? (Subtree query semantics.)
   - Move/rename a subtree: how expensive?
   - Cycles, depth limits, multi-parent (DAG) or tree only?
   - Normalization: case, whitespace, unicode, duplicates, synonyms.

3. **Metadata**
   - Per tag: color, description, creator, created time, owner, usage count.
   - Per tag-assignment: who tagged, when, source (user/automation).
   - Audit/history: needed? Soft deletes?
   - Extensible schema vs fixed columns?

4. **Search ("lightning-fast")**
   - Query types: by tag, AND/OR/NOT, subtree, prefix autocomplete, combined with other filters (JQL / CQL).
   - Does search live in *our* service or do Jira/Confluence search need tags inside their own indexes? (Big architectural fork.)
   - Index choice, denormalization, and **index freshness** (eventual consistency window).
   - Autocomplete/typeahead is a separate latency-critical path.

5. **Permissions & security** *(the Atlassian-specific trap)*
   - User must only see results for entities they can access. Who enforces it? We can't duplicate every product's permission model.
   - Options: callback to product (slow), permission snapshot in index (stale), post-filtering (pagination problems).
   - Tag names themselves can leak info.

6. **Consistency across systems**
   - Entity deleted/moved/archived in Jira → orphan tags. Needs event consumption.
   - Tag renamed/merged/deleted → fan-out to potentially millions of assignments + index updates.
   - Dual writes (DB and search index) → failure modes.

7. **Multi-tenancy & scale**
   - Tenant isolation, noisy neighbors, hot tags ("bug" on millions of items), huge tenants, sharding key.
   - Data residency (Atlassian cares).

8. **Integration & API design**
   - Sync API vs events; SDK for products; backward compatibility; rate limiting; bulk tagging; idempotency.
   - Migrating existing Jira labels / Confluence labels into the system.

9. **Governance & abuse**
   - Tag sprawl, duplicates, limits per entity, who can create/merge/delete tags.

10. **Non-functionals**
    - Availability (must not break Jira if tagging is down: graceful degradation), observability, GDPR deletion, cost.

## 3. Key tensions to surface (these differentiate strong candidates)

| Tension | Question to discuss |
|---|---|
| Central vs embedded search | Own the index vs feed product indexes |
| ID vs name for tags | Rename-safety vs simplicity |
| Hierarchy at write vs read time | Materialized path vs recursive query |
| Strong vs eventual consistency | Is a 2s stale search acceptable? |
| Permission accuracy vs speed | Filter at query time or index time |
| Generic vs product-specific | Flexibility vs losing product features |

## 4. Suggested interview flow
1. Clarify requirements and scope (3–5 min)
2. State assumptions and rough scale (2 min)
3. Core data model and APIs (10 min)
4. High-level architecture (write path, read/search path, event path)
5. Deep dive on 1–2 hard areas (interviewer chooses; likely search or hierarchy or permissions)
6. Failure modes, tradeoffs, evolution

## 5. Learnings from this problem
- **Reference, don't own**: shared services should store pointers, not copies of domain data.
- **CQRS pattern**: separate write store from search-optimized read model.
- **Hierarchy storage patterns**: materialized path, closure table, nested set.
- **Event-driven integration** to keep derived data consistent.
- **Authorization in search** is the classic hard problem for multi-product platforms.
- **Multi-tenancy** shapes every decision (sharding, isolation, quotas).
- Ability to **name tradeoffs** matters more than the final design.

---

**Let's discuss next.** A few questions to steer our brainstorm; which do you want to tackle first?
1. Hierarchy modeling (materialized path vs closure table)
2. Search architecture and the permissions problem
3. Consistency/event flow between products and the tag service
4. Back-of-envelope scale estimation

## Turn 41 (2026-10-12T13:41:50.007Z)

### User

Okay, let's focus on one of the problem that is uh the fan out problem uh assume uh you are renaming a tag so there are basic RUD operations creating a tag or reading a tag deleting a tag and uh reading attack so let's focus on uh updating the tag uh what if I rename the tag how will uh all the existing documents will get the new name

### Assistant

# Tag Rename: The Fan-Out Problem

## 1. Reframe: is it even a fan-out problem?

The key insight is that **fan-out only exists if you denormalized the name**. So the first question is where the tag name lives.

| Layer | Stores name? | Rename cost |
|---|---|---|
| **Source of truth (tag table)** | Once, keyed by `tagId` | 1 row update (O(1)) |
| **Assignments (entity ↔ tag)** | Only `tagId` | 0 updates |
| **Search index (denormalized docs)** | Often name/path, for fast filtering | **O(N) docs**, the real fan-out |
| **Product-side caches / embedded copies** | Possibly name | Staleness problem |

**Principle 1: tag identity = immutable ID; name is just an attribute.** This is the answer to "ID vs name" from before. With IDs, rename is cheap in the DB and the pain moves to the read model.

## 2. Where fan-out actually hurts

1. **Search index:** a tag like `bug` on 5M documents means 5M reindex operations. Elasticsearch updates are really delete + reinsert of the whole doc.
2. **Hierarchical tags:** renaming `Engineering` changes the path of every descendant (`Engineering/Backend/Payments`). That's fan-out on tags *and* on the entities tagged with any descendant. It's multiplicative.
3. **Display on product UIs:** Jira issue view shows tag names. Does it call us, or hold a copy?
4. **Hot tags and big tenants:** one rename can saturate the indexing pipeline and starve other tenants.
5. **Consistency window:** during the rename, some docs show old and some new. Is search by the old name still valid?

## 3. Sub-problems to solve

- **Avoid fan-out:** can we design the index so rename doesn't need to touch docs?
- **If unavoidable, make it async, throttled, resumable, and idempotent.**
- **Partial-failure handling:** the job dies at 60%. Resume and reconcile.
- **Concurrent writes during the rename:** new tagging events arrive mid-migration. Which name do they get?
- **Concurrent renames:** A→B then B→C while the first is still propagating (ordering, versioning).
- **Name collisions:** renaming to a name that already exists (merge vs reject).
- **Backpressure and fairness:** per-tenant quotas on reindex throughput.
- **Observability:** progress, lag, "rename complete" signal.
- **Rollback/undo.**

## 4. Design directions (to discuss, not decide yet)

**Option A: Index only IDs (no fan-out).**
Search docs store `tagIds`. A query for the name resolves name→ID through a small tag dictionary, then searches by ID. Rename = update dictionary only.
- Pros: O(1) rename, which is the cleanest answer.
- Cons: a query-time lookup (cacheable, tiny), and hierarchy queries need subtree→IDs expansion.
- For hierarchy: store ancestor IDs per assignment, or expand the subtree to a set of IDs at query time. Since IDs are stable, **rename and even move don't require reindexing** (a move still does, if ancestors are stored per doc).

**Option B: Denormalize the name in the index, with async propagation.**
Event `TagRenamed(tagId, v2)` → worker batches → bulk update by query. Needs versioning, throttling, and checkpointing.
- Pros: self-contained docs, no lookup.
- Cons: a stale window and heavy reindexing.

**Option C: Hybrid.** Index IDs for filtering, and resolve names at display time via a cached tag dictionary (with version or ETag invalidation).

The likely strong answer is **A/C**: filter by ID, render by lookup. Fan-out is removed instead of optimized.

## 5. Trade-offs to weigh

- **Query-time resolution vs index-time denormalization:** one extra cached lookup vs millions of writes.
- **Stale name in the cache:** a TTL or pub/sub invalidation is cheap compared to reindexing.
- **Does anyone need to search by the *old* name?** Aliases or redirects (keep the history of names) are a product decision.
- **Free-text relevance:** if the tag name must be part of full-text relevance in Jira/Confluence search, denormalization pressure returns.

## 6. What the interviewer is testing here
- Do you notice that **the fan-out is a consequence of a design choice** and not a law of nature?
- ID immutability, normalization vs denormalization.
- If fan-out is needed: idempotent async jobs, throttling, versioning, resumability.
- Hierarchy rename and move as the "gotcha" follow-up.

## 7. Questions back to you
1. Do you want to push on **Option A** and work out how hierarchy queries work with IDs only?
2. Or assume the interviewer says "the product indexes must contain the tag name" and design the **async propagation pipeline** (Option B)?
3. Or discuss **tag merge/delete**, which is the same fan-out problem but harder because assignments themselves change?

## Turn 76 (2026-10-12T13:49:36.155Z)

### User

What is the recommended solution? Uh, let's say if you're not storing the names gimme end to end query pattern for tag renaming

### Assistant

# Recommended: ID-based indexing, names resolved from a tag dictionary

**Rule:** assignments and search docs store only `tagId` (and ancestor IDs). Names live in exactly one place, so rename is O(1).

## Data model (minimal)

```
tag(tenant_id, tag_id PK, parent_id, path_ids[], name, name_normalized,
    version, updated_by, updated_at, deleted_at)
UNIQUE (tenant_id, parent_id, name_normalized)

assignment(tenant_id, product, entity_type, entity_id, tag_id, tagged_by, tagged_at)

search doc: { entity_ref, tag_ids: [...], ancestor_tag_ids: [...] }   // no names
```

## End-to-end: rename `Backend` → `Services`

**1. Write path (the only thing that changes)**
```
PATCH /tenants/{t}/tags/{tagId}   If-Match: version=7
{ "name": "Services" }
```
1. AuthZ: caller may manage this tag.
2. Normalize the name (trim, NFKC, lowercase) → `services`.
3. In one transaction:
   ```sql
   UPDATE tag
   SET name='Services', name_normalized='services', version=version+1, updated_at=now()
   WHERE tenant_id=? AND tag_id=? AND version=7;     -- optimistic lock
   ```
   - 0 rows updated → 409 (concurrent rename).
   - Unique violation on `(tenant, parent, name_normalized)` → 409 name collision (offer merge as a separate operation).
   - Write an audit row and an outbox row `TagRenamed(tagId, oldName, newName, v8)` in the same transaction.
4. Return 200. **Nothing else is touched**: no assignments, no search docs, no descendants.

Hierarchy bonus: because paths are stored as IDs (`[eng_id, backend_id, payments_id]`), descendants need no update either. Display paths are built at read time.

**2. Propagation (cache only, tiny)**
Outbox relay → event bus → `TagRenamed` → invalidate or overwrite the entry in the tag dictionary caches (Redis plus in-process, keyed `tenant:tagId`, versioned). This is a single key per cache, not millions of docs.

**3. Read path: search by name**
```
GET /search?tag=Services   (or tag path "Engineering/Services")
```
1. Resolve name → ID via the dictionary: `(tenant, parent, name_normalized) → tagId` (cached, ~1ms).
2. Subtree queries: `ancestor_tag_ids: tagId` (or a terms filter on the expanded ID set).
3. Query the index with the ID filter, then apply the permission filter and paginate.
4. Hydrate: collect the distinct `tagId`s in the page (typically a few dozen), then do a batched `MGET` from the cache, falling back to DB on a miss. Render the current names.

Result: the new name is visible on the next request after the cache update, with no reindexing.

**4. Read path: display tags on an entity**
Product calls `GET /entities/{ref}/tags` → assignments (IDs) → batch resolve names → response. Products that cache names should store `tagId + version` and refresh on the `TagRenamed` event or ETag.

## Edge cases to mention
- **Old-name searches:** keep a `tag_name_history` / alias table if you want old links to keep working; otherwise they 404.
- **Rename vs move:** a move (change of `parent_id`) rewrites `path_ids` for the subtree (O(subtree tags)) and `ancestor_tag_ids` in docs that carry them. That is the one real fan-out left, so handle it async, batched, and versioned. Rename never needs it.
- **Cache stampede:** use versioned keys with a short TTL as a safety net, with pub/sub invalidation as the fast path.
- **Eventual consistency:** the cache window is seconds, and it can be bounded by reading the DB with `version >= event.version` on a hot miss.
- **Free-text relevance** is the one case that reintroduces denormalization. Then use the async pipeline from Option B only for that field.

## One-liner for the interview
> "Tag identity is an immutable ID. Names are a single-row attribute resolved through a cached dictionary at query and display time, so rename is one transactional update plus a cache invalidation, with no fan-out to assignments or the search index."

Next, shall we go deeper into **tag move** (the remaining real fan-out) or **merge/delete**?

## Turn 111 (2026-10-12T13:51:20.061Z)

### User

Okay, before we jump into the tags, uh, let's discuss how tags are getting created, what operations or what things are getting done when tag is created, uh, then how tag is assigned I think these are the two primary user journeys we should understand so start with the tag creation

### Assistant

# Journey 1: Tag Creation

## 1. Who creates tags, and how? (clarify first)

| Mode | Example | Implication |
|---|---|---|
| **Explicit** | Admin creates `Engineering/Backend` in a tag manager | Governed, can set metadata up front |
| **Implicit (create-on-assign)** | User types `#payments` on a Jira issue and it doesn't exist | Hot path, high volume, race-prone |
| **Programmatic** | Automation rules, Bitbucket bots, migration of Jira labels | Bulk, idempotent, needs rate limits |

Also decide the **scope**: tenant-wide, or project/space-scoped. Scope is part of the uniqueness key.

## 2. What happens on create (step by step)

```
POST /tenants/{t}/tags
Idempotency-Key: abc123
{ "name": "Payments", "parentId": "tag_backend", "color": "blue", "description": "..." }
```

1. **AuthN/AuthZ:** can this caller create tags in this scope? Governed tenants may restrict it to admins; free-form tenants allow any member.
2. **Validate input:** length limit, allowed characters, reserved words, and a hierarchy path separator rule (`/` not allowed inside a name).
3. **Normalize:** trim, Unicode NFKC, case-fold → `name_normalized = "payments"`. Keep the original `name` for display.
4. **Resolve parent:** it must exist, be in the same tenant, and not be deleted. Enforce **max depth** (e.g. 5) and, for a tree, no cycles (trivial on create, matters on move).
5. **Check quotas:** max tags per tenant, max children per parent (anti-sprawl).
6. **Generate ID:** ULID/UUID (immutable, tenant-agnostic, sortable).
7. **Build path:** `path_ids = parent.path_ids + [newId]`, `depth = parent.depth + 1`.
8. **Insert in one transaction:**
   - the `tag` row with `UNIQUE(tenant_id, parent_id, name_normalized)`
   - an audit row
   - an outbox row `TagCreated`
9. **Handle the duplicate:** a unique violation means the tag already exists.
   - Explicit create → **409**, return the existing `tagId`.
   - Implicit create-on-assign → **get-or-create**: return the existing tag (idempotent).
10. **Respond 201** with `tagId`, `version=1`.
11. **Async (via outbox):**
    - warm the dictionary cache (`tenant:parent:normalized → tagId`)
    - feed the autocomplete index
    - emit analytics/audit events

Notice what's *not* done: no search-doc writes, because nothing is tagged yet.

## 3. Sub-problems this surfaces

1. **Race on duplicate create:** two users create `payments` at the same instant. The DB unique constraint is the arbiter, not an app-level check-then-insert.
2. **Near-duplicate sprawl:** `Payment`, `payments`, `Payments `. Normalization handles case and whitespace, but not synonyms or typos. Options: suggest existing tags on type-ahead (UX prevention), later a merge tool.
3. **Root uniqueness with NULL parent:** a unique index treats NULLs as distinct in many DBs, so use a sentinel root ID.
4. **Hierarchy create shortcuts:** `POST name="Engineering/Backend/Payments"` should auto-create missing ancestors (idempotent, multi-row transaction).
5. **Scope and visibility:** a tag created in a private project, does its name leak in tenant-wide autocomplete? Scope the dictionary accordingly.
6. **Idempotency for retries:** `Idempotency-Key` or natural-key upsert.
7. **Bulk import/migration:** batching, partial failure, rate limits (existing Jira labels, Confluence labels).
8. **Soft-deleted name reuse:** can you recreate a name that was deleted? The unique index should be partial (`WHERE deleted_at IS NULL`), or you restore the old tag.
9. **Sharding:** key by `tenant_id` so the uniqueness check stays within a single shard.

## 4. Metadata captured at creation
- Tag: `createdBy`, `createdAt`, `color`, `description`, `scope`, `ownerTeam`, `status` (active / deprecated).
- Optional extensible attributes (a JSON column) with a size limit.

## 5. What the interviewer is judging here
- DB constraints for correctness, not app-level checks.
- Get-or-create idempotency in the implicit flow.
- Normalization vs display name.
- Hierarchy invariants (depth, cycles, uniqueness per parent).
- Governance vs flexibility trade-off.

## 6. One-liner
> "Creation is a validated, normalized, idempotent insert guarded by a unique constraint on (tenant, parent, normalized name), with an outbox event to warm caches. Nothing touches the search index until the tag is actually assigned."

Next is **Journey 2: tag assignment**, where the real complexity lives (create-on-assign, dual writes to the index, permissions, limits). Shall I start?

## Turn 145 (2026-10-12T13:52:06.108Z)

### User

What exactly is the tenant

### Assistant

# What is a tenant?

A **tenant** is the top-level customer boundary: one organization's isolated slice of a shared multi-customer system. All their data is logically separated from every other customer's, even though it runs on the same infrastructure.

## In Atlassian terms
- A customer like **Acme Corp** has a **site** (`acme.atlassian.net`) with Jira, Confluence, and Bitbucket.
- Atlassian's cloud identifier for this is the **cloudId** (site-level), under an **organization** (orgId) for admin and billing.
- So **tenant ≈ cloudId (site)**. Acme's tags are never visible to Globex.

## Hierarchy of scope
```
Organization (orgId)
 └─ Site / Tenant (cloudId)       ← isolation boundary
     ├─ Product: Jira
     │   └─ Project (PAY)         ← optional narrower scope
     ├─ Product: Confluence
     │   └─ Space (ENG)
     └─ Product: Bitbucket
         └─ Workspace/Repo
```

## Why it matters in this design
- **Uniqueness:** `payments` is unique *within* a tenant, not globally. Two tenants can both have it.
- **Sharding key:** `tenant_id` keeps a tenant's tags, assignments, and uniqueness checks on one shard.
- **Security:** every query is filtered by `tenant_id`, so a bug can't leak across customers.
- **Noisy neighbors:** quotas and rate limits are per tenant.
- **Data residency and deletion:** a tenant can be pinned to a region, and offboarding or GDPR deletion is a tenant-wide purge.

## Interview tip
Say it explicitly: "I'll treat tenant as the Atlassian site (cloudId), and every table key and index starts with it." Then ask whether tags should be shared across products within the tenant (yes, that is the point of a unified service) and whether narrower scopes like project or space are needed.

Ready to move on to **Journey 2: tag assignment**?

## Turn 179 (2026-10-12T14:00:35.488Z)

### User

Do tag need to be even unique under a tenant? Can it not be unique under a namespace

### Assistant

# Uniqueness: tenant-wide vs namespace-scoped

Good challenge. Tenant-wide uniqueness is just the default I assumed. **Uniqueness should be per namespace, and tenant is simply the outermost namespace.**

## Generalized model
```
tag(tenant_id, namespace_id, tag_id, parent_id, name_normalized, ...)
UNIQUE (tenant_id, namespace_id, parent_id, name_normalized)
```
A **namespace** is a scope that owns a vocabulary, for example:
- `tenant` (global, shared across all products)
- `product` (Jira only)
- `project/space/repo` (Jira project `PAY`, Confluence space `ENG`)
- `team` or `user` (personal tags)

Hierarchy already gives you partial namespacing: uniqueness is per parent, so `Backend/Payments` and `Billing/Payments` coexist.

## Why namespaces are better
| Reason | Example |
|---|---|
| **Same word, different meaning** | `release` in Jira project A vs B |
| **Autonomy** | A team manages its own vocabulary without admin approval |
| **Security** | Private project tag names don't leak into tenant-wide autocomplete |
| **Less sprawl pressure** | No fight over a global `bug` |
| **Smaller blast radius** | Rename, merge, delete only affect one namespace |
| **Sharding and quotas** | Per-namespace limits |

## Costs
- **Cross-namespace search:** "all items tagged `payments`" is now ambiguous. Search must be by `tagId` (which we already do), or by name across namespaces, which returns multiple IDs.
- **Same concept fragmented:** `payments` in 10 projects = 10 tags. Reporting on them needs a mapping or promotion to a shared tag.
- **Resolution rules:** when a user types `payments`, which namespace? Use a lookup order (project → product → tenant) or make the namespace explicit in the API.
- **Moving entities** between namespaces (a Jira issue moves projects): its tags must be re-mapped or become foreign references.
- **UX:** the user must understand scope.

## Recommendation
Make `namespace` a first-class field in the key, and support two common modes:
1. **Shared/global namespace** (tenant-level, governed): cross-product reporting.
2. **Local namespaces** (project/space): free-form and team-owned.

Entities can carry tags from both. Tag identity is still the immutable `tagId`, so nothing in the rename or search design changes.

## Interview framing
> "Uniqueness is relative to a namespace. I'll make the namespace part of the unique key, with tenant as the root, and ask whether the product needs cross-namespace tags. That's a product decision I'd confirm before designing."

Want to continue to **Journey 2: tag assignment**, now including namespace resolution?

## Turn 214 (2026-10-12T14:08:36.807Z)

### User

What do you recommend for a forty five minutes interview

### Assistant

# Recommendation for a 45-minute interview

**Keep it simple: tenant-scoped, with an optional namespace field.** Don't build the full namespace system.

## What to say
> "I'll scope tags to a tenant (site) and make uniqueness `(tenant, parent, normalized name)`. I'll include `namespace_id` in the key, defaulting to the tenant root, so project-level vocabularies can be added later without a schema change. Search is always by immutable `tagId`, so the choice doesn't affect the rest of the design."

That takes about 30 seconds, shows you saw the trade-off, and avoids a 10-minute rabbit hole.

## Why
- 45 minutes is tight. Interviewers reward a clear end-to-end design plus 1-2 deep dives, not breadth of options.
- Namespaces add UX and cross-namespace search complications that don't pay off here.
- Naming the extension point demonstrates foresight.

## Suggested time budget
| Min | Activity |
|---|---|
| 0-5 | Clarify requirements, scope, scale, assumptions (tenant = site, tags free-form, hierarchy is a tree) |
| 5-10 | Entities and APIs: tag CRUD, assign/unassign, search |
| 10-20 | High-level architecture: tag DB, assignment store, outbox/events, search index, cache |
| 20-35 | **Deep dive** (let the interviewer pick, or you propose): rename via ID-based indexing, hierarchy storage, or search with permissions |
| 35-42 | Failure modes: dual-write, orphan cleanup on entity delete, hot tags, multi-tenancy |
| 42-45 | Summarize trade-offs and future extensions (namespaces, merge/move, aliases) |

## Priorities
1. **Must cover:** ID-based tags, assignment model (`tenant, product, entityType, entityId, tagId`), search index fed by events, permissions in search, rename without fan-out.
2. **Mention briefly:** hierarchy (materialized path of IDs), idempotency, normalization, quotas.
3. **Park as follow-ups:** namespaces, merge, move, aliases, data residency.

Next, shall we do **Journey 2: tag assignment** in this 45-minute-sized scope?

## Turn 249 (2026-10-12T14:09:21.4Z)

### User

Okay, let's build it uh with the uniqueness at tenant level. Now tell me how to create a how to create a tag and how to assign it

### Assistant

# Create + Assign (tenant-level uniqueness)

## Schema

```
tag(tenant_id, tag_id PK, parent_id, path_ids[], depth, name, name_normalized,
    color, description, status, version, created_by, created_at, deleted_at)
UNIQUE (tenant_id, parent_id, name_normalized) WHERE deleted_at IS NULL
  -- root parent_id = sentinel 'ROOT'

assignment(tenant_id, entity_key, tag_id, tagged_by, tagged_at)
  PK (tenant_id, entity_key, tag_id)
  INDEX (tenant_id, tag_id, entity_key)     -- reverse lookup, cleanup, counts
  entity_key = product:entityType:entityId  (e.g. jira:issue:PAY-123)

outbox(id, tenant_id, type, payload, created_at)
```
Shard everything by `tenant_id`.

## 1. Create tag

```
POST /v1/tenants/{t}/tags   Idempotency-Key: k1
{ "name": "Payments", "parentId": "tag_backend", "color": "blue" }
```
1. AuthZ: caller can create tags in the tenant.
2. Validate (length, chars, no `/`) and normalize (trim, NFKC, lowercase).
3. Load parent: same tenant, active; check depth ≤ 5 and quotas.
4. Generate ULID `tagId`; `path_ids = parent.path_ids + [tagId]`.
5. One transaction: insert tag, audit row, outbox `TagCreated`.
6. Unique violation → explicit create returns 409 with the existing `tagId`. Get-or-create (used by assign) returns the existing tag.
7. Return 201 `{tagId, version:1}`.
8. Async: the outbox relay warms the dictionary cache and autocomplete.

No search index write yet.

## 2. Assign tag

```
PUT /v1/tenants/{t}/entities/jira:issue:PAY-123/tags/{tagId}
(or POST .../tags {"name":"Payments"} for create-on-assign)
```
1. **AuthN/AuthZ:** the caller must be able to edit that entity. The tag service doesn't know product permissions, so the product (Jira) calls us with a trusted service token plus the user context, or Jira performs the check and calls us. Confirm the entity exists through the product gateway (or trust the caller).
2. **Resolve tag:** by `tagId`, or by name through the dictionary (get-or-create, per the create flow). Reject deleted or deprecated tags.
3. **Check limits:** e.g. ≤ 50 tags per entity.
4. **Transaction:**
   ```sql
   INSERT INTO assignment (...) ON CONFLICT DO NOTHING;   -- idempotent
   ```
   If a row was inserted: bump the `tag_usage` counter (async or approximate), write an outbox row `TagAssigned(entity_key, tagId, ancestorIds, v)`.
5. Return 200/204. A repeat call is a no-op.
6. **Async indexing:** the relay publishes to Kafka, partitioned by `tenant_id:entity_key` for per-entity ordering. The indexer consumer upserts the search doc:
   ```
   { entity_key, tag_ids:[...], ancestor_tag_ids:[...] }
   ```
   with `external_version` (the assignment event version or timestamp), so stale or out-of-order events are dropped.
7. Search sees the tag within about 1-2 s (eventual consistency). Reads of `GET /entities/{key}/tags` come from the assignment table, so they are strongly consistent.

Unassign is the mirror: delete the assignment row and emit `TagUnassigned`.

## Why this shape
- **Outbox** avoids dual-write inconsistency between the DB and the index.
- **Natural-key upsert** gives idempotency under retries.
- **Entity-ordered partitions** avoid assign/unassign reordering.
- **Index IDs only**, so rename stays O(1).
- **Reconciliation job** (periodic DB-to-index diff) repairs any lost events.

## Interview one-liner
> "Create is a normalized insert guarded by a unique constraint. Assign is an idempotent insert plus an outbox event, and an ordered, versioned indexer builds the search doc asynchronously."

Next: **search with permissions**, or **entity delete/orphan cleanup**?

## Turn 283 (2026-10-12T14:11:06.59Z)

### User

I did not understand the tag model go step by step and explain me briefly so that I can understand so I want to understand the tag session. There are so many attributes which you have taken I don't have any idea what these attributes are for so maybe start with a simple explanation of the tag model maybe just use a brief plain English that would also be fine

### Assistant

# The Tag Model in Plain English

## Think of two things
1. **A tag**: a label that exists in the system, like "Payments".
2. **An assignment**: a link saying "this Jira issue has the label Payments".

Two tables: one for labels, one for links.

## Table 1: `tag` (the label itself)

**Start with the 4 essentials:**
| Field | Plain meaning |
|---|---|
| `tenant_id` | Which customer owns this tag (Acme vs Globex) |
| `tag_id` | Permanent unique ID. Never changes, even if renamed |
| `name` | What users see: "Payments" |
| `parent_id` | The tag above it in the hierarchy (`Backend` is the parent of `Payments`). Empty for top-level |

That's enough for a working hierarchical tag.

**Supporting fields (each solves one specific problem):**
| Field | Why it exists |
|---|---|
| `name_normalized` | Lowercased/trimmed copy of the name, so "Payments" and "payments " count as duplicates |
| `path_ids` | List of ancestor IDs, e.g. `[Eng, Backend, Payments]`. Lets us find "everything under Engineering" quickly |
| `depth` | How deep in the tree (to enforce a max, like 5 levels) |
| `version` | Counter that increases on every edit. Prevents two people editing at once from overwriting each other |
| `status` | Active or deprecated (hidden from new use) |
| `deleted_at` | Soft delete: marks removed without erasing, so we can undo or audit |
| `color`, `description` | Display metadata |
| `created_by`, `created_at` | Who made it and when (audit) |

**The one rule:** no two tags with the same normalized name under the same parent in a tenant. That's the `UNIQUE` constraint.

## Table 2: `assignment` (the link)

| Field | Plain meaning |
|---|---|
| `tenant_id` | Which customer |
| `entity_key` | What is tagged, written as `jira:issue:PAY-123` (product + type + id) |
| `tag_id` | Which tag is applied |
| `tagged_by`, `tagged_at` | Who tagged it and when |

The key is (tenant, entity, tag), so you can't tag the same thing twice with the same tag.

The assignment stores `tag_id`, **not the name**. That's why renaming costs one row update.

## Example
```
tag:  id=T1  name=Engineering  parent=none
      id=T2  name=Backend      parent=T1   path=[T1,T2]
      id=T3  name=Payments     parent=T2   path=[T1,T2,T3]

assignment: jira:issue:PAY-123 → T3
```
Issue PAY-123 is tagged `Engineering/Backend/Payments`. Searching "Engineering" finds it because T1 is in T3's path.

## The outbox table (ignore for now)
It's only a reliable "to-do list" for telling the search index about changes. It's a plumbing detail, not part of the tag model.

Does this make sense? If so, I can walk through create and assign using only the 4 essential fields first, then layer the rest on.

## Turn 317 (2026-10-12T14:12:40.371Z)

### User

Okay given now we are creating a hierarchical tags. So first explain me how to create hierarchical tag before even jumping into the assignments. So does the tag need to be unique under a hierarchy

### Assistant

# Creating Hierarchical Tags

## 1. Does a tag need to be unique under a hierarchy?

**Unique among siblings, not globally.** The rule: no two tags with the same normalized name under the same parent (within a tenant).

```
Engineering
 ├─ Backend
 │   └─ Payments     ✅
 └─ Frontend
     └─ Payments     ✅ allowed (different parent)
Engineering
 └─ Backend
     └─ payments     ❌ duplicate of Payments (same parent, same normalized name)
```

**Why siblings, not tenant-wide?**
- Names only have meaning in context: `Backend/Payments` and `Billing/Payments` are different concepts.
- It's how file systems work (same filename in different folders).
- Forcing global uniqueness would make users invent names like `Backend-Payments`, which defeats hierarchy.

**What identifies a tag, then?**
- Internally: the immutable `tag_id`.
- For humans: the **full path** (`Engineering/Backend/Payments`), which is unique.

**Two small catches**
- Top-level tags have no parent, so use a sentinel `ROOT` parent. Otherwise many databases treat NULLs as distinct and allow duplicates.
- Names can't contain `/`, since that's the path separator.

## 2. How to create a hierarchical tag

**Case A: create under an existing parent**
```
POST /tags  { "name": "Payments", "parentId": "T2" }
```
1. Check the caller's permission.
2. Normalize the name (`payments`) and validate it.
3. Load parent T2: it must exist, be active, and be in the same tenant.
4. Check depth: `parent.depth + 1 ≤ max` (e.g. 5).
5. Generate a new `tag_id` (T3).
6. Compute `path_ids = T2.path_ids + [T3]` → `[T1, T2, T3]`, `depth = 3`.
7. Insert. The DB unique constraint on `(tenant, parent_id, name_normalized)` rejects sibling duplicates.
8. Return 201.

Creating a child never changes the parent or any other row.

**Case B: create by path (convenience)**
```
POST /tags  { "path": "Engineering/Backend/Payments" }
```
Split on `/`, then walk from the top: for each segment, get-or-create under the previous node. Do it in one transaction. Existing segments are reused and only missing ones are created. It is idempotent, so retries are safe.

## 3. Why store `path_ids`?
It makes "everything under Engineering" a single lookup. Since it uses IDs, renaming any ancestor doesn't change it. Only moving a tag changes it (a later topic).

## 4. Why a tree, not a graph?
One parent per tag keeps paths unambiguous and avoids cycles. A tag that belongs in two places (a DAG) is a follow-up to mention, not build.

Ready to move on to assigning a hierarchical tag (and what "tagged with Payments" implies for searching `Engineering`)?

## Turn 369 (2026-10-12T14:24:18.559Z)

### User

How do you ensure the duplicate check? What if I am trying to assign a duplicate tag name under the same uh parent like what sort of db query you will do to avoid this

### Assistant

# Duplicate Check: let the database enforce it

## Don't do this (race condition)
```sql
SELECT 1 FROM tag WHERE tenant_id=? AND parent_id=? AND name_normalized=?;
-- if none: INSERT ...
```
Two concurrent requests both see "none" and both insert, so you get duplicates.

## Do this: unique index + atomic insert

**1. Constraint**
```sql
CREATE UNIQUE INDEX uq_tag_sibling
ON tag (tenant_id, parent_id, name_normalized)
WHERE deleted_at IS NULL;      -- soft-deleted tags don't block reuse
```
Root tags use `parent_id = 'ROOT'` so NULLs don't bypass uniqueness.

**2. Insert and let the DB arbitrate**
```sql
INSERT INTO tag (tenant_id, tag_id, parent_id, name, name_normalized, path_ids, depth, ...)
VALUES (?, ?, ?, 'Payments', 'payments', ?, ?, ...);
```
If a sibling exists, the DB raises a unique violation, which the app maps to **409 Conflict**. The index check and insert are atomic, so concurrent requests can't both succeed.

## Explicit create vs get-or-create

**Explicit create:** catch the violation and return 409 (plus the existing `tagId`).

**Create-on-assign (get-or-create):**
```sql
INSERT INTO tag (...) VALUES (...)
ON CONFLICT (tenant_id, parent_id, name_normalized) WHERE deleted_at IS NULL
DO NOTHING;

SELECT tag_id FROM tag
WHERE tenant_id=? AND parent_id=? AND name_normalized=? AND deleted_at IS NULL;
```
Whether the insert won or lost, the select returns the one real tag. This is safe under concurrency and idempotent for retries.

## Assigning a duplicate tag to an entity
Same idea on the assignment table: the primary key is `(tenant_id, entity_key, tag_id)`.
```sql
INSERT INTO assignment (...) VALUES (...) ON CONFLICT DO NOTHING;
```
A repeat assign is a silent no-op, which is idempotent and returns success.

## Rename and move use the same guard
Renaming `A` to `B` under the same parent hits the same unique index, so a collision returns 409. A move to a new parent is checked against the new parent's children.

## Edge cases
- **Normalization must be done in the app consistently** (trim, NFKC, lowercase). The index only compares the stored `name_normalized`.
- **Databases without partial indexes** (e.g. MySQL): use a generated column, e.g. `active_key = IF(deleted_at IS NULL, 1, NULL)`, and include it in the unique key.
- **NoSQL (DynamoDB):** use a separate lookup item keyed `tenant#parent#normalized` and write it with a conditional put (`attribute_not_exists`) inside a transaction with the tag item.

## One-liner
> "Uniqueness is enforced by a unique index on (tenant, parent, normalized name), not by a check-then-insert. The insert is atomic, and a violation becomes a 409 or, for create-on-assign, a get-or-create."

Ready for hierarchical assignment next?

## Turn 406 (2026-10-12T14:29:21.745Z)

### User

Now let's focus on the assignment data model. I want to understand how the assignment will work

### Assistant

# Assignment Data Model

## Plain English
An assignment is one row saying: **"this entity has this tag."** It's a many-to-many link between entities (which we don't own) and tags (which we own).

## The table
```
assignment
  tenant_id      customer
  entity_key     what is tagged
  tag_id         which tag
  tagged_by      who applied it
  tagged_at      when
  PK (tenant_id, entity_key, tag_id)
```

## Entity identity
We don't store Jira issues, only a reference. A reference needs three parts, since IDs aren't unique across products:
```
product      = jira | confluence | bitbucket
entity_type  = issue | page | pull_request
entity_id    = PAY-123 / 98765 / 42
```
Store them as separate columns (better filtering, e.g. "all Jira issues") plus a composed `entity_key = jira:issue:PAY-123` for the key.

## What gets stored for hierarchical tags
**Only the tag the user picked.** If PAY-123 is tagged `Payments` (path Engineering/Backend/Payments), we store one row: `PAY-123 → Payments`. We do **not** also store rows for `Backend` and `Engineering`.

Why: if you stored ancestors too, moving or deleting a tag would require rewriting millions of rows, and unassigning would need to know which ancestors to keep.

"Find everything under Engineering" works at **query time** via the tag's `path_ids`: expand Engineering to its subtree, then match. (In the search index, we store `ancestor_tag_ids` per doc so that is a single filter.)

## Access patterns, and the indexes that serve them
| Query | Served by |
|---|---|
| Tags on this entity | PK prefix `(tenant, entity_key)` |
| Entities with this tag | Secondary index `(tenant, tag_id, entity_key)` |
| Count of entities per tag | Counter table, updated async (don't `COUNT(*)` on hot tags) |
| Cleanup when an entity is deleted | PK prefix, delete all its rows |
| Cleanup when a tag is deleted | Secondary index on `tag_id` |

## Operations
- **Assign:** `INSERT ... ON CONFLICT DO NOTHING`, so it's idempotent.
- **Unassign:** `DELETE WHERE tenant, entity_key, tag_id`.
- **Bulk assign:** batch insert, in chunks, with partial-failure reporting.
- **Limit:** max ~50 tags per entity, checked at assign time.

## Design points to say aloud
- **Reference, don't own**: no foreign key to Jira; consistency comes from events (entity deleted → cleanup).
- **Store IDs, not names**: rename is free.
- **Source of truth vs search**: this table is the truth and serves entity-to-tags reads; the search index is a derived copy fed by events.
- **Hot tag**: `bug` on 5M entities makes the `(tenant, tag_id)` index range huge. Always paginate by cursor, never scan it for user queries (use the search index).

Next: how the assignment flows into the search index, or entity-delete cleanup?

## Turn 440 (2026-10-12T14:33:51.369Z)

### User

I want to understand the query pattern or the API where I want to where I'm opening a Jira page and I want to find out all the tags on this and I want to know the full path till the root uh root tag so tell me how this API would fetch what query operation I would do

### Assistant

# Read tags on an entity, with full path to root

## API
```
GET /v1/tenants/{t}/entities/jira:issue:PAY-123/tags?expand=path
```
Response:
```json
[
  { "tagId": "T3", "name": "Payments",
    "path": [ {"id":"T1","name":"Engineering"},
              {"id":"T2","name":"Backend"},
              {"id":"T3","name":"Payments"} ] },
  { "tagId": "T9", "name": "Urgent",
    "path": [ {"id":"T9","name":"Urgent"} ] }
]
```

## Step by step

**Step 1: Get the assignments (1 indexed query, strongly consistent)**
```sql
SELECT tag_id FROM assignment
WHERE tenant_id = ? AND entity_key = 'jira:issue:PAY-123';
```
Result: `[T3, T9]`. This uses the PK prefix, so it is a single-partition read of at most ~50 rows.

**Step 2: Fetch those tags (batched, not N queries)**
```sql
SELECT tag_id, name, path_ids FROM tag
WHERE tenant_id = ? AND tag_id IN ('T3','T9');
```
Each row carries `path_ids`, e.g. T3 → `[T1,T2,T3]`.

**Step 3: Fetch the ancestors' names (one more batched lookup)**
Union all `path_ids` across the tags → `{T1,T2,T3,T9}`, subtract IDs already fetched → `{T1,T2}`.
```sql
SELECT tag_id, name FROM tag
WHERE tenant_id = ? AND tag_id IN ('T1','T2');
```

**Step 4: Assemble in memory.** For each tag, map `path_ids` to names, in order.

Total: **3 queries, regardless of depth**. This is the payoff of storing `path_ids`. The alternative, walking `parent_id` one level at a time, costs one query per level (or a recursive CTE).

## Make it faster: the cache
Steps 2 and 3 are lookups by `tagId`, so serve them from the tag dictionary cache:
```
MGET tenant:T3 tenant:T9 tenant:T1 tenant:T2   → name, path_ids
```
Misses fall back to the DB and then populate the cache. The cache is tiny (tags are few compared to assignments) and highly shared, so the hit rate is very high. Renames invalidate a single key. Practical cost: **1 DB query (assignments) + 1 cache MGET**.

## Edge cases
- **Deleted or deprecated tags:** filter them out, or flag them.
- **Entity with no tags:** return an empty list, with no further queries.
- **Many tags per entity:** capped at 50, so the response is bounded.
- **Authorization:** the caller must be able to view the entity. Jira enforces it before calling, or passes the user context.
- **Product-side batching:** a Jira board showing 50 issues should call a bulk endpoint `POST /entities/tags:batchGet {keys:[...]}`, which runs the same steps with one `IN` query on assignments and one MGET. This avoids N+1 calls.
- **Staleness:** names come from the cache, so they are current within seconds after a rename.

## One-liner
> "Read assignments by (tenant, entity), batch-fetch the tags, and use the stored `path_ids` to resolve all ancestor names in one more batched lookup, so cost is constant regardless of depth and mostly served from a tag dictionary cache."

Next: the reverse query (find all entities under `Engineering`) through the search index?

## Turn 474 (2026-10-12T14:36:25.529Z)

### User

> PK (tenant\_id, entity\_key, tag\_id)

Here you mentioned the primary key would be tenant ID, entity key, and tag ID, but uh in this query you are saying the primary key prefix. So is it aligning

### Assistant

# Yes, it aligns

A composite primary key `(tenant_id, entity_key, tag_id)` is backed by an index sorted in that column order. Any **leftmost prefix** of it can be served by that same index:

| Query filters on | Uses PK index? |
|---|---|
| `tenant_id` | ✅ prefix |
| `tenant_id, entity_key` | ✅ prefix (our "tags on this entity" query) |
| `tenant_id, entity_key, tag_id` | ✅ full key (exact assign/unassign/dup check) |
| `tag_id` alone, or `tenant_id, tag_id` | ❌ not a prefix (skips `entity_key`), needs the secondary index |

So `WHERE tenant_id=? AND entity_key='jira:issue:PAY-123'` is a range scan over all rows sharing that prefix, which returns the entity's tags (at most ~50) in one contiguous read. "Prefix" just means the leading columns of the key, not a different key.

## Why the order matters
- Our main read is "tags of an entity", so `entity_key` comes right after `tenant_id`.
- The reverse query ("entities with tag X") needs the second index `(tenant_id, tag_id, entity_key)`.

## In DynamoDB/Cassandra terms
- Partition key: `(tenant_id, entity_key)`
- Sort key: `tag_id`
- Reverse lookup: a GSI with partition `(tenant_id, tag_id)` and sort `entity_key`.

The same idea applies: the entity query hits one partition. (For a hot tag the GSI partition gets large, so shard it with a bucket suffix, or use the search index instead.)

## Turn 526 (2026-10-12T15:42:07.173Z)

### User

okay, I think mostly clear on the entity model, now jump into deletion, how would the tag deletion would work,

### Assistant

# Tag Deletion

Deletion is where fan-out is real: assignments and descendants both reference the tag.

## 1. Decide semantics first (clarify)
| Question | Options |
|---|---|
| Tag has **children**? | Reject (409), cascade delete subtree, or reparent children to the grandparent |
| Tag has **assignments**? | Reject if in use, or remove them all |
| Reversible? | Soft delete with a grace period (e.g. 30 days), then purge |

**Recommended:** soft delete, cascading to the subtree and its assignments, run async, with an explicit confirm and a usage count shown to the user ("used on 12,400 items").

## 2. Flow

**Step 1: Synchronous (fast, O(1))**
```
DELETE /v1/tenants/{t}/tags/{tagId}?cascade=true   If-Match: version
```
1. AuthZ: the caller can manage the tag.
2. One transaction:
   - Set `deleted_at = now()`, `status = DELETING`, and version+1 on the tag. For a subtree, mark the descendants (`WHERE path_ids @> [tagId]`), which is tags only, so it is small.
   - Insert an audit row and outbox row `TagDeleted(tagId, subtreeIds)`.
   - The partial unique index frees the name for reuse immediately.
3. Return **202 Accepted** with a job ID.

From this moment the tag is logically gone: reads filter it out, and the cache entry is invalidated. This is why soft delete matters; the user sees an instant result.

**Step 2: Asynchronous cleanup (the real fan-out)**
A worker consumes `TagDeleted`:
1. **Assignments:** page through the reverse index `(tenant, tag_id, entity_key)` in batches (e.g. 1,000), deleting each batch with a checkpoint cursor. Throttle per tenant.
2. **Search index:** for each batch, emit `TagUnassigned` events (or an update-by-query removing the tag IDs from `tag_ids` and `ancestor_tag_ids`). This goes through the same ordered, versioned indexer path as normal unassigns.
3. **Counters, autocomplete, caches:** remove the entries.
4. **Finalize:** mark the job `COMPLETED`, and hard-delete the tag rows after the retention window.

**Step 3: Meanwhile, reads and writes stay correct**
- Reads: tag lookups skip `deleted_at != null`. Assignments pointing at a deleted tag are filtered by the tag dictionary (hide anything whose tag is deleted), so users never see ghosts while cleanup runs.
- Assign to a deleted tag: rejected (404/410) at the tag check.
- Search by a deleted tag ID: the resolver returns nothing.

## 3. Problems this surfaces
- **Job dies midway:** idempotent batches plus a checkpoint cursor; rerunning is safe because deletes of missing rows are no-ops.
- **Race: assign during delete.** The assign checks the tag status and the delete flips it in the DB. Use a row lock or version check on the tag in the assign transaction, so either assign wins first (and the cleanup finds it) or it fails. The cleanup sweeps until the reverse index is empty.
- **Hot tag (`bug` on 5M):** throttling and fairness per tenant, so one delete doesn't starve the indexer.
- **Undo within the grace period:** since assignments are physically deleted in the cleanup, undo is only possible if cleanup is deferred. Alternative: **delay cleanup until the grace period ends** and just hide via the dictionary. That makes undo O(1), and is simpler to reason about.
- **Children policy:** reparenting means a move, which changes `path_ids` of the whole subtree, so it is more fan-out. Cascade or reject is simpler.
- **GDPR/audit:** the audit log keeps who deleted what.

## 4. Delete vs merge
Often users really want **merge** ("Payment" into "Payments"): reassign the assignments to the target, then delete the source. Mention it as the better UX for sprawl, and as the same pipeline.

## One-liner
> "Delete is a soft delete in one transaction that hides the tag instantly, followed by an async, throttled, checkpointed cleanup of assignments and index entries. Reads filter deleted tags in the meantime, so the system is correct while it converges."

Next: **entity-deleted cleanup** (the reverse direction), or **search with permissions**?

## Turn 600 (2026-10-12T16:40:50.354Z)

### User

okay explain me the pronblem in delete one by one

### Assistant

# Problems in Tag Deletion, One by One

## 1. Children: what happens to sub-tags?
Delete `Backend` and `Payments` (its child) is left with a dead parent.
- Reject, cascade, or reparent?
- Reparenting rewrites `path_ids` for the whole subtree (fan-out).
- **Pick:** cascade (or reject if non-empty) for simplicity.

## 2. Assignments: millions of rows point at the tag
Deleting `bug` means removing 5M assignment rows, plus 5M search-doc updates. A single transaction would time out or lock the table.
- **Fix:** hide the tag instantly (soft delete), clean up asynchronously in batches.

## 3. Users must not see "ghost" tags during cleanup
Cleanup takes minutes or hours. Meanwhile, entities still hold assignments to a deleted tag.
- **Fix:** reads resolve names via the tag dictionary and skip deleted tags, so ghosts are invisible.

## 4. Race: assign while delete is running
Someone tags an issue with `bug` just as `bug` is deleted. The cleanup may have already passed that row, so an orphan assignment survives.
- **Fix:** the assign transaction checks the tag's status/version, so it either fails or lands before cleanup finishes. Cleanup keeps sweeping until the reverse index is empty.

## 5. Job crashes halfway
The worker dies at 60%.
- **Fix:** batches are idempotent (deleting a missing row is a no-op), with a checkpoint cursor so it resumes where it stopped.

## 6. Hot tags and noisy neighbors
One big delete floods the DB and the indexer, slowing other tenants.
- **Fix:** per-tenant throttling, small batches, and priority queues.

## 7. Search index consistency
The DB and the index are separate systems. Removing the assignments but failing to update the index leaves stale search results.
- **Fix:** emit unassign events through the same ordered, versioned indexer path, plus a periodic reconciliation job.

## 8. Undo
Users delete by mistake. If assignments are physically gone, you can't restore.
- **Fix:** keep the soft-deleted tag and defer the cleanup until a grace period (e.g. 30 days) ends. Undo is then just clearing `deleted_at`.

## 9. Name reuse
After deleting `Payments`, can someone create it again immediately?
- **Fix:** partial unique index `WHERE deleted_at IS NULL`. The new tag gets a new ID, so old assignments never attach to it by accident.

## 10. Audit and compliance
Who deleted what, and when? GDPR purge needs a hard delete eventually.
- **Fix:** audit row at delete time, and a final hard-delete after retention.

## Quick summary
| # | Problem | Fix |
|---|---|---|
| 1 | Children | Cascade/reject |
| 2 | Huge assignment fan-out | Soft delete + async batches |
| 3 | Ghost tags | Filter on read |
| 4 | Assign-vs-delete race | Status check in transaction |
| 5 | Job failure | Idempotent + checkpoint |
| 6 | Noisy neighbor | Throttle |
| 7 | Index drift | Events + reconciliation |
| 8 | Undo | Deferred cleanup |
| 9 | Name reuse | Partial unique index |
| 10 | Audit | Audit log + final purge |

Which one do you want to dig into: the race (#4) or the undo vs cleanup trade-off (#8)?

## Turn 637 (2026-10-12T16:45:04.418Z)

### User

Explain other pronblems involved in such system

### Assistant

# Other Problems in the Tagging System (beyond create/assign/rename/delete)

## 1. Tag move (re-parent)
Changing `Payments` from `Backend` to `Billing` rewrites `path_ids` for the whole subtree and `ancestor_tag_ids` in every search doc that carries them. Also needs cycle prevention (can't move under your own descendant), depth checks, and sibling-name collisions at the new parent. This is the one real fan-out left after IDs.

## 2. Tag merge
"Payment" into "Payments": reassign all assignments, handle entities that have both (dedupe), then delete the source. Keep a redirect/alias so old IDs and links still resolve.

## 3. Search at scale
- Queries: tag, AND/OR/NOT, subtree, combined with other filters.
- Subtree search by `ancestor_tag_ids` is cheap; expanding a huge subtree into an ID list is not.
- Index freshness (eventual consistency window).
- Deep pagination on hot tags (use cursors, not offsets).

## 4. Permissions in search
Users must only see entities they can access. Options: post-filter (breaks pagination and counts), store permission data in the index (goes stale), or call back to the product (slow). Also tag names and counts can leak the existence of private items.

## 5. Entity lifecycle events
Entity deleted, moved between projects, archived, or restored in Jira means orphan assignments. Needs event consumption, idempotent cleanup, and a reconciliation job if events are lost.

## 6. Dual writes and ordering
DB and search index are separate systems. Needs outbox, per-entity ordered partitions, version checks to drop stale events, and periodic DB-to-index diffs.

## 7. Hot keys and skew
`bug` on millions of entities creates a hot partition on the reverse index and heavy counters. Needs bucketed partitions, approximate counters, and caching.

## 8. Multi-tenancy
Noisy neighbors, per-tenant quotas and rate limits, huge-tenant sharding, isolation of cache and index, and data residency by region.

## 9. Autocomplete
Separate latency-critical path (prefix search, ranking by usage, per-user permissions on tag visibility). Needs its own index and cache.

## 10. Usage counts
`COUNT(*)` on hot tags is too slow. Use async or approximate counters, with drift repair.

## 11. Concurrency
Concurrent rename/move/delete on the same tag. Use optimistic locking (`version`) and clear conflict responses (409).

## 12. Integration and availability
Tag service outage must not break Jira page loads: timeouts, circuit breakers, graceful degradation (show page without tags). Also SDK, API versioning, bulk APIs, and idempotency.

## 13. Migration
Importing existing Jira labels and Confluence labels: dedupe, normalization, batch and resume, dual-run period.

## 14. Governance and abuse
Tag sprawl, near-duplicates, per-entity and per-tenant limits, who can create, rename, merge, delete.

## 15. Compliance and observability
Audit log, GDPR deletion, metrics (index lag, job progress, error rates), and alerting on reconciliation drift.

## Priority for a 45-minute interview
Cover in depth: **search + permissions, consistency (outbox/ordering), hot tags**. Mention briefly: move, merge, entity lifecycle, multi-tenancy. Park the rest as follow-ups.

Which one next: **permissions in search** or **entity lifecycle cleanup**?
