Here’s the **TreeMap methods cheat-sheet** the way interviewers and system designers think about it: **what you call, what it costs, and why that cost makes sense**.

(Underlying structure reminder: `TreeMap` is backed by a **Red-Black Tree**, a self-balancing binary search tree.)

---

## **TreeMap — Commonly Used Methods & Time Complexity**

### **Core Map Operations**

| Method                    | What it does                      | Time Complexity |
| ------------------------- | --------------------------------- | --------------- |
| `put(K key, V value)`     | Insert or update a key-value pair | **O(log N)**    |
| `get(Object key)`         | Fetch value for a key             | **O(log N)**    |
| `remove(Object key)`      | Remove entry by key               | **O(log N)**    |
| `containsKey(Object key)` | Check if key exists               | **O(log N)**    |
| `size()`                  | Number of entries                 | **O(1)**        |
| `isEmpty()`               | Check if map is empty             | **O(1)**        |
| `clear()`                 | Remove all entries                | **O(N)**        |

---

### **Navigation / Ordering Operations (TreeMap Superpower)**

| Method              | What it does             | Time Complexity |
| ------------------- | ------------------------ | --------------- |
| `firstKey()`        | Smallest key             | **O(log N)**    |
| `lastKey()`         | Largest key              | **O(log N)**    |
| `floorKey(K key)`   | Greatest key ≤ given key | **O(log N)**    |
| `ceilingKey(K key)` | Smallest key ≥ given key | **O(log N)**    |
| `lowerKey(K key)`   | Greatest key < given key | **O(log N)**    |
| `higherKey(K key)`  | Smallest key > given key | **O(log N)**    |
| `firstEntry()`      | Entry with smallest key  | **O(log N)**    |
| `lastEntry()`       | Entry with largest key   | **O(log N)**    |

---

### **Range & View Operations (Interview Gold)**

| Method                       | What it does             | Time Complexity          |
| ---------------------------- | ------------------------ | ------------------------ |
| `headMap(K toKey)`           | View of keys `< toKey`   | **O(log N)**             |
| `tailMap(K fromKey)`         | View of keys `≥ fromKey` | **O(log N)**             |
| `subMap(K fromKey, K toKey)` | View of keys in range    | **O(log N)**             |
| `descendingMap()`            | Reverse-order view       | **O(1)** (view creation) |

⚠️ These return **views**, not copies — changes affect the original map.

---

### **Iteration / Collection Views**

| Method               | What it does              | Time Complexity |
| -------------------- | ------------------------- | --------------- |
| `keySet()`           | Set view of keys          | **O(1)**        |
| `values()`           | Collection view of values | **O(1)**        |
| `entrySet()`         | Set view of entries       | **O(1)**        |
| Iterating over views | Traverse all elements     | **O(N)**        |

---

### **Why everything is `O(log N)`**

Every key-based operation walks the height of a **balanced tree**.
Red-Black Trees guarantee height ≈ `log N`, so nothing degenerates into linear scans.

---

### **One-line intuition to lock it in**

If a TreeMap method **needs to locate a key or boundary**, it’s **O(log N)**.
If it **just reports metadata**, it’s **O(1)**.
If it **touches everything**, it’s **O(N)**.

This table alone covers **90% of TreeMap interview questions** and almost all real-world usage.
