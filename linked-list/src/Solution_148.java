class Solution_148 {

    private ListNode insertInSortedList(ListNode head, ListNode node) throws IllegalArgumentException {
        if (node.next != null) {
            throw new IllegalArgumentException("Node's next value should be null");
        }
        if (head == null) {
            head = node;
        } else {
            ListNode pseudoNode = new ListNode(Integer.MIN_VALUE);
            pseudoNode.next = head;
            ListNode prev = pseudoNode;
            ListNode curr = head;
            while (curr != null) {
                if (curr.val < node.val) {
                    prev = curr;
                    curr = curr.next;
                } else {
                    prev.next = node;
                    node.next = curr;
                    break;
                }
            }
            ListNode insertedHead = pseudoNode.next;
            pseudoNode.next = null;
            return insertedHead;
        }
        return head;
    }

    public ListNode sortList(ListNode head) {
        if (head == null) return head;
        ListNode sorted = head;
        ListNode curr = head.next;
        head.next = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = null;
            sorted = insertInSortedList(sorted, curr);
            curr = next;
        }
        return sorted;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}