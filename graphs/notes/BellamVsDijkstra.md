# Shortest Path Algorithms — One Pager (Dijkstra vs Bellman-Ford)

## Problem Type

**Single Source → All Nodes shortest path**

Given:

* Weighted graph
* Source node `S`
* Goal: shortest distance from `S` to all other nodes

---

# Core Idea (Common to both)

Both algorithms rely on **edge relaxation**:

```java
if(dist[u] + weight < dist[v])
    dist[v] = dist[u] + weight;
```

Meaning:

> If going through `u` gives shorter path to `v`, update the distance.

---

# Dijkstra Algorithm

## When to use

* All edge weights are **non-negative**
* Need fast solution

## Key Idea

Always process the node with **minimum distance first** using **Priority Queue (Min Heap)**.

## Code Template (Adj List style)

```java
class Dijkstra {

    static class Edge {
        int target, weight;
        Edge(int t, int w) {
            target = t;
            weight = w;
        }
    }

    static int[] shortestPath(List<List<Edge>> graph, int source) {

        int V = graph.size();
        int[] dist = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<int[]> pq =
            new PriorityQueue<>((a,b)->a[1]-b[1]);

        pq.add(new int[]{source, 0});

        while(!pq.isEmpty()) {

            int[] curr = pq.poll();
            int u = curr[0];

            for(Edge e : graph.get(u)) {

                int v = e.target;
                int w = e.weight;

                if(dist[u] + w < dist[v]) {

                    dist[v] = dist[u] + w;

                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        return dist;
    }
}
```

## Complexity

```
Each edge relaxed once → E
Each PQ operation → log V
Total → O(E log V)
```

---

# Bellman-Ford Algorithm

## When to use

* Graph contains **negative edges**
* Need to **detect negative cycle**

## Key Idea

Relax **all edges repeatedly V−1 times**

Why V−1?
Shortest path cannot contain more than **V−1 edges** (no cycles in shortest path).

## Code Template (Adj List style — similar structure)

```java
class BellmanFord {

    static class Edge {
        int target, weight;

        Edge(int t, int w) {
            target = t;
            weight = w;
        }
    }


    static int[] shortestPath(List<List<Edge>> graph, int source) {

        int V = graph.size();

        int[] dist = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[source] = 0;


        // repeat V-1 times
        for(int i = 1; i <= V-1; i++) {

            for(int u = 0; u < V; u++) {

                for(Edge e : graph.get(u)) {

                    int v = e.target;
                    int w = e.weight;

                    if(dist[u] != Integer.MAX_VALUE &&
                       dist[u] + w < dist[v]) {

                        dist[v] = dist[u] + w;
                    }
                }
            }
        }


        // detect negative cycle
        for(int u = 0; u < V; u++) {

            for(Edge e : graph.get(u)) {

                int v = e.target;
                int w = e.weight;

                if(dist[u] != Integer.MAX_VALUE &&
                   dist[u] + w < dist[v]) {

                    System.out.println("Negative cycle exists");
                }
            }
        }

        return dist;
    }
}
```

## Complexity

```
Each iteration → O(E)
Total iterations → V-1

Total → O(VE)
```

---

# Key Intuition Difference

### Dijkstra

Greedy:

> Always expand the closest node first

Uses PQ to avoid unnecessary work.

---

### Bellman-Ford

Brute force improvement:

> Keep improving distances until no better path exists

Allows negative weights.

---

# Side-by-side Comparison

| Feature                  | Dijkstra                    | Bellman-Ford                |
| ------------------------ | --------------------------- | --------------------------- |
| Edge weights             | non-negative only           | can handle negative         |
| Core logic               | greedy                      | repeated relaxation         |
| Data structure           | priority queue              | simple loops                |
| Relax edges              | when node popped from PQ    | all edges V-1 times         |
| Negative cycle detection | ❌                           | ✅                           |
| Complexity               | O(E log V)                  | O(VE)                       |
| Speed                    | faster                      | slower                      |
| Use case                 | most shortest path problems | when negative edges present |

---

# Pattern to Remember

### Dijkstra structure

```text
pick minimum distance node
relax its neighbors
repeat
```

---

### Bellman-Ford structure

```text
repeat V-1 times:
    relax all edges
```

---

# Interview Summary

Use **Dijkstra** when:

* weights ≥ 0
* need efficient solution

Use **Bellman-Ford** when:

* negative weights exist
* need to detect negative cycle

---

If useful, next notes:

* Floyd Warshall comparison
* BFS vs Dijkstra decision tree
* Generic Graph template covering BFS, DFS, Dijkstra, Bellman-Ford
