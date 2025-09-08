Here’s a crisp guide to **LeetCode 110. Balanced Binary Tree** with multiple approaches, complexities, and a clean Java
solution (bottom-up is best).

---

# Problem recap

A binary tree is **height-balanced** if for every node,
`|height(left) − height(right)| ≤ 1`.

---

## Approach 1 — Top-down (naïve) recursion

**Idea:**
At each node, compute `height(left)` and `height(right)` with a separate height DFS, then recurse to children to ensure
both subtrees are balanced.

**Code (for reference)**

```java
class TopDownSolution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return Math.abs(height(root.left) - height(root.right)) <= 1
                && isBalanced(root.left)
                && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
```

**Time:** `O(n^2)` in the worst case (degenerate tree), because height is recomputed for many nodes.
**Space:** `O(h)` recursion stack (`h` = tree height; worst `O(n)`, best `O(log n)` for balanced).

---

## Approach 2 — Bottom-up DFS with early exit (sentinel) ✅ **Best**

**Idea:**
Post-order compute heights once. If a subtree is imbalanced, bubble up a sentinel (e.g., `-1`) immediately—no more work
under that branch.

**Why best:**
Single pass, no redundant height recomputation.

**Time:** `O(n)` (each node visited once).
**Space:** `O(h)` recursion stack.

**Java (LeetCode-ready)**

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
    public boolean isBalanced(TreeNode root) {
        return dfsHeight(root) != -1;
    }

    // Returns height if balanced; otherwise returns -1 as a sentinel.
    private int dfsHeight(TreeNode node) {
        if (node == null) return 0;

        int lh = dfsHeight(node.left);
        if (lh == -1) return -1;                 // left subtree unbalanced

        int rh = dfsHeight(node.right);
        if (rh == -1) return -1;                 // right subtree unbalanced

        if (Math.abs(lh - rh) > 1) return -1;    // current node unbalanced

        return 1 + Math.max(lh, rh);             // height if balanced
    }
}
```

---

## Approach 3 — Iterative post-order (stack + map)

**Idea:**
Avoid recursion. Do a manual post-order traversal with a stack, compute/store heights in a map, and check balance on the
way back up.

**When to use:**
If recursion depth might overflow (very deep trees) or style guidelines prefer iterative.

**Time:** `O(n)`
**Space:** `O(n)` for stack + height map (and up to `O(h)` active stack frames during traversal).

**Code (reference)**

```java
import java.util.*;

class IterativeSolution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        Map<TreeNode, Integer> height = new HashMap<>();
        Deque<Object[]> stack = new ArrayDeque<>(); // {node, visitedFlag}
        stack.push(new Object[]{root, false});

        while (!stack.isEmpty()) {
            Object[] top = stack.pop();
            TreeNode node = (TreeNode) top[0];
            boolean visited = (boolean) top[1];

            if (node == null) continue;

            if (!visited) {
                stack.push(new Object[]{node, true});
                stack.push(new Object[]{node.left, false});
                stack.push(new Object[]{node.right, false});
            } else {
                int lh = height.getOrDefault(node.left, 0);
                int rh = height.getOrDefault(node.right, 0);
                if (Math.abs(lh - rh) > 1) return false;
                height.put(node, 1 + Math.max(lh, rh));
            }
        }
        return true;
    }
}
```

---

## Which approach should you use?

* **Use Bottom-up DFS (Approach 2)** in almost all cases: it’s short, clean, and **optimal `O(n)`** with minimal extra
  memory (`O(h)`).
* Top-down is easy to reason about but can degrade to `O(n^2)`.
* Iterative is a solid non-recursive alternative if you’re worried about stack depth.

---

### Quick edge notes

* Empty tree is balanced by definition.
* Early exit in Approach 2 minimizes work on unbalanced trees.
* For pathological depths (linked-list-like trees), Java recursion may hit stack limits—use the iterative variant if
  that’s a concern.
