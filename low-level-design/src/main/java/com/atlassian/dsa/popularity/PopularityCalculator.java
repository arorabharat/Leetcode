package com.atlassian.dsa.popularity;

import java.util.HashMap;
import java.util.Map;

public class PopularityCalculator {

    interface MostPopular {

        void increasePopularity(Integer contentId);

        Integer mostPopular();

        void decreasePopularity(Integer contentId);
    }

    static class MostPopularImplementation implements MostPopular {

        static class Node {

            Node prev;
            Node next;
            int val;
            int cid;

            public Node(int val, int cid) {
                this.val = val;
                this.cid = cid;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "val=" + val +
                        ", cid=" + cid +
                        '}';
            }
        }

        static class DoublyLinkedList {
            private final Node head = new Node(Integer.MAX_VALUE, -1);
            private final Node tail = new Node(Integer.MIN_VALUE, -1);

            public DoublyLinkedList() {
                linkNodes(head, tail);
            }

            private void linkNodes(Node n1, Node n2) {
                n1.next = n2;
                n2.prev = n1;
            }

            void insertLast(Node node) {
                linkNodes(this.tail.prev, node);
                linkNodes(node, this.tail);
            }

            // p -> n1 -> n2 -> n3
            // p -> n2 -> n1 -> n3
            void swapNode(Node p, Node n1, Node n2, Node n) {
                linkNodes(p, n2);
                linkNodes(n2, n1);
                linkNodes(n1, n);
            }

            void bubbleUp(Node node) {
                if (node == head || node == tail || node == null) {
                    return;
                }
                while (node.prev != head && node.val > node.prev.val) {
                    // pp -> p -> node -> n  == pp -> node -> p -> n
                    // H -> p -> node -> n  == H -> node -> p -> n
                    swapNode(node.prev.prev, node.prev, node, node.next);
                }
            }

            // p -> n1 -> n2 -> n
            void bubbleDown(Node node) {
                if (node == head || node == tail || node == null) {
                    return;
                }
                while (node.next != tail && node.val < node.next.val) {
                    // p -> node -> n -> nn == p -> n -> node -> nn
                    // p -> node -> n -> T == p -> n -> node -> T
                    swapNode(node.prev, node, node.next, node.next.next);
                }
            }

            void remove(Node node) {
                if (node == head || node == tail || node == null) {
                    return;
                }
                Node p = node.prev;
                Node n = node.next;
                linkNodes(p, n);
            }

            int getMostPopularContentId() {
                return this.head.next.cid;
            }

            void print() {
                Node tr = this.head;
                while (tr.next != tail) {
                    tr = tr.next;
                    System.out.print(tr + "->");
                }
                System.out.println();
            }
        }

        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();
        Map<Integer, Node> contentCount = new HashMap<>();

        @Override
        public void increasePopularity(Integer contentId) {
            Node node;
            if (contentCount.containsKey(contentId)) {
                node = contentCount.get(contentId);
                node.val++;
                doublyLinkedList.bubbleUp(node);
            } else {
                node = new Node(1, contentId);
                contentCount.put(contentId, node);
                doublyLinkedList.insertLast(node);
            }
            doublyLinkedList.print();
        }

        @Override
        public Integer mostPopular() {
            return doublyLinkedList.getMostPopularContentId();
        }

        @Override
        public void decreasePopularity(Integer contentId) {
            if (contentCount.containsKey(contentId)) {
                Node node = contentCount.get(contentId);
                node.val--;
                if (node.val == 0) {
                    doublyLinkedList.remove(node);
                } else {
                    doublyLinkedList.bubbleDown(node);
                }
            }
            doublyLinkedList.print();
        }
    }
}

