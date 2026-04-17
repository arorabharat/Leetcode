## Shortest Path Algorithms (Single Source → All Nodes)

### Problem Definition

Given a graph **G(V, E)** and a **source node S**, compute the **minimum distance from S to every other node**.

---

# Algorithm Selection Guide

| Graph condition              | Algorithm                     | Time Complexity |
| ---------------------------- | ----------------------------- | --------------- |
| Non-negative weights         | Dijkstra                      | O(E log V)      |
| Negative weights present     | Bellman–Ford                  | O(VE)           |
| All edges weight = 1         | BFS                           | O(V + E)        |
| Directed Acyclic Graph (DAG) | Topological Sort + Relaxation | O(V + E)        |

---

# Core Relaxation Concept

dist[v] = \min(dist[v],; dist[u] + w(u,v))

Relaxation checks whether going through **u → v** produces a shorter path.

---

# 1. Dijkstra Algorithm

### When to use

* weights ≥ 0
* most common interview problem
* optimized using **Priority Queue**

### Pattern

```text
Initialize dist[source] = 0
Use min heap
Always expand closest unvisited node
Relax neighbors
```

### Complexity

```text
O(E log V)
```

---

# 2. Bellman–Ford Algorithm

### When to use

* negative edge weights exist
* need negative cycle detection

### Pattern

```text
Repeat V-1 times:
    relax all edges

Extra iteration:
    if distance improves → negative cycle
```

### Complexity

```text
O(VE)
```

---

# 3. BFS (Unweighted Graph)

### When to use

* all edges weight = 1
* shortest path = minimum number of edges

### Pattern

```text
Use queue
Traverse level by level
First visit gives shortest distance
```

### Complexity

```text
O(V + E)
```

---

# 4. DAG Shortest Path

### When to use

* graph has no cycles
* faster than Dijkstra

### Pattern

```text
Topological sort
Process nodes in topo order
Relax outgoing edges
```

### Complexity

```text
O(V + E)
```

---

# Quick Identification Cheat Sheet

| Clue in problem         | Likely algorithm       |
| ----------------------- | ---------------------- |
| minimum cost / distance | Dijkstra               |
| negative weights        | Bellman Ford           |
| minimum steps           | BFS                    |
| dependency graph        | DAG shortest path      |
| K stops constraint      | Bellman Ford variation |
| unit weight edges       | BFS                    |

---

# Mental Model

```text
Single source shortest path = repeated relaxation
Goal = minimize distance array
Choice of algorithm depends on edge constraints
```
