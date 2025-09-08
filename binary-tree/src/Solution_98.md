Here’s a clear rundown of multiple ways to solve **110. Balanced Binary Tree** and which one to prefer, followed by Java
implementations.

---

# Problem recap

A binary tree is height-balanced if for every node, the absolute difference between the heights of its left and right
subtrees is **≤ 1**.

---

# Approaches

## 1) Top-down recursion (naïve)

**Idea:**
For each node:

1. compute `height(left)`, `height(right)` via a separate DFS,
2. check `|hl - hr| ≤ 1`,
3. recursively ensure both subtrees are balanced.

**Why it’s slower:**
`height()` recomputes heights from scratch for many nodes, causing repeated work.

**Time:** Worst-case **O(n²)** (e.g., skewed tree).
**Space:** **O(h)** recursion stack (h = tree height; worst-case O(n), best O(log n) for balanced).

---

## 2) Bottom-up recursion with early exit ✅ **(Best)**

**Idea:**
Do a single postorder DFS. For each node, ask its children for their heights.

* If either subtree is already unbalanced, bubble up a sentinel (e.g., `-1`) immediately.
* Otherwise, return `1 + max(hl, hr)`.
* If `|hl - hr| > 1`, mark unbalanced by returning `-1`.

This fuses the height computation and balance check into one pass.

**Time:** **O(n)** (each node visited once).
**Space:** **O(h)** recursion stack.

---

## 3) Postorder traversal (iterative) using explicit stack

**Idea:**
Simulate postorder with a stack and a map from node to height.

* Process a node only after both children’s heights are known.
* If at any point `|hl - hr| > 1`, stop and return false.

**Time:** **O(n)**.
**Space:** **O(n)** in the worst case for the stack + map (also **O(h)** stack depth during traversal, but the height
map itself can be up to **O(n)**).

**When to use:**
If you must avoid recursion (very deep trees & risk of stack overflow), this is a workable alternative.

---

## 4) Pair return type (clean variant of #2)

Same as #2 but instead of a sentinel value, return a small record/class holding `(isBalanced, height)`.

* Slightly cleaner code, same complexity.

**Time:** **O(n)**.
**Space:** **O(h)**.

---

# Java implementations

## A) Best approach: bottom-up recursion with early exit (Approach 2)

```java
/**
 * LeetCode-style TreeNode definition:
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
        // returns -1 if unbalanced; otherwise returns height
        return heightOrFail(root) != -1;
    }

    private int heightOrFail(TreeNode node) {
        if (node == null) return 0;

        int lh = heightOrFail(node.left);
        if (lh == -1) return -1; // left subtree unbalanced

        int rh = heightOrFail(node.right);
        if (rh == -1) return -1; // right subtree unbalanced

        if (Math.abs(lh - rh) > 1) return -1; // current node unbalanced

        return 1 + Math.max(lh, rh);
    }
}
```

* **Time:** O(n)
* **Space:** O(h)

---

## B) Top-down recursion (Approach 1; simple but slower)

```java
class SolutionTopDown {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int lh = height(root.left);
        int rh = height(root.right);
        if (Math.abs(lh - rh) > 1) return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
```

* **Time:** O(n²) worst case
* **Space:** O(h)

---

## C) Iterative postorder with stack (Approach 3; recursion-free)

```java
import java.util.*;

class SolutionIterative {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        Map<TreeNode, Integer> height = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode prev = null;
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.peek();
            // Traverse down
            if (prev == null || prev.left == curr || prev.right == curr) {
                if (curr.left != null) {
                    stack.push(curr.left);
                } else if (curr.right != null) {
                    stack.push(curr.right);
                }
            }
            // Traverse up from left
            else if (curr.left == prev) {
                if (curr.right != null) {
                    stack.push(curr.right);
                }
            }
            // Traverse up from right or leaf processed
            else {
                stack.pop();
                int lh = height.getOrDefault(curr.left, 0);
                int rh = height.getOrDefault(curr.right, 0);
                if (Math.abs(lh - rh) > 1) return false;
                height.put(curr, 1 + Math.max(lh, rh));
            }
            prev = curr;
        }

        return true;
    }
}
```

* **Time:** O(n)
* **Space:** O(n) for map + O(h) stack depth (overall O(n) worst-case)

---

# Which approach is best?

* **Approach 2 (bottom-up with early exit)** is the best all-around: linear time, minimal extra space, and very simple.
* Use **Approach 3** only when you must avoid recursion.
* **Approach 1** is easy to reason about but can time out on large skewed trees due to O(n²).

---

## Edge notes

* An empty tree is balanced by definition.
* Early exit is important: the best approach stops descending as soon as any subtree is unbalanced.
* All approaches treat leaf height as 1 (or 0, depending on convention); the comparisons remain consistent as long as
  you’re consistent throughout.
