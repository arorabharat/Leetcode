Here are several solid ways to solve **LeetCode 236. Lowest Common Ancestor of a Binary Tree**. I’ll start with the
clean recursive solution most people submit, then show alternatives, and finally compare them with time/space analysis.

---

# ✅ Approach 1: Post-order DFS (return-up the answer) — *most common & best for this problem*

**Idea:**
Do a post-order traversal. From each node, ask left and right subtrees whether they contain `p` or `q`.

* If both sides return non-null, current node is the LCA.
* If only one side is non-null, bubble that result up.
* If the current node is `p` or `q`, return it (so it can be a candidate LCA).

This works because the first node where `p` and `q` meet from different branches is their LCA.

### Java

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root; // p and q found in different branches
        return (left != null) ? left : right;           // bubble up the non-null side (or null)
    }
}
```

**Time:** `O(n)` (visit each node once)
**Space:** `O(h)` recursion stack, where `h` is tree height (`O(n)` worst-case skewed, `O(log n)` average for balanced)

**Why it’s best here:** Single LCA query, no extra structures, elegant, linear time.

---

# Approach 2: Iterative with parent pointers (stack + map)

**Idea:**
Build `parent` map via DFS/BFS until both `p` and `q` are discovered. Then walk up from `p` to root recording all
ancestors in a `Set`. Finally climb from `q` upwards until you hit the first ancestor in the set.

### Java (alternative implementation)

```java
import java.util.*;

class SolutionIterative {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        parent.put(root, null);
        stack.push(root);

        // Build parent map until both p and q are found
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }

        // Add ancestors of p
        Set<TreeNode> ancestors = new HashSet<>();
        TreeNode cur = p;
        while (cur != null) {
            ancestors.add(cur);
            cur = parent.get(cur);
        }

        // First common ancestor while climbing from q
        cur = q;
        while (!ancestors.contains(cur)) {
            cur = parent.get(cur);
        }
        return cur;
    }
}
```

**Time:** `O(n)` to build the map + `O(h)` to climb → `O(n)`
**Space:** `O(n)` for the map and set

**When to prefer:** When you want an iterative approach or you dislike recursion depth risk.

---

# Approach 3: Root-to-node paths, then compare

**Idea:**
Find path from root to `p` and root to `q` (via DFS), store both lists, then scan from the front until the paths
diverge; the last equal node is the LCA.

**Time:** `O(n)` to find both paths + `O(h)` compare → `O(n)`
**Space:** `O(h)` per path → `O(h)` to `O(n)` worst case

**Notes:** Clear conceptually, a bit more code and memory than Approach 1.

---

# Approach 4 (overkill here): Euler Tour + RMQ / Binary Lifting

* **Euler Tour + RMQ/Segment Tree/Sparse Table:** Preprocess for *many* LCA queries fast (`O(1)` or `O(log n)` per
  query) with `O(n log n)` preprocessing.
* **Binary Lifting (jump pointers):** Preprocess `up[v][i]` = `2^i`-th ancestor. Query LCA in `O(log n)`.

**Why overkill:** LeetCode #236 typically asks for **one** LCA per input. Preprocessing adds complexity without payoff.

---

## Edge cases (all approaches handle them):

* `p == root` or `q == root` → root is the LCA.
* `p` is ancestor of `q` (or vice-versa) → ancestor node returned correctly.
* LeetCode guarantees both `p` and `q` exist in the tree.

---

## Which approach should you use?

* **Best for this problem:** **Approach 1 (recursive post-order DFS)** — simplest, cleanest, optimal `O(n)` time, `O(h)`
  space.
* **If you want iterative / avoid recursion depth:** Approach 2.
* **If you like explicit paths:** Approach 3 (still `O(n)`, slightly heavier memory).

---

## Quick complexity summary

| Approach                    |                                             Time |         Space | Notes                        |
|-----------------------------|-------------------------------------------------:|--------------:|------------------------------|
| Post-order DFS (recursive)  |                                           `O(n)` |        `O(h)` | **Best for single query**    |
| Iterative with parent map   |                                           `O(n)` |        `O(n)` | Avoids recursion             |
| Root-to-node paths          |                                           `O(n)` | `O(h)`–`O(n)` | Conceptually simple          |
| Euler Tour / Binary Lifting | Preprocess `O(n log n)`; Query `O(1)`/`O(log n)` |  `O(n log n)` | Overkill unless many queries |

If you want, I can also show a version that detects the case where one of `p` or `q` might be missing (common interview
twist) and returns `null` accordingly.
