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

Here’s **Approach 3 (root→node paths, then compare)** in Java.

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

import java.util.*;

class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    List<TreeNode> pathP = new ArrayList<>();
    List<TreeNode> pathQ = new ArrayList<>();

    // LeetCode guarantees p and q exist, but guard anyway.
    if (!findPath(root, p, pathP) || !findPath(root, q, pathQ)) return null;

    // Compare the two paths to find the last common node.
    int i = 0, minLen = Math.min(pathP.size(), pathQ.size());
    TreeNode lca = null;
    while (i < minLen && pathP.get(i) == pathQ.get(i)) {
      lca = pathP.get(i);
      i++;
    }
    return lca;
  }

  // Adds nodes from root to target into 'path'; returns true if target is found.
  private boolean findPath(TreeNode node, TreeNode target, List<TreeNode> path) {
    if (node == null) return false;
    path.add(node);
    if (node == target) return true;  // identity comparison is correct for LeetCode

    if (findPath(node.left, target, path) || findPath(node.right, target, path)) {
      return true;
    }

    path.remove(path.size() - 1); // backtrack
    return false;
  }
}
```

Awesome—here’s the **iterative root→node paths** approach (no recursion), then compare the two paths to get the LCA.

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

import java.util.*;

class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    List<TreeNode> pathP = new ArrayList<>();
    List<TreeNode> pathQ = new ArrayList<>();

    if (!findPathIterative(root, p, pathP) || !findPathIterative(root, q, pathQ)) {
      return null; // defensive; LeetCode guarantees both exist
    }

    // Compare paths to find last common node
    int i = 0, minLen = Math.min(pathP.size(), pathQ.size());
    TreeNode lca = null;
    while (i < minLen && pathP.get(i) == pathQ.get(i)) {
      lca = pathP.get(i);
      i++;
    }
    return lca;
  }

  // Iterative DFS that fills 'outPath' with nodes from root to 'target' and returns true if found
  private boolean findPathIterative(TreeNode root, TreeNode target, List<TreeNode> outPath) {
    if (root == null) return false;

    // Frame.state: 0 = enter, 1 = left done, 2 = right done (about to backtrack)
    class Frame {
      final TreeNode node;
      int state;

      Frame(TreeNode n) {
        this.node = n;
        this.state = 0;
      }
    }

    Deque<Frame> stack = new ArrayDeque<>();
    Deque<TreeNode> pathStack = new ArrayDeque<>(); // current root->node path

    stack.push(new Frame(root));
    while (!stack.isEmpty()) {
      Frame f = stack.peek();

      if (f.state == 0) {                 // entering node
        pathStack.addLast(f.node);
        if (f.node == target) {         // found target: copy path and finish
          outPath.addAll(pathStack);
          return true;
        }
        f.state = 1;
        if (f.node.left != null) {
          stack.push(new Frame(f.node.left));
        }
      } else if (f.state == 1) {          // left processed, go right
        f.state = 2;
        if (f.node.right != null) {
          stack.push(new Frame(f.node.right));
        }
      } else {                             // backtrack
        stack.pop();
        pathStack.removeLast();
      }
    }
    return false; // not found
  }
}
```

**Why this is iterative “path approach”:**
We perform a manual DFS with our own stack and keep a `pathStack` that mirrors the current root→node path. When we hit
the target, we copy that path. Do it for `p` and `q`, then scan both paths from the start to find the last common node.

**Complexity:**

* Time: `O(n)` (each node pushed/popped a constant number of times; we run it twice → still `O(n)`).
* Space: `O(h)` for the stacks, where `h` is the tree height (`O(n)` worst case, `O(log n)` balanced).

Fair point. Here’s a crisper, **single-pass iterative “paths” solution** that feels tighter: we build parent links with
a stack (no recursion), reconstruct the two root→node paths by climbing parents, then compare from the root end.

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

import java.util.*;

class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    if (p == root || q == root) return root;

    // 1) Build parent pointers iteratively (DFS) until both targets are seen
    Map<TreeNode, TreeNode> parent = new HashMap<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    parent.put(root, null);
    stack.push(root);

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

    // 2) Build node->root paths by climbing parents
    List<TreeNode> pathP = new ArrayList<>();
    for (TreeNode cur = p; cur != null; cur = parent.get(cur)) pathP.add(cur);

    List<TreeNode> pathQ = new ArrayList<>();
    for (TreeNode cur = q; cur != null; cur = parent.get(cur)) pathQ.add(cur);

    // 3) Compare from the root side (end of the lists)
    int i = pathP.size() - 1, j = pathQ.size() - 1;
    TreeNode lca = null;
    while (i >= 0 && j >= 0 && pathP.get(i) == pathQ.get(j)) {
      lca = pathP.get(i);
      i--;
      j--;
    }
    return lca;
  }
}
```

**Why this feels better:** single DFS pass, no custom frame structs, clear separation of steps (parents → paths →
compare).

**Complexity:**

* Time: `O(n)` (each node visited once to build parents; path build/compare is `O(h)`)
* Space: `O(n)` for the parent map; `O(h)` for paths (worst-case `O(n)` on a skewed tree)

**Complexity:**

* Time: `O(n)` to collect both paths (each node visited at most once).
* Space: `O(h)` extra for each path + recursion stack, where `h` is the tree height (`O(n)` worst-case skewed,
  `O(log n)` for balanced).

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

