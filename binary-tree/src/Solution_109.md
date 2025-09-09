Below are 3 common ways to solve **Convert Sorted List to Binary Search Tree**. I’ve included a clean Java solution (
best approach) and brief code sketches for the others, plus full complexity analysis.

---

# Approach A — Optimal: In-order construction using length (single pass over nodes)

**Idea:**
Use the fact that an in-order traversal of a BST yields a sorted sequence.

1. First, count the length `n` of the list.
2. Recursively build the tree for the range `[0, n-1]`.

    * Build the left subtree for the left half.
    * Use the **current list node** as the root (advance the global pointer).
    * Build the right subtree for the right half.

Because we never re-scan to find middles and we advance the list pointer exactly once per node, this is **O(n)** time.

**Time:** `O(n)` (each node visited once)
**Space:** `O(log n)` recursion stack (balanced tree height); no extra arrays

### Java (LeetCode-ready)

```java
// LeetCode provides these:
// class ListNode { int val; ListNode next; ListNode() {} ListNode(int x) { val = x; } ListNode(int x, ListNode n){val=x; next=n;} }
// class TreeNode { int val; TreeNode left; TreeNode right; TreeNode() {} TreeNode(int x) { val = x; } TreeNode(int x, TreeNode l, TreeNode r){val=x;left=l;right=r;} }

class Solution {
    private ListNode curr; // global pointer that walks the list in-order

    public TreeNode sortedListToBST(ListNode head) {
        // Base: empty list
        if (head == null) return null;

        // Count length
        int n = 0;
        for (ListNode t = head; t != null; t = t.next) n++;

        curr = head;
        return build(0, n - 1);
    }

    // Build BST for indices [lo, hi] corresponding to positions in the sorted order
    private TreeNode build(int lo, int hi) {
        if (lo > hi) return null;
        int mid = lo + (hi - lo) / 2;

        // 1) Build left subtree from left half
        TreeNode left = build(lo, mid - 1);

        // 2) Root = current list node
        TreeNode root = new TreeNode(curr.val);
        root.left = left;

        // advance the list pointer
        curr = curr.next;

        // 3) Build right subtree from right half
        root.right = build(mid + 1, hi);

        return root;
    }
}
```

**Why this is best:**

* Linear time, minimal extra space, and very clean logic.
* Avoids repeatedly finding the middle with slow/fast pointers for each subproblem.

---

# Approach B — Middle-per-sublist recursion (slow/fast pointer each time)

**Idea:**
For a given list segment, find its middle with slow/fast pointers; make that the root; recurse on the left half and
right half (by splitting the list).

* You either pass head & tail bounds (`[head, tail)`) or physically split the list (keeping references).

**Time:** `O(n log n)` in general (each level scans much of the list to find middles; with T(n)=2T(n/2)+O(n) ⇒ O(n log
n) )
**Space:** `O(log n)` recursion stack
**Pros/Cons:** Simple conceptually; but strictly worse time than Approach A.

**Sketch:**

```java
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return build(head, null);
    }

    private TreeNode build(ListNode head, ListNode tail) {
        if (head == tail) return null;
        ListNode slow = head, fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        TreeNode root = new TreeNode(slow.val);
        root.left = build(head, slow);
        root.right = build(slow.next, tail);
        return root;
    }
}
```

---

# Approach C — Copy to array, then build BST from array

**Idea:**
Read all list values into an array (random access). Build a height-balanced BST from sorted array via standard
recursion.

**Time:** `O(n)` (copy `O(n)` + array-to-BST `O(n)`)
**Space:** `O(n)` extra for the array + `O(log n)` recursion stack
**Pros/Cons:** Easiest to implement and reason about; but extra `O(n)` memory.

**Sketch:**

```java
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer> vals = new ArrayList<>();
        for (ListNode t = head; t != null; t = t.next) vals.add(t.val);
        return build(vals, 0, vals.size() - 1);
    }

    private TreeNode build(List<Integer> a, int lo, int hi) {
        if (lo > hi) return null;
        int mid = lo + (hi - lo) / 2;
        TreeNode root = new TreeNode(a.get(mid));
        root.left = build(a, lo, mid - 1);
        root.right = build(a, mid + 1, hi);
        return root;
    }
}
```

---

## Which should you choose?

* **Best overall:** **Approach A** (in-order construction using length) — `O(n)` time, `O(log n)` space, no extra array.
* Prefer **Approach C** if you want the simplest code and don’t mind `O(n)` extra memory.
* **Approach B** is fine but typically slower (`O(n log n)`), so use only if you really want to avoid a length
  pass/global pointer pattern.

---

## Edge cases handled

* Empty list (`head == null`) → returns `null`.
* Single element → becomes the root.
* Duplicates are fine; balance is by structure (splits), not by distinctness.

That’s it—drop Approach A into LeetCode and you’re good.
