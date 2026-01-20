import java.util.*;

public class Solution_432 {
    class AllOne {

        TreeMap<Integer, Set<String>> sortedKeys = new TreeMap<>();
        Map<String, Integer> count = new HashMap<>();

        public AllOne() {
        }

        private void updateTreeMap(TreeMap<Integer, Set<String>> map, int prev, int next, String key) {

            if (prev > 0 && map.containsKey(prev)) {
                map.get(prev).remove(key);
                if (map.get(prev).isEmpty()) {
                    map.remove(prev);
                }
            }

            if (next > 0) {
                if (!map.containsKey(next)) {
                    map.put(next, new HashSet<>());
                }
                map.get(next).add(key);
            }

        }

        public void inc(String key) {
            if (count.containsKey(key)) {
                count.put(key, count.get(key) + 1);
            } else {
                count.put(key, 1);
            }
            int next = count.get(key);
            updateTreeMap(sortedKeys, next - 1, next, key);
        }

        public void dec(String key) {
            if (count.containsKey(key)) {
                int prev = count.get(key);
                if (count.get(key) > 1) {
                    count.put(key, prev - 1);
                } else {
                    count.remove(key);
                }
                updateTreeMap(sortedKeys, prev, prev - 1, key);
            }

        }

        public String getMaxKey() {
            return sortedKeys.isEmpty() ? "" : sortedKeys.lastEntry().getValue().iterator().next();
        }

        public String getMinKey() {
            return sortedKeys.isEmpty() ? "" : sortedKeys.firstEntry().getValue().iterator().next();
        }
    }

    class AllOne_2 {

        // count -> set of keys
        private final TreeMap<Integer, Set<String>> freqMap = new TreeMap<>();
        // key -> count
        private final Map<String, Integer> keyCount = new HashMap<>();

        public AllOne_2() {
        }

        /* ---------- Helpers ---------- */

        private void removeFromBucket(int freq, String key) {
            Set<String> keys = freqMap.get(freq);
            if (keys == null) return;
            keys.remove(key);
            if (keys.isEmpty()) {
                freqMap.remove(freq);
            }
        }

        private void addToBucket(int freq, String key) {
            if (freq <= 0) return;
            freqMap.computeIfAbsent(freq, f -> new HashSet<>())
                    .add(key);
        }

        private void moveKey(String key, int prev, int next) {
            removeFromBucket(prev, key);
            addToBucket(next, key);
        }

        /* ---------- Operations ---------- */

        public void inc(String key) {
            int prev = keyCount.getOrDefault(key, 0);
            int next = prev + 1;
            keyCount.put(key, next);
            moveKey(key, prev, next);
        }

        public void dec(String key) {
            Integer prev = keyCount.getOrDefault(key, 0);
            if (prev == 0) return;
            int next = prev - 1;
            if (next == 0) {
                keyCount.remove(key);
            } else {
                keyCount.put(key, next);
            }
            moveKey(key, prev, next);
        }

        public String getMaxKey() {
            if (freqMap.isEmpty()) return "";
            return freqMap.lastEntry().getValue().iterator().next();
        }

        public String getMinKey() {
            if (freqMap.isEmpty()) return "";
            return freqMap.firstEntry().getValue().iterator().next();
        }
    }

    class AllOne_3 {


        class Node {
            Node prev;
            Node next;
            int count;
            Set<String> keys = new HashSet<>();

            public Node(Node prev, Node next, int count) {
                this.prev = prev;
                this.next = next;
                this.count = count;
            }
        }


        class DoublyList {
            private final Node head = new Node(null, null, 0);
            private final Node tail = new Node(null, null, 0);

            public DoublyList() {
                this.head.next = tail;
                this.tail.prev = head;
            }


            Node decrementKeyCount(Node node, String key) {
                Node newNode = null;
                if (node.count > 1) {
                    if (node.prev.count == node.count - 1) {
                        newNode = node.prev;
                    } else {
                        newNode = insertNodeAfterCurr(node.prev, node.count - 1);
                    }
                    newNode.keys.add(key);
                }
                removeKeyFromNode(node, key);
                return newNode;
            }


            Node incrementKeyCount(Node node, String key) {
                node = (node == null) ? head : node;
                Node newNode;
                if (node.next.count == node.count + 1) {
                    newNode = node.next;
                } else {
                    newNode = insertNodeAfterCurr(node, node.count + 1);
                }
                newNode.keys.add(key);
                if (node != head) {
                    removeKeyFromNode(node, key);
                }
                return newNode;
            }

            private void removeKeyFromNode(Node node, String key) {
                node.keys.remove(key);
                if (node.keys.isEmpty()) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
            }

            private Node insertNodeAfterCurr(Node node, int count) {
                Node newNode = new Node(node, node.next, count);
                node.next.prev = newNode;
                node.next = newNode;
                return newNode;
            }

            boolean isEmpty() {
                return head.next == tail;
            }
        }

        private final Map<String, Node> keyCount = new HashMap<>();
        private final DoublyList doublyList = new DoublyList();

        public AllOne_3() {
        }


        /* ---------- Operations ---------- */

        public void inc(String key) {
            Node next = doublyList.incrementKeyCount(keyCount.get(key), key);
            keyCount.put(key, next);
        }

        public void dec(String key) {
            Node prev = keyCount.get(key);
            if (prev != null) {
                Node next = doublyList.decrementKeyCount(prev, key);
                if (next != null) {
                    keyCount.put(key, next);
                } else {
                    keyCount.remove(key);
                }
            }
        }

        public String getMaxKey() {
            if (doublyList.isEmpty()) return "";
            return doublyList.tail.prev.keys.iterator().next();

        }

        public String getMinKey() {
            if (doublyList.isEmpty()) return "";
            return doublyList.head.next.keys.iterator().next();
        }
    }

    class AllOne_4 {

        /* =======================
           Bucket Node
           ======================= */
        private static class Bucket {
            int count;
            Set<String> keys = new HashSet<>();
            Bucket prev, next;

            Bucket(int count) {
                this.count = count;
            }
        }

        /* =======================
           Doubly Linked Bucket List
           ======================= */
        private static class BucketList {
            private final Bucket head = new Bucket(0);
            private final Bucket tail = new Bucket(0);

            BucketList() {
                head.next = tail;
                tail.prev = head;
            }

            boolean isEmpty() {
                return head.next == tail;
            }

            /* ---------- Increment ---------- */
            Bucket increment(Bucket curr, String key) {
                if (curr == null) {
                    curr = head; // treat new key as coming from count 0
                }

                int newCount = curr.count + 1;
                Bucket target = (curr.next.count == newCount)
                        ? curr.next
                        : insertAfter(curr, newCount);

                target.keys.add(key);

                if (curr != head) {
                    removeKey(curr, key);
                }

                return target;
            }

            /* ---------- Decrement ---------- */
            Bucket decrement(Bucket curr, String key) {
                int newCount = curr.count - 1;
                Bucket target = null;

                if (newCount > 0) {
                    target = (curr.prev.count == newCount)
                            ? curr.prev
                            : insertAfter(curr.prev, newCount);

                    target.keys.add(key);
                }

                removeKey(curr, key);
                return target; // null means count reached zero
            }

            /* ---------- Helpers ---------- */
            private void removeKey(Bucket bucket, String key) {
                bucket.keys.remove(key);

                if (bucket.keys.isEmpty()) {
                    bucket.prev.next = bucket.next;
                    bucket.next.prev = bucket.prev;
                }
            }

            private Bucket insertAfter(Bucket prev, int count) {
                Bucket node = new Bucket(count);
                node.prev = prev;
                node.next = prev.next;

                prev.next.prev = node;
                prev.next = node;

                return node;
            }
        }

        /* =======================
           AllOne_4 Data Structure
           ======================= */
        private final Map<String, Bucket> keyToBucket = new HashMap<>();
        private final BucketList buckets = new BucketList();

        public AllOne_4() {
        }

        public void inc(String key) {
            Bucket curr = keyToBucket.get(key);
            Bucket next = buckets.increment(curr, key);
            keyToBucket.put(key, next);
        }

        public void dec(String key) {
            Bucket curr = keyToBucket.get(key);
            if (curr == null) return;

            Bucket next = buckets.decrement(curr, key);

            if (next == null) {
                keyToBucket.remove(key); // count reached zero
            } else {
                keyToBucket.put(key, next);
            }
        }

        public String getMaxKey() {
            return buckets.isEmpty()
                    ? ""
                    : buckets.tail.prev.keys.iterator().next();
        }

        public String getMinKey() {
            return buckets.isEmpty()
                    ? ""
                    : buckets.head.next.keys.iterator().next();
        }
    }


/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */


/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
}
