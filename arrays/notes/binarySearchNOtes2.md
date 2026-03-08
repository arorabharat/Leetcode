## Binary Search — Final Clean Notes (Lower Bound, Upper Bound, Exact Search)

---

# 1. Core Mental Model (FFTTT)

Most binary search problems reduce to:

```text
FFFFFTTTTT
     ^
   answer
```

👉 Goal = **find first TRUE**

Define TRUE condition:

* **Lower Bound** → `arr[i] ≥ x`
* **Upper Bound** → `arr[i] > x`
* **Binary Search on Answer** → `feasible(mid)`

---

# 2. Memory Section (Must Memorize)

## 🔹 Golden Definitions

```text
Lower Bound (LB) → first ≥ x
Upper Bound (UB) → first > x
Exact Search     → arr[mid] == x
```

---

## 🔹 Movement Rules (Fast Recall)

### Lower Bound

```text
>= x → go left
<  x → go right
```

### Upper Bound

```text
>  x → go left
<= x → go right
```

### Exact Search

```text
== → found
<  → right
>  → left
```

---

## 🔹 Interval Rule (Use Always)

```text
Use half-open [l, r)
Loop → while(l < r)
Stop → l == r
```

---

# 3. Why Loop Always Ends (Confidence Trick)

Track:

```text
size = r - l
```

Each step removes ≥1 element → must terminate.

👉 This is the fastest correctness check.

---

# 4. Lower Bound (Final Notes)

## Definition

```text
LB = first index i such that arr[i] ≥ x
```

## Range

```text
0 ≤ LB ≤ n
```

👉 Never −1 (position always exists)

## Key Meaning

```text
LB = insertion index
LB = count of elements < x
```

## Code (Final Template)

```java
public static int lowerBound(int[] arr, int x) {
    int l = 0, r = arr.length;

    while (l < r) {
        int mid = l + (r - l) / 2;

        if (arr[mid] >= x) r = mid;
        else l = mid + 1;
    }
    return l;
}
```

---

# 5. Upper Bound (Final Notes)

## Definition

```text
UB = first index i such that arr[i] > x
```

## Range

```text
0 ≤ UB ≤ n
```

👉 May return `n` (very normal)

## Key Meaning

```text
last occurrence = UB - 1
frequency = UB - LB
```

## Code (Final Template)

```java
public static int upperBound(int[] arr, int x) {
    int l = 0, r = arr.length;

    while (l < r) {
        int mid = l + (r - l) / 2;

        if (arr[mid] > x) r = mid;
        else l = mid + 1;
    }
    return l;
}
```

---

# 6. Exact Search (Classic Binary Search)

## Definition

```text
Find index where arr[i] == x
```

## Range

```text
Return index OR -1
```

## Code

```java
public static int binarySearch(int[] arr, int x) {
    int l = 0, r = arr.length - 1;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (arr[mid] == x) return mid;
        else if (arr[mid] < x) l = mid + 1;
        else r = mid - 1;
    }
    return -1;
}
```

---

# 7. LB vs UB (Important Understanding)

## When x NOT Present

👉 Always:

```text
LB = UB
```

Example:

```text
arr=[1,3,5,7], x=4
LB=2, UB=2
```

Reason:

```text
≥ x and > x become same (no equality case)
```

---

## When x Present

👉 Usually:

```text
LB ≠ UB
```

Example:

```text
arr=[1,2,4,4,4,6]
LB=2
UB=5
```

---

# 8. Final Comparison Table (Revision)

| Type         | Goal      | Pattern | Return     |
| ------------ | --------- | ------- | ---------- |
| Lower Bound  | first ≥ x | FFTTT   | index      |
| Upper Bound  | first > x | FFTTT   | index      |
| Exact Search | find x    | compare | index / -1 |

---

# 9. One-Line Final Recall (Best)

```text
LB → first TRUE of (arr[i] ≥ x)
UB → first TRUE of (arr[i] > x)
Exact → normal compare search
```

---

If you revise just sections **2 + 4 + 5**, you're fully ready for interviews.
