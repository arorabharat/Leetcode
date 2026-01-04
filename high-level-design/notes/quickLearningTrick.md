Good for capturing non-functional requirement
| Letter | Name           | Comment                                                                                                        |
|--------|----------------|----------------------------------------------------------------------------------------------------------------|
| S      | Scalability    | What should be the TPS ?                                                                                       |
| C      | Consistency    | Do you want consistency in the data ? If some row is deleted is it fine to return the row for short duration ? |
| A      | Availability   | What happens when there is network partition ? Do you still want to serve data                                 | 
| L      | Latency        | What is the expected latency of each api ?                                                                     |
| E      | Error Handling | Have you done graceful recover ?                                                                               |



Good while making the design diagram

| Letter | Name           | Comment                                            |
|--------|----------------|----------------------------------------------------|
| A      | API Gateway    | AAA , authentication                               |
| R      | Routing        | which microservice request should go to ?          |
| R      | Rate limiting  | How many request you want to allow ? or throttling |
| B      | Business layer | Controller, handlers etc                           |
| C      | Caching        | Caching layer - business data                      |
| D      | data later     |                                                    |



| Letter | Name        | Comment |
|--------|-------------|---------|
| R      | Reliability |         |
| R      | Recovery    |         |
| R      | Resilient   |         |

Letter,Name,Comment
C,Consistency,All nodes see the same data at the same time (Linearizability).
A,Availability,"Every request receives a (non-error) response, without the guarantee that it contains the most recent write."
P,Partition Tolerance,The system continues to operate despite an arbitrary number of messages being dropped or delayed by the network between nodes.

| Letter | Name      | Comment |
|--------|-----------|---------|
| C      |           |         |
| A      | Recovery  |         |
| P      | Resilient |         |


| Letter | Name    | Comment                                                                                   |
|--------|---------|-------------------------------------------------------------------------------------------|
| L      | Logging | """What happened?"" (ELK Stack Splunk). Events with context."                             | 
| M      | Metrics | """Is it healthy?"" (Prometheus Grafana). Time-series data (CPU usage RPS Latency)."      |   
| T      | Tracing | """Where is the bottleneck?"" (Jaeger, Zipkin). Tracking a request across microservices." |



RCA - DHD

| Letter | Name               | Comment            |
|--------|--------------------|--------------------|
| R      | Requirements       | Requirements       | 
| C      | Core entities      | Core entities      |   
| A      | API / Interface    | API / Interface    |
| D      | Data flow          | Data flow          |
| H      | High Level Diagram | High Level Diagram |
| D      | Deep dive          | Deep Dive          |


Functional requirements - divide into two categories
ATL - Above the line
BTL - Below the line


PSR - Scaling techniques

| Letter | Name         | Comment             |
|--------|--------------|---------------------|
| P      | Partitioning | Kafka partition     | 
| S      | Sharding     | Database Sharding   |   
| R      | Replication  | Service replication |



