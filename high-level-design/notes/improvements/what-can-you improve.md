## Read path

1. Latency of a read path.
2. Result quality - ranking , personalization
3. Consistency of results - results should not be stale.
4. How to cut down the throughput
5. Network round trip time.
6. Clock are not in sync
7. Elastic ssearch
8. Geo spatial search

## Write Path
1. Write are persistent in the event of failure.
2. How to handle resource contention - when thousand of user are fighting for the same resource.
3. How to ensure it is fair game.
4. How to bring consensus on result accuracy.
5. Network round trip time.
6. Clock are not in sync

## Failure strategy 
1. Fail Closed or fail open
2. Compensation pattern - if something fails , rollback strategy. What bad expirence is okay.
3. Client go crazy - degrade the output, blocked them completely
4. Downstream go crazy.
5. Things which can cause thread starvation.
6. Things which can explode the memory.
7. 