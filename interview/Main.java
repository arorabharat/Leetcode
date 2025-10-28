// Caching (LRU Caching) with transactions
// get(key)
// put(key, val)
// 10, 1

// capactiy = 5

// k = 1 10
// transactions
// begin()
// k = 1, 15;
// put(k, v)
// put(k, v)
// put()
// get(k)
// k = 1 -
// commit()


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Node<K, V> {
    private K key;
    private V value;
    private Node<K,V> prev;
    private Node<K,V> next;


    public Node() {
    }

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setPrev(Node<K,V> prev) {
        this.prev = prev;
    }

    public void setNext(Node<K,V> next) {
        this.next = next;
    }

    public Node<K,V> getPrev() {
        return this.prev;
    }

    public Node<K, V> getNext() {
        return this.next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

class DoublyLinkedList<K, V> {
    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    // H <-> T ;
    public DoublyLinkedList() {
        this.head = new Node<K,V>();
        this.tail = new Node<K,V>();
        this.head.setNext(tail);
        this.tail.setPrev(head);
        this.size = 0;
    }

    // P <-> n <-> N
    // P <-> N;
    public Node<K,V> remove(Node<K,V> node) {
        Node<K,V> prev = node.getPrev();
        Node<K,V> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        return node;
    }

    // H <-> T ;
    // H <-> N <-> T ;
    // H <-> N <-> F .... <-> T ;
    public Node<K,V> addFirst(Node<K, V> node) {
        Node<K,V> firstEle = this.head.getNext();
        this.head.setNext(node);
        node.setPrev(head);
        node.setNext(firstEle);
        firstEle.setPrev(node);
        this.size++;
        return node;
    }

    // H <-> T ;
    // H <-> N <-> T ;
    // H <-> N <-> .... L <-> T ;
    // H <-> N <-> ....LP <-> T ;
    // H <-> T
    //public Node removeFirst() {
    // }

    public Node<K,V> removeLast() {
        Node<K,V> lastEle = this.tail.getPrev();
        if (lastEle == this.head) {
            return null;
        }
        lastEle.getPrev().setNext(tail);
        this.size--;
        return lastEle;
    }

}


class Operation<K, V> {
    String operation; // this can be enum
    K key;
    V value;

    public Operation(String op, K key, V value) {
        this.operation = op;
        this.key = key;
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

class Cache<K, V> {

    private final Map<K, Node<K, V>> keyMap;
    private final DoublyLinkedList<K, V> accessq;
    private Map<K, V> tempMap;
    private ArrayList<Operation<K,V>> operationList;
    boolean begin;

    private final int capacity;

    public Cache(int capactiy) {
        this.capacity = capactiy;
        this.keyMap = new HashMap<>(capactiy);
        this.accessq = new DoublyLinkedList<>();
        this.tempMap = new HashMap<>();
        this.operationList = new ArrayList<>();
    }

    public int size() {
        return this.keyMap.size();
    }

    public void begin() {
        this.begin = true;
        this.tempMap = new HashMap<>();
        this.operationList = new ArrayList<>();
    }

    public void put(K key, V value) {

        this.tempMap.put(key, value);
        Operation<K, V> op = new Operation<K,V>("PUT", key, value);
        this.operationList.add(op);
    }

    public V get(K key) {
        Operation<K, V> op = new Operation<K,V>("GET", key, null);
        this.operationList.add(op);
        return this.tempMap.get(key);
    }

    public boolean commit() {
        for (Operation<K,V> op : operationList) {
            if ("PUT".equals(op.getOperation())) {
                int size = this.keyMap.size();
                K key = op.getKey();
                V value = op.getValue();
                if (size == this.capacity) {
                    Node<K,V> removedElement = this.accessq.removeLast();
                    this.keyMap.remove(removedElement.getKey());
                }
                Node<K, V> node = new Node<K,V>(key, value);
                this.accessq.addFirst(node);
                this.keyMap.put(key, node);

            } else if ("GET".equals(op.getOperation())) {
                K key = op.getKey();
                Node<K,V> keyNode = this.keyMap.get(key);
                if (keyNode != null) {
                    this.accessq.remove(keyNode);
                    this.accessq.addFirst(keyNode);
                }
            }
        }
        this.tempMap = new HashMap<>();
        return true;
    }
}


public class Main {
    public static void main(String[] args) {
        Cache<Integer, Integer> cache = new Cache<Integer,Integer>(5);
        cache.begin();
        for (int i = 1; i <= 10; i++) {
            cache.put(i, i);
        }
        cache.commit();
        // int size = this.cache.size();
        for (int i = 1; i <= 10; i++) {
            System.out.println(cache.get(i));
        }

    }
}
