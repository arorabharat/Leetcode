Here’s a clean, interview-ready breakdown for **LeetCode 700. Search in a Binary Search Tree**.

---

# Problem recap

Given the root of a BST and a value `val`, return the **subtree rooted at the node whose value is `val`**, or `null` if
it doesn’t exist.

BST property: for any node `x`, all values in `x.left` are `< x.val` and all values in `x.right` are `> x.val`.

---

# Approach 1 — Iterative BST search (best in practice)

### Idea

Walk down from the root. At each step:

* if `val == node.val` → return the node
* if `val < node.val` → go left
* else → go right

This uses the BST property to discard half the remaining subtree each step.

### Code (Java)

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
   public TreeNode searchBST(TreeNode root, int val) {
      TreeNode curr = root;
      while (curr != null) {
         if (curr.val == val) return curr;
         curr = (val < curr.val) ? curr.left : curr.right;
      }
        return null;
    }
}
```

### Complexity

* **Time:** `O(h)` where `h` is tree height (`O(log n)` for balanced, `O(n)` worst-case skewed)
* **Space:** `O(1)`

---

# Approach 2 — Recursive BST search

### Idea

Same logic as iterative, but let recursion move down the tree.

### Code (Java)

```java
class Solution {
   public TreeNode searchBST(TreeNode root, int val) {
      if (root == null || root.val == val) return root;
      if (val < root.val) return searchBST(root.left, val);
      return searchBST(root.right, val);
    }
}
```

### Complexity

* **Time:** `O(h)` (same as above)
* **Space:** `O(h)` due to call stack (`O(log n)` balanced, `O(n)` worst-case)

---

# Approach 3 — BFS / DFS without using BST property (for contrast)

### Idea

Treat it as a plain binary tree and traverse level-order (BFS) or pre/in/post-order (DFS) until you find `val`. This *
*ignores** the BST advantage and explores potentially many nodes unnecessarily.

### Sketch (BFS)

```java
import java.util.*;

class Solution {
   public TreeNode searchBST(TreeNode root, int val) {
      if (root == null) return null;
      Queue<TreeNode> q = new LinkedList<>();
      q.offer(root);
      while (!q.isEmpty()) {
         TreeNode node = q.poll();
         if (node.val == val) return node;
         if (node.left != null) q.offer(node.left);
         if (node.right != null) q.offer(node.right);
        }
      return null;
   }
}
```

### Complexity

* **Time:** `O(n)` worst-case (may touch every node)
* **Space:** `O(w)` where `w` is the max width of the tree (up to `O(n)`)

---

# Which is best?

* **Best overall:** **Iterative BST search (Approach 1)** — minimal memory (`O(1)` space) and optimal time for BSTs (
  `O(h)`).
* Recursive (Approach 2) is equally fast but uses extra stack space and risks stack overflow on very skewed trees.
* BFS/DFS without BST property (Approach 3) is simple but suboptimal (`O(n)` time). Only use when you cannot rely on BST
  ordering (i.e., if the structure might not be a valid BST).

---

## Quick tips / edge cases

* If `root` is `null`, return `null`.
* Return the **node** (subtree root), not just `true/false`.
* The BST is assumed to contain **unique** values as per typical LeetCode setup for this problem.
