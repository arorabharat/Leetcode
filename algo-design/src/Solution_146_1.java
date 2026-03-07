import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ====================== Design question ===================
 * https://leetcode.com/problems/lru-cache/
 * https://app.diagrams.net/#DLRU%20Cache.drawio
 */

/**
 * ["LRUCache","put", "put", "get","put", "get","put",  "get", "get","get"]
 * [[2],        [1,1], [2,2], [1], [3,3], [2],  [4,4],  [1],   [3],  [4]]
 * <p>
 * 3-> 1 -> 2
 * [null,null,null,1,null,2,null,1,3,4]
 * [null,null,null,1,null,-1,null,-1,3,4]
 * <p>
 * ["LRUCache","put","put","get","put","get","put","get","get","get"]
 * [[2],[1,0],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
 * 4 ->3 -> -
 * [null,null,null,0,null,-1,null,0,3,4]
 * [null,null,null,0,null,-1,null,-1,3,4]
 * <p>
 * put
 * 1 0
 * =================
 * 1 0
 * put
 * 2 2
 * =================
 * 1 0
 * 2 2
 * get
 * 1
 * 0
 * put
 * 3 3
 * 2
 * =================
 * 1 0
 * 3 3
 * get
 * 2
 * put
 * 4 4
 * 0
 * =================
 * 4 4
 * 1 0
 * 3 3
 * get
 * 1
 * 0
 * get
 * 3
 * 3
 * get
 * 4
 * 4
 */
class Solution_146_1 {


    class Node {

        Node next;
        Node prev;
        int val;
        int key;

        public Node(int key, int val) {
            this.val = val;
            this.key = key;
        }
    }

    class DoublyLinkedList {

        private final Node head = new Node(-1, -1);
        private final Node tail = new Node(-1, -1);

        public DoublyLinkedList() {
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        Node getLast() {
            Node prev = this.tail.prev;
            return (prev == head) ? null : prev;
        }

        // P <-> C <-> N;
        // P <-> N;
        void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
        }

        // H <-> T
        // H <-> C <-> T
        void addFront(Node node) {

            Node headNext = this.head.next;

            this.head.next = node;
            node.prev = this.head;

            node.next = headNext;
            headNext.prev = node;
        }

        void moveFront(Node node) {
            if (node.prev != head) {
                removeNode(node);
                addFront(node);
            }
        }

        Node addNewNodeFront(int key, int val) {
            Node node = new Node(key, val);
            addFront(node);
            return node;
        }
    }


    class LRUCache {

        private final Map<Integer, Node> keyValue;
        private final DoublyLinkedList accessQueue;
        private final int capacity;

        public LRUCache(int capacity) {
            this.keyValue = new HashMap<>(capacity);
            accessQueue = new DoublyLinkedList();
            this.capacity = capacity;
        }


        public int get(int key) {
            if (this.keyValue.containsKey(key)) {
                Node node = this.keyValue.get(key);
                accessQueue.moveFront(node);
                return node.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (this.keyValue.containsKey(key)) {
                Node node = this.keyValue.get(key);
                node.val = value;
                accessQueue.moveFront(node);
            } else {

                if (this.keyValue.size() == this.capacity) {
                    Node last = accessQueue.getLast();
                    accessQueue.removeNode(last);
                    this.keyValue.remove(last.key);
                }

                Node node = accessQueue.addNewNodeFront(key, value);
                this.keyValue.put(key, node);
            }
        }
    }
}
