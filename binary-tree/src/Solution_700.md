Here’s a detailed write-up: multiple approaches, analysis of complexity, and a Java implementation.

---

## Problem restatement

You’re given the root of a binary search tree (BST) and an integer `val`. You need to search the BST for a node with
value equal to `val`. If such a node exists, return the subtree rooted at that node. Otherwise return `null`.

Because it’s a BST, for any node:

* All values in the left subtree are *less* than the node’s value.
* All values in the right subtree are *greater* than the node’s value.

---

## Approaches

### 1. Recursive BST search (simple recursion)

**Idea**:

* If current node is `null`, return `null`.
* If current node’s value equals `val`, return current node.
* If `val` is less than the node’s value, recurse on the left subtree.
* Otherwise recurse on the right subtree.

**Java code**:

```java
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) {
        return null;
    }
    if (root.val == val) {
        return root;
    } else if (val < root.val) {
        return searchBST(root.left, val);
    } else {
        return searchBST(root.right, val);
    }
}
```

**Complexity**:

| Measure            | Complexity                                                                                    |
|--------------------|-----------------------------------------------------------------------------------------------|
| Time               | O(h) where *h* is the height of the tree (worst case O(n), average O(log n) for balanced BST) |
| Space (call stack) | O(h) due to recursion depth (worst case O(n), average O(log n))                               |

**Pros**:

* Very simple and clean.
* Takes advantage of the BST property directly.

**Cons**:

* Uses recursion, so if the tree is very deep/unbalanced, it could lead to stack overflow or large recursion overhead.

---

### 2. Iterative BST search

**Idea**:
Do the same logic as the recursive approach, but with a `while` loop instead of recursion. At each iteration: check
current node, then move left or right depending on comparison, until either the node is found or you reach a `null`.

**Java code**:

```java
public TreeNode searchBST(TreeNode root, int val) {
    TreeNode current = root;
    while (current != null) {
        if (current.val == val) {
            return current;
        } else if (val < current.val) {
            current = current.left;
        } else {
            current = current.right;
        }
    }
    return null;
}
```

**Complexity**:

| Measure | Complexity                                                               |
|---------|--------------------------------------------------------------------------|
| Time    | O(h), where *h* is the height of the tree (worst O(n), average O(log n)) |
| Space   | O(1) extra space (ignoring the input)                                    |

**Pros**:

* Avoids recursion and eliminates recursive stack overhead.
* Constant auxiliary space, so no risk of stack overflow for deep trees.

**Cons**:

* Slightly more verbose than the recursive version, but not by much.

---

### 3. Convert BST → sorted list or array, then binary search (less direct / not recommended)

**Idea**:

1. Traverse the tree (in-order traversal) to produce a sorted list of node values or references.
2. Perform a binary search on the sorted list/array to find the target value.
3. If found, return the corresponding node (or subtree). Otherwise, return `null`.

**Java code sketch**:

```java
public TreeNode searchBST(TreeNode root, int val) {
    List<TreeNode> inorder = new ArrayList<>();
    inorderTraversal(root, inorder);
    int idx = binarySearch(inorder, val);
    if (idx >= 0) {
        return inorder.get(idx);
    } else {
        return null;
    }
}

private void inorderTraversal(TreeNode node, List<TreeNode> list) {
    if (node == null) return;
    inorderTraversal(node.left, list);
    list.add(node);
    inorderTraversal(node.right, list);
}

private int binarySearch(List<TreeNode> list, int val) {
    int lo = 0, hi = list.size() - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        int midVal = list.get(mid).val;
        if (midVal == val) {
            return mid;
        } else if (midVal < val) {
            lo = mid + 1;
        } else {
            hi = mid - 1;
        }
    }
    return -1;
}
```

**Complexity**:

| Measure | Complexity                                                                               |
|---------|------------------------------------------------------------------------------------------|
| Time    | O(n) to do in-order traversal + O(log n) for binary search ⇒ O(n) overall                |
| Space   | O(n) for storing the list + O(h) recursion stack for traversal ⇒ O(n) total (worst-case) |

**Pros**:

* Demonstrates how to combine tree and array approaches, useful for learning or certain modifications.
* Binary search is a familiar paradigm.

**Cons**:

* Inefficient for this problem specifically (extra O(n) time and O(n) space) compared to using the BST property
  directly.
* More complex and unnecessary for a simple BST search.
* You lose the direct linkage to subtree structure unless you store node references carefully.

---

### 4. Morris traversal or threaded binary tree approach (theoretical alternative)

You could, in theory, use a Morris in-order traversal or threaded tree variant to traverse the BST without recursion or
stack, in O(1) auxiliary space, and stop early once you find the value. But this is quite complex, and since you want to
*return the subtree rooted at the found node*, altering pointers temporarily might complicate preserving the tree
structure. This approach is **not recommended** in this problem context, because of the pointer manipulation and
restoration complexity, and risk of breaking the tree’s structure or temporarily corrupting it.

**Complexity**:

* Time: O(n) in worst case (because Morris traversal visits all nodes in-order)
* Space: O(1) auxiliary (not counting output), but manipulation overhead and complexity is high.

---

## Which approach is best?

For the LeetCode “Search in a Binary Search Tree” problem, the **iterative BST search** approach is generally the best
choice. Here’s why:

1. **Efficiency**: It runs in O(h) time where *h* is the height of the tree. In a balanced BST this is O(log n), and in
   the worst case it’s O(n). That’s the best you can do—because in the worst case, the target might not be present, and
   you may have to traverse from root down to a leaf.
2. **Space**: It uses O(1) extra space (constant space), which is better than the recursive approach’s O(h) stack usage,
   especially if the tree is skewed and deep.
3. **Robustness**: It avoids recursion stack overflow risks on very deep (or skewed) trees, which makes it more robust
   in languages/environments where recursion depth is limited or expensive.
4. **Simplicity**: Although slightly more verbose than the recursive version, it remains simple and clear, and directly
   uses the BST property without extra overhead.

If the tree is guaranteed to be balanced and recursion depth is not a concern, the **recursive approach** is also fine
and arguably cleaner to write. But in real-world or worst-case scenarios (e.g. extremely unbalanced tree or
pathologically deep tree), the iterative method is safer.

---

## Full Java code (both recursive and iterative)

Here is a complete Java class showing both methods side by side, and a small test harness for illustration.

```java
// Definition for a binary tree node (LeetCode style).
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class Solution {

    // --- Method 1: recursion ---
    public TreeNode searchBSTRecursive(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        } else if (val < root.val) {
            return searchBSTRecursive(root.left, val);
        } else {
            return searchBSTRecursive(root.right, val);
        }
    }

    // --- Method 2: iterative ---
    public TreeNode searchBSTIterative(TreeNode root, int val) {
        TreeNode current = root;
        while (current != null) {
            if (current.val == val) {
                return current;
            } else if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    // --- Example test harness ---
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Build a small BST:
        //       4
        //      / \
        //     2   7
        //    / \
        //   1   3
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        // Test recursive search
        TreeNode res1 = sol.searchBSTRecursive(root, 2);
        if (res1 != null) {
            System.out.println("Recursive: Found subtree root: " + res1.val);  // should print 2
        } else {
            System.out.println("Recursive: Not found");
        }

        // Test iterative search
        TreeNode res2 = sol.searchBSTIterative(root, 5);
        if (res2 != null) {
            System.out.println("Iterative: Found subtree root: " + res2.val);
        } else {
            System.out.println("Iterative: Not found");  // should print “Not found”
        }
    }
}
```

---

If you want, I can also walk through example run-throughs for both approaches (step by step) on skewed or balanced BSTs,
or compare their recursion / iteration behavior.
