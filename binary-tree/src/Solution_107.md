Here are three solid ways to solve **Binary Tree Level Order Traversal II** (bottom-up level order). I’ll give clean
Java, then compare approaches and complexities.

---

# Approach 1 — BFS by levels, **prepend** each level (no final reverse)

Use a normal queue to traverse level by level. For each level, collect the values into a list and **add it to the front
** of the result (a `LinkedList<List<Integer>>`), so the final order is bottom-up without a separate reverse pass.

### Code

```java
import java.util.*;

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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }

            // Prepend current level
            result.addFirst(level);
        }

        return result;
    }
}
```

**Time:** `O(n)` (each node enqueued/dequeued once)
**Space:** `O(n)` (queue + output; queue worst-case \~ breadth of tree)

**Why it’s great:** Simple, readable, and avoids an extra reverse pass.

---

# Approach 2 — BFS by levels, then **reverse** the result at the end

Exactly like standard level order; just call `Collections.reverse(result)` afterward.

### Code

```java
import java.util.*;

class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            result.add(level);
        }

        Collections.reverse(result);
        return result;
    }
}
```

**Time:** `O(n)` + `O(L)` for reverse where `L` = number of levels; overall `O(n)`
**Space:** `O(n)` (same reasoning)

**Why it’s nice:** Very straightforward if you already have a top-down traversal handy.

---

# Approach 3 — **DFS (preorder)** with depth indexing from the bottom

Use recursion to collect nodes by depth. When you first reach a new depth, insert a new level at the **front** of the
result. The list index for a node at `depth` is `result.size() - 1 - depth`.

### Code

```java
import java.util.*;

class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        dfs(root, 0, result);
        return result;
    }

    private void dfs(TreeNode node, int depth, LinkedList<List<Integer>> result) {
        if (node == null) return;

        // If we're at a new depth, add a new list at the front
        if (depth == result.size()) {
            result.addFirst(new ArrayList<>());
        }

        // Map depth to correct list from the end
        result.get(result.size() - 1 - depth).add(node.val);

        dfs(node.left, depth + 1, result);
        dfs(node.right, depth + 1, result);
    }
}
```

**Time:** `O(n)` (visit each node once)
**Space:** `O(h)` recursion stack (worst case `O(n)` for skewed tree) + output

**Why it’s useful:** Avoids a queue; neat index trick keeps bottom-up order.

---

## Which is best?

* For interviews and production readability, **Approach 1 (BFS + prepend)** is my pick: it’s iterative (no recursion
  depth risk), no final reverse, and very clear.
* **Approach 2** is just as performant and arguably the easiest to remember; it does an extra `reverse`, but that’s
  negligible.
* **Approach 3 (DFS)** is elegant but uses recursion; beware of stack depth on very skewed trees.

**Bottom line:** All are `O(n)` time and `O(n)` space overall. Choose **Approach 1** for clean, iterative code without
the reverse step.
