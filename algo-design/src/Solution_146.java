import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ====================== Design question ===================
 * https://leetcode.com/problems/lru-cache/
 * https://app.diagrams.net/#DLRU%20Cache.drawio
 */
class Solution_146 {


    class LRUCache {

        static class Node {
            int key;
            int val;
            Node prev;
            Node next;

            public Node(int key, int val, Node prev, Node next) {
                this.key = key;
                this.val = val;
                this.prev = prev;
                this.next = next;
            }
        }

        class DoublyLinkedList {

            private Node head;
            private Node tail;

            Node addFront(int key, int value) {
                if (this.head == null) {
                    this.head = new Node(key, value, null, null);
                    this.tail = this.head;
                } else {
                    Node curr = new Node(key, value, null, head);
                    curr.next = this.head;
                    this.head.prev = curr;
                    this.head = curr;
                }
                return this.head;
            }

            Node removeLast() {
                if (this.tail != null) {
                    Node curr = this.tail;
                    Node prev = curr.prev;
                    if (prev == null) {
                        this.head = null;
                        this.tail = null;
                    } else {
                        curr.prev = null;
                        prev.next = null;
                        this.tail = prev;
                    }
                    return curr;
                }
                return null;
            }

            void moveFront(Node curr) {
                if (curr == null) {
                    return;
                }
                Node prev = curr.prev;
                Node next = curr.next;
                if (prev == null) {
                    return;
                } else if (next == null) {
                    prev.next = null;
                    this.tail = this.tail.prev;
                } else {
                    prev.next = next;
                    next.prev = prev;
                }
                this.head.prev = curr;
                curr.prev = null;
                curr.next = this.head;
                this.head = curr;
            }
        }

        private final int capacity;
        private final Map<Integer, Node> cache;
        private final DoublyLinkedList list;

        public LRUCache(int capacity) {
            this.cache = new HashMap<>();
            this.list = new DoublyLinkedList();
            this.capacity = capacity;
        }

        public int get(int key) {
            if (this.cache.containsKey(key)) {
                Node curr = this.cache.get(key);
                this.list.moveFront(curr);
                return curr.val;
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (this.cache.containsKey(key)) {
                Node curr = this.cache.get(key);
                curr.val = value;
                this.list.moveFront(curr);
            } else {
                if (this.cache.size() == this.capacity) {
                    Node removed = this.list.removeLast();
                    this.cache.remove(removed.key);
                }
                Node curr = this.list.addFront(key, value);
                this.cache.put(key, curr);
            }
        }
    }

    static class LRUCache_2 {

        int capacity;
        Map<Integer, Integer> keyValueMap;
        Set<Integer> linkedHashSet;

        public LRUCache_2(int capacity) {
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

        private final Map<Integer, DLinkedNode> cache = new HashMap<>();
        private final int capacity;
        private final DLinkedNode head;
        private final DLinkedNode tail;
        private int count;

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
    }

    static class LRUCache2 extends LinkedHashMap<Integer, Integer> {

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
