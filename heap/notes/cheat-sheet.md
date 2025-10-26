| Use Case                 | Comparator                            |
| ------------------------ | ------------------------------------- |
| Min Heap (default)       | `(a, b) -> a - b`                     |
| Max Heap                 | `(a, b) -> b - a`                     |
| Sort by frequency (desc) | `(a, b) -> freq.get(b) - freq.get(a)` |
| Sort by string length    | `(a, b) -> a.length() - b.length()`   |
| Kth smallest             | Use Min Heap, pop `k` times           |
| Kth largest              | Use Max Heap, pop `k` times           |
