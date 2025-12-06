
[![Alt text](https://img.youtube.com/vi/iHrsHqSAe18/0.jpg)](https://www.youtube.com/watch?v=iHrsHqSAe18)


This video explains Apache ZooKeeper, an open-source, centralized coordination service for distributed systems.

Here's a summary:

What it is: ZooKeeper acts like a shared project management tool for services in a distributed system, keeping them in sync (0:02).
Why it's used: It helps coordinate tasks, especially as distributed systems become complex, for things like leader election (0:33).
How it works: It stores data in a hierarchical structure of "Z-nodes," similar to a file system (0:51). There are persistent Z-nodes (remain until deleted) and ephemeral Z-nodes (disappear when the client session ends) (1:14). Clients can set "watches" to get notifications when data changes (1:41).
Architecture: ZooKeeper runs as a cluster called an "ensemble," with one leader handling all write requests for consistency, and followers serving read requests from their local copies (1:53).
Benefits: It offers consistency, fault tolerance, high performance (especially for reads), and simplicity (3:05).
Use Cases: It's used for configuration management, service discovery, leader election, and synchronization/distributed locks (4:08). Real-world examples include Apache Kafka, HBase, Hadoop, and companies like Netflix (4:48).