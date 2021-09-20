import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * https://leetcode.com/problems/clone-graph/
 */
class Solution_133 {

    /**
     * @see DSA#BFS_TRAVERSAL
     */
    Node bfs(Node s) {
        if (s == null) return null;
        Queue<Node> q = new LinkedList<>();
        q.add(s);
        boolean[] v = new boolean[101];
        v[s.val] = true;

        Node root = new Node(s.val);
        Map<Integer, Node> cache = new HashMap<>();
        cache.put(s.val, root);

        while (!q.isEmpty()) {
            Node f = q.remove();
            Node clone = cache.get(f.val);
            for (Node c : f.neighbors) {
                if (!v[c.val]) {
                    v[c.val] = true;
                    q.add(c);
                    Node child = new Node(c.val);
                    cache.put(c.val, child);
                }
                clone.neighbors.add(cache.get(c.val));
            }
        }
        return root;
    }

    Node bfs1(Node g1) {
        if (g1 == null) return null;

        Queue<Node> q = new LinkedList<>();
        q.add(g1);
        boolean[] v = new boolean[101];
        v[g1.val] = true;

        Map<Integer, Node> cache = new HashMap<>();
        Node root = new Node(g1.val);
        cache.put(g1.val, root);

        while (!q.isEmpty()) {
            g1 = q.remove();
            Node g2 = cache.get(g1.val);
            Predicate<Node> visited = c -> v[c.val];
            Consumer<Node> addToCache = c -> cache.put(c.val, new Node(c.val));
            Consumer<Node> cloneNeighbour = c -> g2.neighbors.add(cache.get(c.val));
            Consumer<Node> markVisited = c -> v[c.val] = true;
            g1.neighbors.stream().filter(visited.negate()).forEach(q::add);
            g1.neighbors.stream().filter(visited.negate()).forEach(addToCache);
            g1.neighbors.forEach(cloneNeighbour);
            g1.neighbors.forEach(markVisited);
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