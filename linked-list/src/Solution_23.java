class Solution_23 {

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

    /**
     * Use the same approach as used to merge 2 linked list
     * Pick the minimum and increase the pointer to the minimum
     * Time complexity : O(N*K)
     */
    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        ListNode[] itr = new ListNode[n];
        ListNode head = new ListNode(-1);
        ListNode curr = head;
        System.arraycopy(lists, 0, itr, 0, n);
        int minList;
        do {
            minList = -1;
            for (int i = 0; i < n; i++) {
                if (itr[i] != null && (minList == -1 || itr[i].val < itr[minList].val)) {
                    minList = i;
                }
            }
            if (minList != -1) {
                curr.next = itr[minList];
                curr = curr.next;
                itr[minList] = itr[minList].next;
            }
        } while (minList != -1);
        return head.next;
    }


    /**
     * it merges the two sorted list
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null)
            return l1 != null ? l1 : l2;

        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;

        ListNode c1 = l1;
        ListNode c2 = l2;

        while (c1 != null && c2 != null) {
            if (c1.val <= c2.val) {
                prev.next = c1;
                c1 = c1.next;
            } else {
                prev.next = c2;
                c2 = c2.next;
            }
            prev = prev.next;
        }

        prev.next = (c1 != null ? c1 : c2);

        return dummy.next;
    }

    /**
     * we use divide and conquer approach, we apply the merge Klist on the first and second half then merge them resulted linked list using merge 2
     * Base case : k = 1 then there is no need merge
     * Time Complexity : O(N*Log(k))
     */
    public static ListNode mergeKLists(ListNode[] lists, int si, int ei) {
        if (si == ei)
            return lists[si];
        int mid = (si + ei) / 2;
        return mergeTwoLists(mergeKLists(lists, si, mid), mergeKLists(lists, mid + 1, ei));
    }

    public static ListNode mergeKLists2(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        return mergeKLists(lists, 0, lists.length - 1);
    }
}