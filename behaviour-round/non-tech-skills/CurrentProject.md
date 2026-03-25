High level design of Me Tab upsell:

My current project is a fairly complex system covering areas such as user interface, customer event handling, and API standardization. The goal is to 
launch a prime upsell on new page and ensure the pieces of making a upsell can be standardise into plug and play compnents so developer efforts can be reduced.

Layer 1 – Widget Rendering (UI layer)
This layer focuses on rendering a widget that nudges customers to buy a Prime plan at a discounted rate for the next billing cycle. The widget is shown only to customers 
who are in the last 30 days of their plan expiry and do not have autopay enabled. The primary objective is to improve customer retention.

Layer 2 – Eligibility Engine
This layer dynamically determines customer eligibility for the upsell offer. It evaluates business rules such as subscription status, expiry timeline, and other signals to decide the best discounted plan to offer.

Layer 3 – Data Aggregation Controller
This layer builds a controller that provides a dynamic data model used to render the widget. It includes information such as savings amount, plan price, discount percentage, 
and other relevant details required by the UI.

Layer 4 – Action & Payment Integration
This layer enables customers to take action on the upsell offer, such as purchasing the subscription through supported payment methods. While the payment API already exists, 
the current integration effort is high. My work involves aligning stakeholders and service owners to standardize API parameters and building an adapter library that transforms request 
objects into the required parameter format. This ensures the same action flow can be reused across multiple widget placements.

Non-technical contribution
Apart from technical work, I am also contributing to standardizing the overall process for building Prime upsell features by having increased collaboration between WW and IN prime PM. 
This helps ensure reusability across different marketplaces and reduces development effort for future implementations.