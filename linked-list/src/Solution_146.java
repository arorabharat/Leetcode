import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Solution_146 {

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    static class LRUCache {

        int capacity;
        Map<Integer, Integer> keyValueMap;
        Set<Integer> linkedHashSet;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.keyValueMap = new ConcurrentHashMap<>();
            this.linkedHashSet = new LinkedHashSet<>();
        }

        public int get(int key) {
            if (this.keyValueMap.containsKey(key)) {
                this.linkedHashSet.remove(key);
                this.linkedHashSet.add(key);
                return keyValueMap.get(key);
            }
            return -1;
        }

        public void put(int key, int value) {
            if (this.keyValueMap.containsKey(key)) {
                this.keyValueMap.put(key, value);
                this.linkedHashSet.remove(key);
                this.linkedHashSet.add(key);
            } else if (this.keyValueMap.size() == this.capacity) {
                Integer leastAccessedKey = this.linkedHashSet.iterator().next();
                this.linkedHashSet.remove(leastAccessedKey);
                this.keyValueMap.remove(leastAccessedKey);
                this.keyValueMap.put(key, value);
                this.linkedHashSet.add(key);
            } else {
                this.keyValueMap.put(key, value);
                this.linkedHashSet.add(key);
            }
        }
    }

    static class LRUCache1 {

        static class DLinkedNode {
            int key;
            int value;
            DLinkedNode prev;
            DLinkedNode next;

            public DLinkedNode(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private void addNode(DLinkedNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(DLinkedNode node) {
            DLinkedNode prev = node.prev;
            DLinkedNode next = node.next;
            prev.next = next;
            next.prev = prev;
        }

        private void moveToHead(DLinkedNode node) {
            this.removeNode(node);
            this.addNode(node);
        }

        private DLinkedNode popTail() {
            DLinkedNode res = tail.prev;
            this.removeNode(res);
            return res;
        }

        private final Map<Integer, DLinkedNode> cache = new HashMap<>();
        private int count;
        private final int capacity;
        private final DLinkedNode head;
        private final DLinkedNode tail;

        public LRUCache1(int capacity) {
            this.count = 0;
            this.capacity = capacity;
            head = new DLinkedNode(-1, -1);
            tail = new DLinkedNode(-1, -1);
            head.prev = null;
            head.next = tail;
            tail.prev = head;
            tail.next = null;
        }

        public int get(int key) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            this.moveToHead(node);
            return node.value;
        }


        public void put(int key, int value) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                DLinkedNode newNode = new DLinkedNode(key, value);
                this.cache.put(key, newNode);
                this.addNode(newNode);
                ++count;
                if (count > capacity) {
                    DLinkedNode tail = this.popTail();
                    this.cache.remove(tail.key);
                    --count;
                }
            } else {
                node.value = value;
                this.moveToHead(node);
            }
        }
    }

    static class LRUCache2 extends LinkedHashMap<Integer, Integer>{

        private final int capacity;

        public LRUCache2(int capacity) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
}
