In a Redis Cluster with 3 masters ($M_1, M_2, M_3$) and 3 replicas ($R_1, R_2, R_3$), the lifecycle of an operation is a journey of deterministic routing, atomic execution, and asynchronous propagation.

Here is the end-to-end lifecycle of a `SET` operation (e.g., `SET listing_99 "available"`).

---

## 1. Client Discovery & Routing (The Mapping Phase)
When your application starts, the **Smart Client** (like Jedis or Lettuce) needs to know the topology.

* **Topology Fetch:** The client connects to any node (e.g., $M_1$) and runs `CLUSTER SLOTS`.
* **The Map:** It receives a routing table:
    * $M_1$: Slots $0$–$5460$
    * $M_2$: Slots $5461$–$10922$
    * $M_3$: Slots $10923$–$16383$
* **Local Hashing:** For the key `listing_99`, the client calculates:
  $$Slot = CRC16(\text{"listing\_99"}) \pmod{16384}$$
  Let’s assume the result is **Slot 7000**.
* **Direct Connection:** The client sees that **Slot 7000** belongs to **$M_2$** and sends the command directly to $M_2$'s IP address.



---

## 2. Master Execution (The Write Phase)
Once the command reaches **$M_2$**, the node performs several checks before committing:

1.  **Ownership Check:** $M_2$ verifies it actually owns Slot 7000. If it didn't (due to a recent reshard), it would send a `-MOVED` error.
2.  **Command Execution:** Since Redis is single-threaded for command execution, it processes the `SET` atomistically.
3.  **Memory Update:** The value is updated in $M_2$'s RAM.
4.  **AOF/RDB Persistence:** If configured, the operation is logged to the **Append Only File (AOF)** on $M_2$'s local disk to ensure durability.
5.  **Client Response:** $M_2$ sends "OK" back to the client. **Note:** In the default configuration, it does *not* wait for replicas yet.

---

## 3. Propagation (The Replication Phase)
Now that $M_2$ has committed the change, it must inform its replica, **$R_2$**.

* **Replication Buffer:** $M_2$ writes the command into a memory buffer (the replication backlog) specifically for $R_2$.
* **Asynchronous Send:** $M_2$ streams the command to $R_2$ over a TCP connection.
* **Replica Application:** $R_2$ receives the command, executes it in its own memory, and writes it to its own AOF.
* **Offset Tracking:** Both $M_2$ and $R_2$ maintain a **Replication Offset** (a byte counter). This allows them to know exactly how far "out of sync" they are if the connection drops.



---

## 4. Cluster Health (The Gossip Phase)
While the data is moving, the nodes are constantly talking to each other to ensure the cluster is still healthy.

* **Heartbeats:** Every node (Masters and Replicas) sends "Ping/Pong" packets on the **Cluster Bus** (port 16379).
* **State Sharing:** $M_2$ tells $M_1$ and $M_3$: *"I am healthy, and I still own slots 5461–10922."*
* **Failure Detection:** If $M_2$ stops responding, $M_1$ and $M_3$ will flag it as `PFAIL` (Possible Failure). If the majority of masters agree, they will signal $R_2$ to promote itself.

---

## 5. Failover (The Promotion Phase)
If $M_2$ stays down, the lifecycle takes a dramatic turn to maintain availability:

1.  **Election:** $R_2$ notices its master is down and asks $M_1$ and $M_3$ for permission to take over.
2.  **Majority Vote:** $M_1$ and $M_3$ grant the vote.
3.  **Role Swap:** $R_2$ promotes itself to **New-Master-2**.
4.  **Broadcast:** The new master sends a "Pong" to the whole cluster. The client’s next request to the old $M_2$ IP will time out, triggering the client to refresh its map and start talking to the new IP.



---

## Summary of the Journey

| Stage | Responsibility | Communication Type |
| :--- | :--- | :--- |
| **1. Routing** | Client Library | Deterministic Math ($CRC16$) |
| **2. Execution** | Master Node | Atomic Write to RAM/Disk |
| **3. Propagation** | Master $\rightarrow$ Replica | Asynchronous Streaming |
| **4. Maintenance** | Node $\rightarrow$ Node | Gossip Protocol (Binary) |
| **5. Failover** | Replicas/Masters | Quorum-based Election |

This design ensures that your Airbnb booking is processed in **under 1ms**, while the cluster works in the background to keep the data redundant and the system alive.