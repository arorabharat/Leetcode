Here’s a clean Java solution plus multiple ways to solve it, with pros/cons and full complexity analysis.

# Problem recap

Given a binary tree and an integer `targetSum`, return `true` if there exists a **root-to-leaf** path with node values
summing to `targetSum`.

---

# ✅ Approach 1 — Recursive DFS (subtracting the running sum)

At each node, subtract its value from the remaining sum and recurse. If you hit a leaf, check whether the remaining sum
is zero.

### Java (LeetCode-ready)

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
 *         this.val = val; this.left = left; this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return dfs(root, targetSum);
    }

    private boolean dfs(TreeNode node, int rem) {
        if (node == null) return false;

        rem -= node.val;

        // If it's a leaf, we only succeed if remaining is 0
        if (node.left == null && node.right == null) {
            return rem == 0;
        }

        return dfs(node.left, rem) || dfs(node.right, rem);
    }
}
```

**Time:** `O(n)` (visit each node once)
**Space:** `O(h)` recursion stack, where `h` is tree height (`O(log n)` for balanced, `O(n)` worst-case skewed)

**Why it’s great:** Tiny, readable, and optimal in time. For most interview situations, this is the best choice.

---

# Approach 2 — Iterative BFS (level order) with running sums

Traverse level-by-level, carrying the running sum along with each node.

### Java

```java
import java.util.*;

class SolutionBFSVariant {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        Deque<TreeNode> qNode = new ArrayDeque<>();
        Deque<Integer> qSum = new ArrayDeque<>();
        qNode.offer(root);
        qSum.offer(root.val);

        while (!qNode.isEmpty()) {
            TreeNode cur = qNode.poll();
            int sum = qSum.poll();

            if (cur.left == null && cur.right == null && sum == targetSum) {
                return true;
            }
            if (cur.left != null) {
                qNode.offer(cur.left);
                qSum.offer(sum + cur.left.val);
            }
            if (cur.right != null) {
                qNode.offer(cur.right);
                qSum.offer(sum + cur.right.val);
            }
        }
        return false;
    }
}
```

**Time:** `O(n)`
**Space:** `O(w)` where `w` is the max width of the tree (worst-case `O(n)`)

**When to use:** Prefer if you want to avoid recursion (stack overflow concerns) and like explicit iteration.

---

# Approach 3 — Iterative DFS (stack) with running sums

Same idea as BFS but use a stack to simulate recursion.

### Java

```java
import java.util.*;

class SolutionDFSIterativeVariant {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        Deque<TreeNode> stackNode = new ArrayDeque<>();
        Deque<Integer> stackSum = new ArrayDeque<>();
        stackNode.push(root);
        stackSum.push(root.val);

        while (!stackNode.isEmpty()) {
            TreeNode cur = stackNode.pop();
            int sum = stackSum.pop();

            if (cur.left == null && cur.right == null && sum == targetSum) {
                return true;
            }
            if (cur.right != null) {
                stackNode.push(cur.right);
                stackSum.push(sum + cur.right.val);
            }
            if (cur.left != null) {
                stackNode.push(cur.left);
                stackSum.push(sum + cur.left.val);
            }
        }
        return false;
    }
}
```

**Time:** `O(n)`
**Space:** `O(h)` average (worst-case `O(n)`), because the stack holds a path plus some siblings

**When to use:** If you want iterative control/ordering similar to recursion without using the call stack.

---

# Which is best?

* **Best default:** **Recursive DFS (Approach 1)** — minimal code, optimal time, and typical interview-friendly
  solution.
* **If recursion depth is a concern** (very skewed trees and strict stack limits): **Iterative DFS or BFS** are safer.

All three are `O(n)` time; space depends on tree shape but is acceptable in practice.
