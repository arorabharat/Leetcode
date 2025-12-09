
## Requirements

### Functional Requirements
#### ATL
1. User Should be able to collect the data from different servers - CPU, IO, Memory etc, 
2. User should be able to query the data on different intervals like 1 min, 5 min, 1 hour etc
3. User should be able to create alarm on the collected metrics, should be able to trigger ticketing service , email etc, like simple threshold
#### BTL
1. User should be able to Customer metrics
2. User should be able to put Metric math on the metrics

### Non-Functional Requirements - SCALE 
* Medium scale - 10,000 nodes, 1000*10000  = 10^7 / 60 = 10^5 TPS   
* Monitoring should not add overhead or cause latency overhead on the servers - minimal overhead
* User should be able to see the metrics within <5 mins. - Availability over consistency
* Data for fine granularity should be purged after some time like 30 days -
* Latency - < 20-50ms latency reporting API , 
* Agent which gather the metrics - latency in nano seconds 
* Error handling - we might need have alarms on the monitoring service , using some other tool, 

## Core Entities

Metric-
- metric Id
- metric name 
- value
- timestamp

Alarm -
- threshold
- MetricId
- Window
- Number of data points

Graph - 
- List of metric

Event -
- Alarm
- Channel


## API interface

POST /metrics/{Id}
```
Metric-
- metric Id
- metric name 
- value
- timestamp
```

GET /metrics/{id}?start={startTime}&end={endTime}&depth={1M,5M}
```
[
{
Metric-
- metric Id
- metric name 
- value
- timestamp
}
,
]
```

CREATE /alarm
```
Alarm -
- threshold
- MetricId
- Window
- Number of data points
```


GET /alarm/{alarmId}
```
Alarm -
- threshold
- MetricId
- Window
- Number of data points
```

Emit Queue event -  
```
Event -
- Alarm
- Channel
```

## Data flow


## High level diagram



## Deep dive






