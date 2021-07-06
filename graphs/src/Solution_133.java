import java.util.*;

/**
 * https://leetcode.com/problems/clone-graph/
 */
class Solution_133 {

    Node bfs(Node s) {
        if (s == null) return null;
        Queue<Node> q = new LinkedList<>();
        q.add(s);
        boolean[] v = new boolean[101];
        v[1] = true;

        Node root = new Node(s.val);
        Map<Integer, Node> map = new HashMap<>();
        map.put(s.val, root);

        while (!q.isEmpty()) {
            Node f = q.remove();
            Node clone = map.get(f.val);
            for (Node c : f.neighbors) {
                if (!v[c.val]) {
                    v[c.val] = true;
                    q.add(c);
                    Node child = new Node(c.val);
                    map.put(c.val, child);
                }
                clone.neighbors.add(map.get(c.val));
            }

        }
        return root;
    }

    public Node cloneGraph(Node node) {
        if (node == null) return null;
        return bfs(node);
    }

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}