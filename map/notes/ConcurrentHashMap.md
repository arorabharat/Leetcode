Here’s the **ConcurrentHashMap cheat-sheet** designed for multithreaded and high-concurrency interview/system problems. Think **thread-safety + lock-free reads + high throughput**.

---

## **ConcurrentHashMap — Commonly Used Methods & Usage Patterns**

*(Underlying structure: Array of Node buckets with CAS for empty buckets + bucket-level synchronization on collision)*

---

### **The Golden Rule**
> `null` keys and `null` values are **strictly forbidden**. Any attempt to insert `null` throws a `NullPointerException`.

---

### **"Don't Do This -> Do This" (Avoiding Race Conditions)**

Even with `ConcurrentHashMap`, non-atomic compound operations (`check-then-act`) introduce subtle race conditions.

| What You Want to Do | ❌ Don't Do This (Race Condition) | ✅ Do This Instead (Atomic) |
| :--- | :--- | :--- |
| **Initialize absent entry** | `if (!map.containsKey(k)) map.put(k, v);` | `map.putIfAbsent(k, v);` |
| **Lazy create list/set/cache** | `if (!map.containsKey(k)) map.put(k, new List());` | `map.computeIfAbsent(k, key -> new List());` |
| **Increment counter** | `map.put(k, map.get(k) + 1);` | `map.merge(k, 1, Integer::sum);` |
| **Custom update logic** | `val = map.get(k); map.put(k, transform(val));` | `map.compute(k, (key, val) -> transform(val));` |
| **Conditional remove** | `if (map.get(k).equals(v)) map.remove(k);` | `map.remove(k, v);` |
| **Conditional replace** | `if (map.get(k).equals(oldV)) map.put(k, newV);` | `map.replace(k, oldV, newV);` |

---

### **Core Operations & Behavior**

| Method | What it does | Concurrency Guarantee |
| :--- | :--- | :--- |
| `get(Object key)` | Fetch value for key | **100% Lock-Free** (uses `volatile` read) |
| `put(K key, V value)` | Insert or update | Locks **only** the target bucket head node |
| `putIfAbsent(K key, V value)` | Put only if key absent | Atomic (returns `null` if added, else existing value) |
| `computeIfAbsent(K key, Function)` | Lazy compute if absent | Atomic (mapping function runs under bucket lock) |
| `merge(K key, V value, BiFunction)` | Combine / aggregate values | Atomic (remapping function runs under bucket lock) |
| `replace(K key, V oldVal, V newVal)`| Compare-and-swap update | Atomic (only updates if current value equals `oldVal`) |
| `remove(K key, Object value)` | Conditional remove | Atomic (only removes if current value equals `value`) |
| Iterators (`keySet`, `entrySet`) | Traverse entries | **Weakly consistent** (never throws `ConcurrentModificationException`) |

---

### **One-Line Mental Lock**
* **HashMap**: Fast, not thread-safe.
* **Hashtable**: Thread-safe via 1 global lock (concurrency bottleneck).
* **ConcurrentHashMap**: Thread-safe with no global lock; reads are lock-free, writes lock only the target bucket.
