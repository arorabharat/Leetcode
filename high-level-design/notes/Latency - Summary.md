[![Alt text](https://img.youtube.com/vi/84ZLMbHefJI/0.jpg)](https://www.youtube.com/watch?v=84ZLMbHefJI)


This video explains the difference between latency and throughput in system design.

Latency refers to "how fast" a request can be completed. It's the amount of time it takes for a packet or request to travel across a network and for the server to process it (1:27, 4:27). The video breaks latency down into:

Network latency: Time for data to travel across the network (4:42).
Processing latency: Time the server spends handling the request (5:47). Latency is best measured using percentiles like P90 (90% of requests are faster than this) and P99 (99% of requests are faster than this), which give a better understanding of worst-case performance than just average latency (8:26).
Throughput refers to "how much" data or how many requests a system can handle per unit of time (12:00, 13:06). In the client-server model, this is often measured in Transactions Per Second (TPS) (14:01). Throughput can be increased by adding more servers or by increasing the concurrency of requests each server can handle, while being mindful of CPU usage (16:01, 17:42).

In essence, latency is about the speed of a single operation, while throughput is about the volume of operations over time.