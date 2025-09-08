Here’s a clean breakdown of multiple ways to solve **LeetCode 111. Minimum Depth of Binary Tree**—plus Java
implementations and complexity trade-offs.

---

# Problem recap

> Given the `root` of a binary tree, return its **minimum depth** (the number of nodes along the shortest path from the
> root down to the nearest **leaf**, where a leaf has no children).

Key pitfall: If a node has **only one child**, you **must not** take `min(leftDepth, rightDepth)` directly—use the
non-null side only.

---

# Approach 1 — BFS (Level-Order), stop at first leaf ✅ (Best practical)

**Idea.** Traverse level by level. The **first leaf** you encounter gives the minimum depth—so you can return
immediately.

**Why it’s great.** Typically fastest in practice because it short-circuits as soon as a shallow leaf is found.

**Time.** `O(N)` worst case (visit all nodes).
**Space.** `O(W)` where `W` is the maximum width of the tree (queue).

### Java (BFS)

```java
import java.util.*;

class Solution {
    public static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int depth = 1;

        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                if (cur.left == null && cur.right == null) return depth; // first leaf
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            depth++;
        }
        return depth; // unreachable in well-formed trees
    }
}
```

---

# Approach 2 — DFS (Recursive) with correct single-child handling

**Idea.** Recurse down. Use the non-null subtree when one child is absent.

**Time.** `O(N)` (must visit each node once).
**Space.** `O(H)` recursion stack where `H` is height (`H = N` worst-case skewed, `log N` balanced).

### Java (Recursive DFS)

```java
class Solution {
    public static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        // If one child is null, depth comes from the other child.
        if (root.left == null && root.right == null) return 1;
        if (root.left == null) return 1 + minDepth(root.right);
        if (root.right == null) return 1 + minDepth(root.left);
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }
}
```

---

# Approach 3 — DFS (Iterative) with stack (node, depth)

**Idea.** Emulate recursion using your own stack; track the depth per node and take the minimum when you hit leaves.

**Time.** `O(N)`.
**Space.** `O(H)` average (stack holds a root-to-node path; worst case `O(N)` for skewed).

### Java (Iterative DFS)

```java
import java.util.*;

class Solution {
    public static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Deque<Pair> stack = new ArrayDeque<>();
        stack.push(new Pair(root, 1));
        int ans = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {
            Pair p = stack.pop();
            TreeNode node = p.node;
            int d = p.depth;

            if (node.left == null && node.right == null) {
                ans = Math.min(ans, d);
            } else {
                if (node.left != null) stack.push(new Pair(node.left, d + 1));
                if (node.right != null) stack.push(new Pair(node.right, d + 1));
            }
        }
        return ans;
    }

    private static class Pair {
        TreeNode node;
        int depth;

        Pair(TreeNode n, int d) {
            node = n;
            depth = d;
        }
    }
}
```

---

## Which approach is best?

* **BFS (Approach 1)** is the **best practical choice**: it finishes as soon as it finds the first leaf, which is often
  near the top in real trees.
* **Recursive DFS (Approach 2)** is concise and perfectly fine, but it may traverse the whole tree even if a shallow
  leaf exists elsewhere, and recursion depth can be an issue on very skewed trees.
* **Iterative DFS (Approach 3)** avoids recursion limits and is acceptable, but doesn’t short-circuit as naturally as
  BFS.

---

## Quick comparison

| Approach          | Time   | Space  | Pros                             | Cons                                          |
|-------------------|--------|--------|----------------------------------|-----------------------------------------------|
| BFS (level-order) | `O(N)` | `O(W)` | Early exit at first leaf, robust | Needs queue                                   |
| DFS Recursive     | `O(N)` | `O(H)` | Simple, readable                 | Can traverse all; stack overflow on deep skew |
| DFS Iterative     | `O(N)` | `O(H)` | No recursion, explicit control   | No natural early exit like BFS                |

**Recommendation:** Use **BFS** for production; keep **recursive DFS** as a neat one-liner style for interviews (with
the single-child fix!).
