| Letter | Name           | Comment                                                                                                        |
|--------|----------------|----------------------------------------------------------------------------------------------------------------|
| S      | Scalability    | What should be the TPS ?                                                                                       |
| C      | Consistency    | Do you want consistency in the data ? If some row is deleted is it fine to return the row for short duration ? |
| A      | Availability   | What happens when there is network partition ? Do you still want to serve data                                 | 
| L      | Latency        | What is the expected latency of each api ?                                                                     |
| E      | Error Handling | Have you done graceful recover ?                                                                               |



| Letter | Name           | Comment                       |
|--------|----------------|-------------------------------|
| A      | API Gateway    | AAA, ROUsing , authentication |
| B      | Business layer | Controller etc                |
| C      | Caching        | Caching layer                 |
| D      | data later     |                               |