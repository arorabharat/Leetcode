/**
 * Implementation of a Circular Linked List data structure.
 * In a circular linked list, the last node points back to the first node,
 * forming a circular structure.
 */
class CircularLinkedListImpl {
    
    /**
     * Inner class representing a node in the circular linked list.
     */
    private static class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;
    private Node tail;
    private int size;
    
    /**
     * Constructor to initialize an empty circular linked list.
     */
    public CircularLinkedListImpl() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Adds an element to the end of the circular linked list.
     * 
     * @param data The data to be added
     */
    public void add(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            // First node - make it point to itself
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            // Add to the end and update tail
            tail.next = newNode;
            tail = newNode;
            tail.next = head; // Maintain circular structure
        }
        size++;
    }
    
    /**
     * Adds an element at the beginning of the circular linked list.
     * 
     * @param data The data to be added
     */
    public void addFirst(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            newNode.next = head;
            head = newNode;
            tail.next = head; // Maintain circular structure
        }
        size++;
    }
    
    /**
     * Adds an element at a specific index.
     * 
     * @param index The index at which to add the element
     * @param data The data to be added
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public void add(int index, int data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            addFirst(data);
            return;
        }
        
        if (index == size) {
            add(data);
            return;
        }
        
        Node newNode = new Node(data);
        Node current = head;
        
        // Traverse to the node before the insertion point
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }
    
    /**
     * Removes the first occurrence of the specified element.
     * 
     * @param data The data to be removed
     * @return true if the element was found and removed, false otherwise
     */
    public boolean remove(int data) {
        if (head == null) {
            return false;
        }
        
        // Case 1: Remove head node
        if (head.data == data) {
            if (size == 1) {
                // Only one node
                head = null;
                tail = null;
            } else {
                head = head.next;
                tail.next = head; // Maintain circular structure
            }
            size--;
            return true;
        }
        
        // Case 2: Remove node in the middle or end
        Node current = head;
        do {
            if (current.next.data == data) {
                if (current.next == tail) {
                    // Removing tail node
                    tail = current;
                }
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        } while (current != head);
        
        return false;
    }
    
    /**
     * Removes the element at the specified index.
     * 
     * @param index The index of the element to be removed
     * @return The data of the removed element
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public int removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            int data = head.data;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                tail.next = head;
            }
            size--;
            return data;
        }
        
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        int data = current.next.data;
        if (current.next == tail) {
            tail = current;
        }
        current.next = current.next.next;
        size--;
        return data;
    }
    
    /**
     * Returns the element at the specified index.
     * 
     * @param index The index of the element
     * @return The data at the specified index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    /**
     * Checks if the circular linked list contains the specified element.
     * 
     * @param data The data to search for
     * @return true if the element is found, false otherwise
     */
    public boolean contains(int data) {
        if (head == null) {
            return false;
        }
        
        Node current = head;
        do {
            if (current.data == data) {
                return true;
            }
            current = current.next;
        } while (current != head);
        
        return false;
    }
    
    /**
     * Returns the index of the first occurrence of the specified element.
     * 
     * @param data The data to search for
     * @return The index of the element, or -1 if not found
     */
    public int indexOf(int data) {
        if (head == null) {
            return -1;
        }
        
        int index = 0;
        Node current = head;
        do {
            if (current.data == data) {
                return index;
            }
            current = current.next;
            index++;
        } while (current != head);
        
        return -1;
    }
    
    /**
     * Returns the size of the circular linked list.
     * 
     * @return The number of elements in the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the circular linked list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Removes all elements from the circular linked list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * Returns a string representation of the circular linked list.
     * The list is displayed from head to tail (one full cycle).
     * 
     * @return String representation of the list
     */
    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node current = head;
        do {
            sb.append(current.data);
            if (current.next != head) {
                sb.append(", ");
            }
            current = current.next;
        } while (current != head);
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Displays the circular linked list (for debugging purposes).
     */
    public void display() {
        System.out.println(this.toString());
    }
}
