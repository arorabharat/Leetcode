**Merge sort** is a classic divide-and-conquer algorithm that sorts an array (or list) by recursively splitting it into
smaller subarrays, sorting those, and then merging them back together. Here’s how it works, step by step:

---

## 1. High-Level Intuition

1. **Divide**:

    * If the array has more than one element, split it into two roughly equal halves.
    * Repeat this splitting process on each half until you have subarrays of size 1 (a single‐element array is trivially
      “sorted”).

2. **Conquer (Sort + Merge)**:

    * Once you reach arrays of size 1, begin merging pairs of subarrays.
    * When merging two sorted subarrays, you repeatedly compare the smallest remaining elements in each and append the
      smaller one to a new array.
    * Continue until all elements from both subarrays are placed in order.

3. **Combine**:

    * After merging two subarrays, you get a larger sorted array.
    * Continue merging up the recursion tree until you’ve rebuilt the entire array in sorted order.

Because merge sort always splits until size 1 and then merges back up, its overall work divides into two
phases—splitting (which does almost no work beyond computing mid‐points) and merging (which processes every element
exactly once at each level).

---

## 2. Detailed Steps

Let’s say you have an initial array `A[0..n–1]`. The recursive process is:

1. **Base case**:

    * If the subarray length is 1 (i.e., `left == right`), it’s already sorted—return immediately.

2. **Recursive case**:

    1. Compute `mid = left + (right – left) / 2`.
    2. Recursively sort the left half:

       ```
       mergeSort(A, left, mid);
       ```
    3. Recursively sort the right half:

       ```
       mergeSort(A, mid+1, right);
       ```
    4. Merge the two sorted halves back into a single sorted segment:

       ```
       merge(A, left, mid, right);
       ```

---

## 3. The Merge Procedure

Merging two sorted subarrays is the heart of merge sort. Suppose after recursion you have:

* Left half:  `A[left..mid]` is sorted.
* Right half: `A[mid+1..right]` is sorted.

To merge them:

1. **Allocate temporary buffers** (often two small arrays) to hold copies of each half. For example:

   ```
   L = copy of A[left..mid]
   R = copy of A[mid+1..right]
   ```

2. **Use three indices**:

    * `i` scans through `L` (initially 0).
    * `j` scans through `R` (initially 0).
    * `k` writes back into the original array `A` starting at `left`.

3. **Compare and write**:

    * While both `i < L.length` and `j < R.length`:

        * If `L[i] ≤ R[j]`, set `A[k++] = L[i++]`.
        * Else, set `A[k++] = R[j++]`.
    * One of the two subarrays will run out first. Copy any remaining elements from the other subarray into `A[k..]`.

After this process, the segment `A[left..right]` is fully sorted.

---

## 4. Dry-Run Example

Consider `A = [38, 27, 43, 3, 9, 82, 10]`. We call `mergeSort(A, 0, 6)`:

1. **Split** into `[38, 27, 43, 3]` and `[9, 82, 10]`.
2. On the left `[38, 27, 43, 3]`:

    * Split into `[38, 27]` and `[43, 3]`.
    * `[38, 27]` splits into `[38]` and `[27]`.

        * Merge `[38]` and `[27]` → `[27, 38]`.
    * `[43, 3]` splits into `[43]` and `[3]`.

        * Merge `[43]` and `[3]` → `[3, 43]`.
    * Now merge `[27, 38]` and `[3, 43]`:

        * Compare 27 vs 3 → pick 3, then 27 vs 43 → pick 27, then 38 vs 43 → pick 38, finally 43 → result
          `[3, 27, 38, 43]`.
3. On the right `[9, 82, 10]`:

    * Split into `[9]` and `[82, 10]`.
    * `[82, 10]` splits into `[82]` and `[10]`.

        * Merge → `[10, 82]`.
    * Merge `[9]` and `[10, 82]`:

        * Compare 9 vs 10 → pick 9, then copy 10, then 82 → result `[9, 10, 82]`.
4. **Finally** merge `[3, 27, 38, 43]` and `[9, 10, 82]`:

    * Compare 3 vs 9 → pick 3
    * Compare 27 vs 9 → pick 9
    * Compare 27 vs 10 → pick 10
    * Compare 27 vs 82 → pick 27
    * Then 38 vs 82 → pick 38; 43 vs 82 → pick 43; last pick 82
      → Final sorted array: `[3, 9, 10, 27, 38, 43, 82]`.

---

## 5. Time and Space Complexity

1. **Time Complexity**:

    * **Divide step:** just computing a midpoint (O(1)).
    * **Conquer (recursive) step:** T(n/2) for left half + T(n/2) for right half.
    * **Merge step:** O(n) to combine two halves of total length n.
    * **Recurrence:**

      $$
      T(n) = 2\,T\bigl(\tfrac{n}{2}\bigr) + O(n),
      $$

      which solves to **O(n log n)** in all cases (best, average, and worst).

2. **Space Complexity**:

    * You need O(n) extra space for the temporary buffers during each merge level.
    * The recursion stack height is O(log n).
    * So overall, typical in‐place implementations use **O(n)** extra space. (There are in‐place merges too, but they
      are more complex and less commonly used.)

---

## 6. Stability and Characteristics

* **Stable Sort**: Merge sort preserves the relative order of equal elements because when merging, if `L[i] == R[j]`,
  you always choose from the left subarray first (or however you decide in code), ensuring equal elements stay in their
  original left-to-right order.
* **Predictable Performance**: Unlike quick sort, merge sort’s worst-case runtime is still O(n log n). You don’t need to
  worry about an adversarial input causing O(n²).
* **Good for Linked Lists**: Because merging two linked lists can be done in-place by pointer relinking, merge sort can
  be implemented on a singly linked list with only O(1) extra space (aside from recursion stack).
* **Parallelism**: Since the two halves are independent until the merge step, it’s straightforward to sort left and
  right subarrays in parallel on multicore machines.

---

## 7. When to Use Merge Sort

* When **stability** is required (for example, sorting objects by one key while preserving the original order of equal
  keys).
* When you know worst-case O(n log n) is important (e.g., sorting real-time data where adversarial inputs might appear).
* When sorting large datasets in external memory (variants like “external merge sort” read and write data from disk in
  large chunks).
* When sorting a linked list, since it can be done with only O(1) auxiliary space.

---

**Summary**:

* Merge sort splits the array recursively until each piece is size 1.
* It then merges sorted pieces by comparing the front of each list and building a larger sorted list.
* Its runtime is guaranteed O(n log n), and it is stable, making it a reliable general‐purpose sorting
  algorithm—especially when stability or worst-case bounds matter more than minimizing extra space.
