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

| Letter | Name      | Comment |
|--------|-----------|---------|
| C      |           |         |
| A      | Recovery  |         |
| P      | Resilient |         |

