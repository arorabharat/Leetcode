# Algorithms

### Breadth first search

```html
Time Complexity : O(V+E)
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/oDqjPvD54Ss/0.jpg)](https://www.youtube.com/watch?v=oDqjPvD54Ss)

### Depth first search

```html
Time Complexity :  O(V+E)
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/7fujbpJ0LB4/0.jpg)](https://www.youtube.com/watch?v=7fujbpJ0LB4)

## Single source the shortest path algorithm

### Dijkstra Algorithm of single source shortest path

```html
Time Complexity :
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/pSqmAO-m7Lk/0.jpg)](https://www.youtube.com/watch?v=pSqmAO-m7Lk)  
Time complexity of Dijkstra
algorithm : https://stackoverflow.com/questions/26547816/understanding-time-complexity-calculation-for-dijkstra-algorithm

#### Negative edge weight cycle

[![Alt text](https://img.youtube.com/vi/0HXYTi6ZG5Q/0.jpg)](https://www.youtube.com/watch?v=0HXYTi6ZG5Q)

### Bellman ford algorithm of single source shortest path

```html
Time Complexity :
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/FrLWd1tJ_Wc/0.jpg)](https://www.youtube.com/watch?v=FrLWd1tJ_Wc)  
[![Alt text](https://img.youtube.com/vi/lyw4FaxrwHg/0.jpg)](https://www.youtube.com/watch?v=lyw4FaxrwHg)

1. Helps in detecting negative edge weight cycle
2. Has higher complexity than Dijktra algorithm

## All pair shorted path

### Floyd's warshall algorithm of all pair shortest path

## Sorting in the Graph

### Topological sort ( using dfs and stack )

```html
Time Complexity :
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/eL-KzMXSXXI/0.jpg)](https://www.youtube.com/watch?v=eL-KzMXSXXI)

### Kahn's algorithm of Topological sort ( using bfs and in degree)

```html
Time Complexity :
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/cIBFEhD77b4/0.jpg)](https://www.youtube.com/watch?v=cIBFEhD77b4)

### All Topological sort

[comment]: <> (TODO)

#### Pending Topics related to topological sort

1. Use topological sort to find the minimum number of dependency need to be build to build a target dependency.
2. Use topological sort to find the minimum number of semester require to finish all the course in a graph (
   prerequisite graph). Assume that in each semester you could take infinite curses.
3. Use topological sort to find the minimum number of semester require to finish all the course in a graph (
   prerequisite graph). Assume that in each semester you could take K curses.
4. Use topological sort to find the maximum number of courses you would have to take in a single semester to finish all
   the courses as soon as possible.( prerequisite graph).
5. Find all topological sort

#### Topics related to topological sort

The Longest and shortest path in the directed acyclic graph  
[![Alt text](https://img.youtube.com/vi/TXkDpqjDMHA/0.jpg)](https://www.youtube.com/watch?v=TXkDpqjDMHA)

### Union find

[comment]: <> (TODO)

## Minimum spanning tree

### Prim's algorithm to find minimum spanning tree

```html
Time Complexity :
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/jsmMtJpPnhU/0.jpg)](https://www.youtube.com/watch?v=jsmMtJpPnhU)

### Kruskal Algorithm to find minimum spanning tree

```html
Time Complexity :
Space Complexity :
```
[![Alt txt](../resources/wiki.png)](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)

## Strongly connected components

### Tarjan's algorithm to find strongly connected component Algorithm

```html
Time Complexity :
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/wUgWX0nc4NY/0.jpg)](https://www.youtube.com/watch?v=wUgWX0nc4NY)

### Kosaraju's algorithm to find strongly connected components

```html
Time Complexity : O(V + E)
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/Rs6DXyWpWrI/0.jpg)](https://www.youtube.com/watch?v=Rs6DXyWpWrI)

## Topics

### Cycles

#### Detect cycle in Directed graph

Application of DFS

```html
Time Complexity : O(V+E)
Space Complexity :
```

[![Alt text](https://img.youtube.com/vi/joqmqvHC_Bo/0.jpg)](https://www.youtube.com/watch?v=joqmqvHC_Bo)

#### Detect cycle in Undirected graph

Application of DFS  
[![Alt text](https://img.youtube.com/vi/6ZRhq2oFCuo/0.jpg)](https://www.youtube.com/watch?v=6ZRhq2oFCuo)

#### Check if a graph is bi-partite

Application of BFS / DFS  
Same as check if the graph has odd length cycle  
[![Alt text](https://img.youtube.com/vi/0ACfAqs8mm0/0.jpg)](https://www.youtube.com/watch?v=0ACfAqs8mm0&t=611s)

#### Type of edges in the Graph

1. Tree edge : pre[u] < pre[v] < post[v] < post[u]
2. forward edge : pre[u] < pre[v] < post[v] < post[u]
4. back edge : pre[v] < pre[u] < post[u] < post[v]
3. cross edge : pre[v] < post[v] < pre[u] < post[u]   
   [![Alt text](https://img.youtube.com/vi/Y78KivF-hm0/0.jpg)](https://www.youtube.com/watch?v=Y78KivF-hm0)

#### Connected components

[comment]: <> (TODO)

#### Directed Acyclic graph

[comment]: <> (TODO)

#### Graph coloring

[comment]: <> (TODO)

#### Adjacency list

[comment]: <> (TODO)

#### In-degree and out degree

[comment]: <> (TODO)

#### Disjoint sets

[comment]: <> (TODO)

#### Union find by path compression

[comment]: <> (TODO)

#### Cut-vertex / Articulation point in graph

[![Alt text](https://img.youtube.com/vi/BxAgmaLWaq4/0.jpg)](https://www.youtube.com/watch?v=BxAgmaLWaq4)

#### bridge in graph

[![Alt text](https://img.youtube.com/vi/zxu0dL436gI/0.jpg)](https://www.youtube.com/watch?v=zxu0dL436gI)

#### Eulerian path and circuit (Hierholzer's algorithm)

[![Alt text](https://img.youtube.com/vi/xR4sGgwtR2I/0.jpg)](https://www.youtube.com/watch?v=xR4sGgwtR2I)
[![Alt text](https://img.youtube.com/vi/8MpoO2zA2l4/0.jpg)](https://www.youtube.com/watch?v=8MpoO2zA2l4)
[![Alt text](https://img.youtube.com/vi/1V_6nUUNoms/0.jpg)](https://www.youtube.com/watch?v=1V_6nUUNoms)

#### Hamiltonian Cycle

[![Alt text](https://img.youtube.com/vi/wh9mZCUf-z4/0.jpg)](https://www.youtube.com/watch?v=wh9mZCUf-z4)

#### Max Flow problems

[comment]: <> (TODO)

#### Travelling Salesman Problem

[comment]: <> (TODO)

#### bi-connected components

[comment]: <> (TODO)

9. 0-1 BFS
11. Boruvka’s algorithm
13. Fleury’s Algorithm
14. Ford-Fulkerson Algorithm for Maximum Flow Problem
15. Articulation point
16. Disjoint set
17. Kruskal algorithm
18. Max flow problem
19. Floyd warshall algo
20. union find and union find by the path compression
