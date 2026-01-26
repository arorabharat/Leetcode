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
