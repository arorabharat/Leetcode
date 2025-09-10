Here’s a clear rundown of multiple ways to solve **LeetCode 103. Binary Tree Zigzag Level Order Traversal**, with a
clean Java implementation and complexity analysis.

---

# Problem recap

Return the level-order traversal of a binary tree where direction alternates each level: left→right on level 0,
right→left on level 1, left→right on level 2, etc.

---

# Approach A (Recommended): BFS by levels + direction-aware insertion

Use a normal queue to traverse level-by-level. For each level, we collect node values into a deque/list.

* If the direction is left→right, append values at the end.
* If right→left, insert at the front (or build from the end).
  Toggle the direction after each level.

### Why this is best

* Single pass over nodes, no post-processing (like reversing lists).
* Simple, readable, and strictly linear time.

### Java (clean & accepted on LeetCode)

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

import java.util.*;

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean leftToRight = true;

        while (!q.isEmpty()) {
            int size = q.size();
            Deque<Integer> level = new ArrayDeque<>(size);

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();

                if (leftToRight) {
                    level.addLast(node.val);
                } else {
                    level.addFirst(node.val);
                }

                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }

            res.add(new ArrayList<>(level));
            leftToRight = !leftToRight;
        }

        return res;
    }
}
```

**Time Complexity:** `O(N)` — each node visited once.
**Space Complexity:** `O(W)` → `O(N)` in worst case (queue and one level’s deque), where `W` is max width of the tree.

---

# Approach B: BFS, collect left→right then reverse odd levels

Do a standard level-order traversal; always collect in left→right order. After finishing a level, if it’s an odd level,
`Collections.reverse(levelList)`.

**Pros:** Very simple logic.
**Cons:** Per-level reverse adds extra constant-factor work (still linear overall).

**Time:** `O(N)` (sum of per-level reversals is `O(N)`).
**Space:** `O(W)` → `O(N)`.

**Sketch:**

```java
while(!q.isEmpty()){
int size = q.size();
List<Integer> level = new ArrayList<>(size);
    for(
int i = 0;
i<size;i++){
TreeNode n = q.poll();
        level.

add(n.val);
        if(n.left !=null)q.

offer(n.left);
        if(n.right !=null)q.

offer(n.right);
    }
            if(!leftToRight)Collections.

reverse(level);
    res.

add(level);

leftToRight =!leftToRight;
}
```

---

# Approach C: Two-stacks zigzag

Maintain two stacks: `currentLevel` and `nextLevel`.

* Pop nodes from `currentLevel`.
* If direction is left→right, push `left` then `right` into `nextLevel`; else push `right` then `left`.
* When `currentLevel` empties, swap stacks and flip direction.

**Pros:** Classic zigzag technique; no reversals.
**Cons:** Slightly more ceremony than Approach A.

**Time:** `O(N)`
**Space:** `O(W)` → `O(N)`

**Sketch:**

```java
Stack<TreeNode> curr = new Stack<>(), next = new Stack<>();
curr.

push(root);

boolean leftToRight = true;

while(!curr.

isEmpty()){
List<Integer> level = new ArrayList<>();
    while(!curr.

isEmpty()){
TreeNode n = curr.pop();
        level.

add(n.val);
        if(leftToRight){
        if(n.left !=null)next.

push(n.left);
            if(n.right !=null)next.

push(n.right);
        }else{
                if(n.right !=null)next.

push(n.right);
            if(n.left !=null)next.

push(n.left);
        }
                }
                res.

add(level);

Stack<TreeNode> tmp = curr;
curr =next;next =tmp;
leftToRight =!leftToRight;
}
```

---

# Approach D: DFS (preorder) grouping by level

Recurse with `(node, level)`. Keep a `List<List<Integer>>` where `res.get(level)` is a deque/list.

* If level is even, append; if odd, prepend (use `LinkedList` or `ArrayDeque`).

**Pros:** Elegant; no queue.
**Cons:** Recursion depth `O(H)`; careful to use a structure that supports front insertion efficiently.

**Time:** `O(N)`
**Space:** `O(H)` for recursion + `O(W)` for level containers → `O(N)` worst case.

**Sketch:**

```java
void dfs(TreeNode n, int lvl, List<LinkedList<Integer>> res) {
    if (n == null) return;
    if (lvl == res.size()) res.add(new LinkedList<>());
    if (lvl % 2 == 0) res.get(lvl).addLast(n.val);
    else res.get(lvl).addFirst(n.val);
    dfs(n.left, lvl, res);
    dfs(n.right, lvl, res);
}
```

---

## Which approach should you use?

* **Pick Approach A (BFS + direction-aware insertion)** for production and interviews: it’s simple, optimal, and avoids
  extra reversals or more complex data juggling.
* If you prefer a very compact pattern, Approach B is also fine and still `O(N)`.
* Approach C is a classic alternative; Approach D is nice if you’re already in a DFS mindset.

If you want, I can also provide the DFS or two-stacks version as complete Java code.
