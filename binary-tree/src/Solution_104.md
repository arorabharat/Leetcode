Here are several correct ways to solve **LeetCode 104. Maximum Depth of Binary Tree**—with Java code and a quick
comparison.

---

# Intuition (problem recap)

“Depth” = number of nodes on the longest path from root down to a leaf.
If `root == null`, depth is `0`.

---

# Approaches

## 1) Recursive DFS (postorder / divide & conquer)

**Idea:** Height of a node = `1 + max(height(left), height(right))`.
Recurse down, combine answers on the way up.

**Time:** `O(n)` — visit each node once
**Space:** `O(h)` — recursion stack (worst-case `O(n)` for a skewed tree; best/avg `O(log n)` if balanced)

```java
class Solution {
    // LeetCode-style TreeNode:
    public static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode l, TreeNode r) {
            this.val = val;
            this.left = l;
            this.right = r;
        }
    }

    // 1) Recursive DFS (postorder)
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return 1 + Math.max(left, right);
    }

    // -------- Optional: other approaches below --------

    // 2) Iterative DFS using a stack (simulate recursion)
    public int maxDepthIterativeDFS(TreeNode root) {
        if (root == null) return 0;
        java.util.Deque<Pair> stack = new java.util.ArrayDeque<>();
        stack.push(new Pair(root, 1));
        int answer = 0;
        while (!stack.isEmpty()) {
            Pair cur = stack.pop();
            TreeNode node = cur.node;
            int depth = cur.depth;
            answer = Math.max(answer, depth);
            if (node.left != null) stack.push(new Pair(node.left, depth + 1));
            if (node.right != null) stack.push(new Pair(node.right, depth + 1));
        }
        return answer;
    }

    // 3) BFS level-order (queue)
    public int maxDepthBFS(TreeNode root) {
        if (root == null) return 0;
        java.util.ArrayDeque<TreeNode> q = new java.util.ArrayDeque<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            depth++; // advancing to next level
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
        }
        return depth;
    }

    // 4) Morris-style traversal (advanced, O(1) extra space)
    // Uses temporary threads; tracks current depth while traversing.
    // NOTE: More complex; restores all links before returning.
    public int maxDepthMorris(TreeNode root) {
        int maxDepth = 0, curDepth = 0;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                // going down by one
                curDepth++;
                maxDepth = Math.max(maxDepth, curDepth);
                // go right, then step back up (depth--) after moving past this node
                cur = cur.right;
                curDepth--;
            } else {
                // find predecessor
                TreeNode pred = cur.left;
                int steps = 1; // levels from cur down to pred
                while (pred.right != null && pred.right != cur) {
                    pred = pred.right;
                    steps++;
                }
                if (pred.right == null) {
                    // create thread, go left (down)
                    pred.right = cur;
                    curDepth++; // going down into left subtree
                    cur = cur.left;
                    maxDepth = Math.max(maxDepth, curDepth);
                } else {
                    // thread exists -> remove and go right (back up from left subtree)
                    pred.right = null;
                    cur = cur.right;
                    curDepth -= steps; // we climbed back up 'steps' levels
                }
            }
        }
        return maxDepth;
    }

    // helper for iterative DFS
    static class Pair {
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

## 2) Iterative DFS with a stack

**Idea:** Push `(node, depth)` onto a stack; pop and push children with `depth+1`, track max.

**Time:** `O(n)`
**Space:** `O(h)` average; worst `O(n)` if skewed

(See `maxDepthIterativeDFS` above.)

---

## 3) BFS (level-order) with a queue

**Idea:** Traverse level by level; the number of layers processed is the depth.

**Time:** `O(n)`
**Space:** `O(w)` — `w` = maximum width of the tree (worst `O(n)`)

(See `maxDepthBFS` above.)

---

## 4) Morris Traversal (advanced, constant extra space)

**Idea:** Use temporary threads to avoid recursion/stack/queue; keep a running `curDepth` as you go down and back up.

**Time:** `O(n)`
**Space:** `O(1)` extra (in-place, restores tree)

(See `maxDepthMorris` above.)

---

# Which is “best”?

* **For interviews/production readability:** **Recursive DFS** is the cleanest and least error-prone. It’s the typical
  accepted solution on LeetCode.
* **If recursion depth might overflow the stack (very deep/skewed trees):** use **Iterative DFS** or **BFS**.
* **If you truly need constant extra space:** the **Morris** variant achieves `O(1)` extra space, but it’s much trickier
  and rarely necessary.

**Practical pick:** Recursive DFS. It’s concise, clear, and `O(n)` time with `O(h)` space that’s fine for typical
constraints.
