## URL shortneter

### Requirement

1. Convert long url to short url
2. If two users generate same long url, do the short url should be same ?
3. If user add short url then redirect to long url.
4. Length of the tiny url should not be more than 8 character
5. Does your system should be available all the time ? ( Availability )
6. Do the system need to be consistent ? (Yes)
7.

### Which Database to use ?

Nosql database ( key - value based )

1. No relation between the table
2. Because we have to store the data in the key - value pair so no sql.

## Space requirements

Assumption 500 million active users per month 500 / 30 / 24 / 3600 ~ 200 request per seconds

Write request 200 write request Read request 2000 read request

# Data will be persistent for 5 Year

500 million * 12 * 5 ~ 3 billion 1 MB per user , SO approx 3TB

## Do cache is required

80: 20 rule per day 500 million / 30 ~  20 million * 1 MB * 10 ~ 200 GB

### System APIS

POST api's
(long url, ttl, userid, token, )

Delete requirement
(short url)

GET
(short url,long url)

## Data model

long url -> short,ttl 

