Here’s a clean way to think about **Same Tree (LeetCode 100)** and a few solid approaches. I’ll start with the idiomatic
recursive solution in Java, then show iterative variants and compare.

---

# ✅ Approach 1: Recursive DFS (Preorder)

**Idea:**
Two trees are the same iff:

1. Both nodes are `null`, or
2. Both nodes are non-null, their values are equal, and their left subtrees are the same and right subtrees are the
   same.

This mirrors the definition, so the code is tiny and clear.

```java
// Definition for a binary tree node.
// LeetCode provides this class.
// public class TreeNode {
//     int val;
//     TreeNode left;
//     TreeNode right;
//     TreeNode() {}
//     TreeNode(int val) { this.val = val; }
//     TreeNode(int val, TreeNode left, TreeNode right) {
//         this.val = val;
//         this.left = left;
//         this.right = right;
//     }
// }

class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

**Time:** `O(n)` — visit each node once (n = total nodes across both trees).
**Space:** `O(h)` — recursion stack height `h` (worst-case `O(n)` for a skewed tree; `O(log n)` average for balanced).

**Why it’s great:** shortest, most readable, and optimal in time; auxiliary space is inherent to any traversal.

---

# 🔁 Approach 2: Iterative BFS with a Queue

**Idea:**
Do a synchronized level-order traversal. Always dequeue a pair `(a, b)`. If both `null` → continue; if one `null` or
`a.val != b.val` → false. Otherwise enqueue `(a.left, b.left)` and `(a.right, b.right)`.

```java
import java.util.*;

class SolutionBFS {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> qp = new LinkedList<>();
        Queue<TreeNode> qq = new LinkedList<>();
        qp.offer(p);
        qq.offer(q);

        while (!qp.isEmpty() && !qq.isEmpty()) {
            TreeNode a = qp.poll();
            TreeNode b = qq.poll();

            if (a == null && b == null) continue;
            if (a == null || b == null) return false;
            if (a.val != b.val) return false;

            qp.offer(a.left);
            qq.offer(b.left);
            qp.offer(a.right);
            qq.offer(b.right);
        }

        return qp.isEmpty() && qq.isEmpty();
    }
}
```

**Time:** `O(n)`
**Space:** `O(w)` where `w` is the maximum width of the tree (up to `O(n)` in worst case).

**Why you might use it:** avoids recursion (stack limits), predictable loop.

---

# 🧱 Approach 3: Iterative DFS with a Stack

**Idea:**
Same pairing as BFS, but use a stack (preorder-like). Push pairs `(a, b)`, pop, compare, then push children in the same
order.

```java
import java.util.*;

class SolutionDFSIter {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Deque<TreeNode> sp = new ArrayDeque<>();
        Deque<TreeNode> sq = new ArrayDeque<>();
        sp.push(p);
        sq.push(q);

        while (!sp.isEmpty() && !sq.isEmpty()) {
            TreeNode a = sp.pop();
            TreeNode b = sq.pop();

            if (a == null && b == null) continue;
            if (a == null || b == null) return false;
            if (a.val != b.val) return false;

            // push right then left so left is processed first
            sp.push(a.right);
            sq.push(b.right);
            sp.push(a.left);
            sq.push(b.left);
        }

        return sp.isEmpty() && sq.isEmpty();
    }
}
```

**Time:** `O(n)`
**Space:** `O(h)` average; worst-case `O(n)` (like recursion), but using explicit stacks.

---

# (Optional) 🧾 Approach 4: Serialization (not recommended here)

**Idea:**
Serialize both trees with a traversal that includes `null` markers (e.g., preorder with `#` for nulls), then compare the
strings/arrays.

**Pros:** turns it into a simple string/array equality.
**Cons:** extra `O(n)` space for both serializations, unnecessary overhead when a direct traversal comparison exists.

**Time:** `O(n)`
**Space:** `O(n)`

---

## Which approach is best?

* **Best overall:** **Recursive DFS (Approach 1)** — it’s the most concise, directly expresses the definition, and has
  optimal time. The auxiliary space is the recursion stack, which is as good as any traversal will do.
* **Use iterative (Approach 2 or 3)** if you:

    * must avoid recursion (very deep/skewed trees and strict stack limits), or
    * prefer explicit control over the traversal.

All approaches have the same `O(n)` time; choose based on readability and constraints on recursion.
