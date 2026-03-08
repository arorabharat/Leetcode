## Lower Bound vs Upper Bound (Binary Search)

### Definitions

**Lower Bound (LB)**

> First index `i` such that `arr[i] >= x`

**Upper Bound (UB)**

> First index `i` such that `arr[i] > x`


---

## One-line Memory Trick

* **LB:** first `>= x`
* **UB:** first `> x`


## Intuition (Sorted Array)

Let:

```
arr = [1, 2, 4, 4, 4, 6]
x = 4
```

* **Lower Bound → 2** (first `4`)
* **Upper Bound → 5** (first `>4` i.e., `6`)

👉 Difference gives frequency:

```
count(x) = UB - LB
```

## When to Use

* **Lower Bound**

    * Insert position
    * First occurrence
    * Range queries start

* **Upper Bound**

    * Last occurrence (`UB - 1`)
    * Count occurrences (`UB - LB`)
    * Range queries end

---

## Why `mid = l + (r - l) / 2` is Better than `(l + r) / 2`

## Quick Rule (Interview Memory)

👉 Always write:

```java
int mid = l + (r - l) / 2; // prevents overflow
```

Avoid:

```java
(l + r) / 2   // unsafe in edge cases
```

---

## Extra (Optional Optimization)

To bias toward upper mid:

```java
int mid = l + (r - l + 1) / 2;
```

Used when preventing infinite loops in some binary search variants.
