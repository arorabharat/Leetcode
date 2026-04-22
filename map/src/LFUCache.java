import java.util.*;

class LFUCache {

    static class Node {
        int key, val, freq;
        long time;
        boolean valid; // for lazy deletion

        Node(int key, int val, int freq, long time) {
            this.key = key;
            this.val = val;
            this.freq = freq;
            this.time = time;
            this.valid = true;
        }
    }

    private final int capacity;
    private long timestamp = 0;

    private final Map<Integer, Node> map;
    private final PriorityQueue<Node> minHeap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        this.minHeap = new PriorityQueue<>((a, b) -> {
            if (a.freq != b.freq) return a.freq - b.freq;
            return Long.compare(a.time, b.time);
        });
    }

    // ===================== GET =====================
    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        Node oldNode = map.get(key);
        oldNode.valid = false; // invalidate old version

        Node newNode = new Node(
                key,
                oldNode.val,
                oldNode.freq + 1,
                ++timestamp
        );

        map.put(key, newNode);
        minHeap.add(newNode);

        return newNode.val;
    }

    // ===================== PUT =====================
    public void put(int key, int value) {
        if (capacity == 0) return;

        if (map.containsKey(key)) {
            Node oldNode = map.get(key);
            oldNode.valid = false;

            Node newNode = new Node(
                    key,
                    value,
                    oldNode.freq + 1,
                    ++timestamp
            );

            map.put(key, newNode);
            minHeap.add(newNode);
            return;
        }

        // Evict if needed
        if (map.size() == capacity) {
            evict();
        }

        Node node = new Node(key, value, 1, ++timestamp);
        map.put(key, node);
        minHeap.add(node);
    }

    // ===================== EVICTION =====================
    private void evict() {
        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();

            // skip stale nodes
            if (!node.valid) continue;

            map.remove(node.key);
            return;
        }
    }
}