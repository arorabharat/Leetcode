Here’s the **LinkedHashMap cheat-sheet** that mirrors the TreeMap table, but with a very different soul. Think **HashMap speed + predictable order**.

---

## **LinkedHashMap — Commonly Used Methods & Time Complexity**

*(Underlying structure: Hash table + doubly linked list)*

---

### **Core Map Operations**

| Method                    | What it does                    | Time Complexity |
| ------------------------- | ------------------------------- | --------------- |
| `put(K key, V value)`     | Insert or update key-value pair | **O(1)** avg    |
| `get(Object key)`         | Fetch value for key             | **O(1)** avg    |
| `remove(Object key)`      | Remove entry by key             | **O(1)** avg    |
| `containsKey(Object key)` | Check if key exists             | **O(1)** avg    |
| `size()`                  | Number of entries               | **O(1)**        |
| `isEmpty()`               | Check if map is empty           | **O(1)**        |
| `clear()`                 | Remove all entries              | **O(N)**        |

---

### **Ordering / Access Behavior (The Reason LinkedHashMap Exists)**

| Method / Feature             | What it does                         | Time Complexity |
| ---------------------------- | ------------------------------------ | --------------- |
| Iteration order              | Preserves insertion order by default | —               |
| Access-order mode            | Reorders entries on `get()`          | **O(1)**        |
| Constructor with accessOrder | Enables LRU-style behavior           | **O(1)**        |

```java
new LinkedHashMap<>(16, 0.75f, true); // access-order (LRU)
```

---

### **Iteration / View Operations**

| Method               | What it does                   | Time Complexity |
| -------------------- | ------------------------------ | --------------- |
| `keySet()`           | Keys in insertion/access order | **O(1)**        |
| `values()`           | Values in order                | **O(1)**        |
| `entrySet()`         | Entries in order               | **O(1)**        |
| Iteration over views | Traverse all entries           | **O(N)**        |

---

### **LRU Cache Hook (Interview Favorite)**

| Method                         | What it does             | Time Complexity |
| ------------------------------ | ------------------------ | --------------- |
| `removeEldestEntry(Map.Entry)` | Auto-remove oldest entry | **O(1)**        |

```java
protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
    return size() > CAPACITY;
}
```

---

### **What LinkedHashMap *does not* support**

* No sorting by key
* No range queries
* No `firstKey()` / `floorKey()` style navigation

If you need those → TreeMap.

---

### **Performance intuition**

* HashMap logic → **constant time lookup**
* Doubly linked list → **predictable iteration**
* Slight memory overhead → **worth it when order matters**

---

### **One-line mental lock**

**LinkedHashMap = HashMap speed + remembered order**
**TreeMap = Slower map + structural intelligence**

If you want, next we can do:

* `HashMap vs LinkedHashMap vs TreeMap` side-by-side table
* LRU cache implementation walkthrough
* Or the same table for `HashMap`, `ConcurrentHashMap`, or `PriorityQueue`
