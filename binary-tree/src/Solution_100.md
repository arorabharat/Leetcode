Here’s a clean Java solution plus several ways to think about it, with pros/cons and exact complexities.

# Core idea

We want nodes grouped by depth (level). Two classic patterns do this:

1. **BFS (queue)**: traverse level by level.
2. **DFS (preorder)**: pass the current depth and append into `res[depth]`.

---

# Java implementations

```java
// LeetCode's TreeNode definition
class TreeNode {
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

import java.util .*;

class Solution {

    // ===== Approach 1: BFS with queue (level-size loop) =====
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();                 // number of nodes in this level
            List<Integer> level = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            res.add(level);
        }
        return res;
    }

    // ===== Approach 2: DFS (preorder) with depth tracking =====
    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    private void dfs(TreeNode node, int depth, List<List<Integer>> res) {
        if (node == null) return;
        if (res.size() == depth) res.add(new ArrayList<>()); // first time at this depth
        res.get(depth).add(node.val);
        dfs(node.left, depth + 1, res);
        dfs(node.right, depth + 1, res);
    }

    // ===== Approach 3 (variant): BFS using two queues =====
    public List<List<Integer>> levelOrderTwoQueues(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> curr = new ArrayDeque<>();
        Queue<TreeNode> next = new ArrayDeque<>();
        curr.offer(root);

        List<Integer> level = new ArrayList<>();
        while (!curr.isEmpty()) {
            TreeNode node = curr.poll();
            level.add(node.val);
            if (node.left != null) next.offer(node.left);
            if (node.right != null) next.offer(node.right);

            if (curr.isEmpty()) {
                res.add(level);
                level = new ArrayList<>();
                Queue<TreeNode> tmp = curr;
                curr = next;
                next = tmp; // swap
            }
        }
        return res;
    }
}
```

---

# Approaches, trade-offs, and complexities

### 1) BFS with a single queue (level-size loop) — **Best for this problem**

* **How**: Push root; while queue not empty, grab `size = q.size()`, pop exactly `size` nodes to form one level, pushing
  children as you go.
* **Time**: **O(n)** — each node enqueued/dequeued once.
* **Space**: **O(w)** auxiliary, where **w** is the tree’s max width (plus the output list, which is unavoidable).
* **Pros**: Most intuitive; clearly separates levels; no recursion depth issues; typically the cleanest for level order.
* **Cons**: None serious; just maintain a queue.

### 2) DFS (preorder) with depth tracking

* **How**: Recurse with `(node, depth)`. When first visiting a depth, create a new list; add the node’s value to
  `res.get(depth)`.
* **Time**: **O(n)** — each node visited once.
* **Space**: **O(h)** auxiliary recursion stack in the worst case (skewed tree), where **h** is height; plus output
  storage.
* **Pros**: Elegant; no queue needed; easy to adapt to other “collect by depth” tasks.
* **Cons**: Risk of stack overflow for very deep/skewed trees; some find BFS clearer for level order.

### 3) BFS with two queues (or a deque / sentinel)

* **How**: Keep `curr` and `next` queues; move across `curr`, filling `next`; swap at level end. (Or use a `null`
  sentinel or deque.)
* **Time**: **O(n)**.
* **Space**: **O(w)** auxiliary.
* **Pros**: Conceptually simple separation of “current” and “next” levels.
* **Cons**: Slightly more boilerplate than the level-size loop; no advantage over Approach 1.

---

## Which is best?

For LeetCode’s “Binary Tree Level Order Traversal,” **Approach 1 (BFS with a single queue and level-size loop)** is the
best balance of clarity, safety, and performance. It’s iterative (no deep recursion concerns), and it maps directly to
the level-by-level requirement.

---

## Edge cases to remember

* `root == null` → return `[]`.
* Highly skewed trees → prefer the iterative BFS to avoid deep recursion.
* Large/bushy trees → memory is dominated by the broader of (queue at widest level) vs (recursion stack), so BFS is
  usually more predictable.

You can submit the class as-is on LeetCode (with the `levelOrder` method as your solution).
