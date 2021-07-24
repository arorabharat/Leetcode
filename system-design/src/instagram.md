##

### Requirement
1. post , delete, view
2. Followers, Following
3. News feed


Type of database - No sql


### Database estimation
1 million active users day

10 post per day by each user
1MB per person -
Service should remain for 10 year
10*10*1MB*1 Million user * 365
36.5 billion * 1 MB
36500 TB

## Write operation
### Read operation
Follow screen - 10
Followers screen - 10
News Feed screen - 100


## Caching used ?


## Operation
Read
get followers
Get following
get news feed 
## Write
Update following
Update post


// TODO
Read about S3



