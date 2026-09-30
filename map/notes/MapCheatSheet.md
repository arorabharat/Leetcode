CRUD: put get remove
Check: containsKey containsValue
Views: keySet values entrySet
Safe ops: getOrDefault putIfAbsent
Smart ops: compute* merge
HashMap: null OK, not thread-safe, unordered


HashMap = fast & messy
LinkedHashMap = ordered
TreeMap = sorted
ConcurrentHashMap = safe for many threads
Hashtable = old and grumpy


HashMap        → clone
LinkedHashMap  → removeEldestEntry
TreeMap        → first/last, floor/ceiling, lower/higher, sub/head/tail, descending
ConcurrentMap  → forEach/search/reduce (parallel)
Hashtable      → elements, keys


```aiignore
Map
 └── SortedMap
       └── NavigableMap
             └── TreeMap   ✅
```

```aiignore
java.lang.Object
   |
   └── Map<K,V>
        |
        ├── SortedMap<K,V>
        |     |
        |     └── NavigableMap<K,V>
        |           |
        |           └── TreeMap<K,V>
        |
        ├── ConcurrentMap<K,V>
        |     |
        |     ├── ConcurrentNavigableMap<K,V>
        |     |     |
        |     |     └── ConcurrentSkipListMap<K,V>
        |     |
        |     └── ConcurrentHashMap<K,V>
        |
        └── AbstractMap<K,V>
              |
              └── HashMap<K,V>
                    |
                    └── LinkedHashMap<K,V>
```

---

## ConcurrentHashMap: Safe Atomic Operations

> **Golden Rule**: `null` keys and `null` values are **strictly forbidden** (throws `NullPointerException`).

### "Don't Do This -> Do This" (Avoiding Race Conditions)

| What You Want to Do | ❌ Don't Do This (Race Condition) | ✅ Do This Instead (Atomic) |
| :--- | :--- | :--- |
| **Initialize absent entry** | `if (!map.containsKey(k)) map.put(k, v);` | `map.putIfAbsent(k, v);` |
| **Lazy create list/set/cache** | `if (!map.containsKey(k)) map.put(k, new List());` | `map.computeIfAbsent(k, key -> new List());` |
| **Increment counter** | `map.put(k, map.get(k) + 1);` | `map.merge(k, 1, Integer::sum);` |
| **Custom update logic** | `val = map.get(k); map.put(k, transform(val));` | `map.compute(k, (key, val) -> transform(val));` |
| **Conditional remove** | `if (map.get(k).equals(v)) map.remove(k);` | `map.remove(k, v);` |
| **Conditional replace** | `if (map.get(k).equals(oldV)) map.put(k, newV);` | `map.replace(k, oldV, newV);` |

