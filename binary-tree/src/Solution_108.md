Here’s a clean Java solution plus multiple ways to think about it, with pros/cons and full complexity notes.

---

# Problem recap

Given a sorted (ascending) array `nums`, build a **height-balanced** BST.

“Height-balanced” means for every node, the left and right subtree heights differ by at most 1.

---

# Approach 1 — Recursive Divide & Conquer (pick middle as root) ✅

**Idea:**
For any subarray `nums[l..r]`, make the middle element the root so left side becomes the left subtree and right side
becomes the right subtree. Recurse.

This guarantees near-equal split each time ⇒ balanced tree.

**Java (LeetCode-ready):**

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] a, int l, int r) {
        if (l > r) return null;
        // either mid is fine; using left-middle keeps it simple
        int mid = (l + r) >>> 1; // avoids overflow
        TreeNode root = new TreeNode(a[mid]);
        root.left = build(a, l, mid - 1);
        root.right = build(a, mid + 1, r);
        return root;
    }
}
```

**Complexity**

* **Time:** `O(n)` — each element becomes exactly one node.
* **Space:** `O(log n)` average for recursion stack (balanced splits). In the worst case of extremely skewed splits (not
  happening here because we always pick mid), it could be `O(n)`, but **for this algorithm it’s `O(log n)`**.

**Why this is best:**

* Minimal code, optimal time, and only `O(log n)` extra stack space.
* Deterministically height-balanced due to picking mid each time.

---

# Approach 2 — Iterative with a Stack (simulate recursion)

**Idea:**
Simulate the recursive process using an explicit stack of “work items” holding the segment boundaries and a placeholder
node to fill.

**Java:**

```java
import java.util.*;

class SolutionIterative {
    static class Frame {
        TreeNode node;
        int l, r;

        Frame(TreeNode n, int l, int r) {
            this.node = n;
            this.l = l;
            this.r = r;
        }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        int l = 0, r = nums.length - 1;
        int mid = (l + r) >>> 1;
        TreeNode root = new TreeNode(nums[mid]);

        Deque<Frame> st = new ArrayDeque<>();
        // push right and left segments to process like DFS
        st.push(new Frame(root, l, r));

        while (!st.isEmpty()) {
            Frame f = st.pop();
            int left = f.l, right = f.r;
            if (left > right) continue;

            int m = (left + right) >>> 1;
            // ensure the node currently represents m
            f.node.val = nums[m];

            // Left child
            if (left <= m - 1) {
                int lm = (left + (m - 1)) >>> 1;
                f.node.left = new TreeNode(nums[lm]);
                st.push(new Frame(f.node.left, left, m - 1));
            }

            // Right child
            if (m + 1 <= right) {
                int rm = ((m + 1) + right) >>> 1;
                f.node.right = new TreeNode(nums[rm]);
                st.push(new Frame(f.node.right, m + 1, right));
            }
        }

        return root;
    }
}
```

**Complexity**

* **Time:** `O(n)` — every element becomes a node once.
* **Space:** `O(log n)` auxiliary in practice for a balanced tree (stack behaves like DFS pushing one side while
  descending the other). If you chose a BFS-like queue, the peak could grow larger; with this DFS ordering it stays
  logarithmic on balanced splits.

**When to prefer:**

* If recursion depth limits are a concern (rare for this problem sizes), this avoids recursion.

---

# Approach 3 — Build by “inorder assignment” (two-pass thinking)

**Idea:**
Since the array is inorder of the desired BST, you can:

1. First construct the *shape* of a perfectly balanced BST using sizes (by repeatedly splitting counts).
2. Then perform an inorder traversal and assign array values sequentially.

This is conceptually different but ends up equivalent to Approach 1 in shape and cost.

**Complexity**

* **Time:** `O(n)`
* **Space:** `O(log n)` recursion stack (for the shape build and/or inorder assignment)

**When to prefer:**

* If you find it clearer to separate “shape” (structure) from “values”, or you need to reuse a generic “build balanced
  tree of size k” routine.

---

## Which approach is best?

**Approach 1 (recursive mid-split)** is the most straightforward and optimal for this problem:

* It’s concise, easy to reason about, and guarantees balance.
* It matches optimal bounds: **`O(n)` time, `O(log n)` extra space**.

Use Approach 2 only if you must avoid recursion due to environment constraints. Approach 3 is mainly a conceptual
alternative (useful in related problems like rebuilding from traversals).

If you want, I can add quick tests or a helper to print the tree level-order to verify the result.
