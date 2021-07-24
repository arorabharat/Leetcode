# Linked List

https://leetcode.com/tag/linked-list/

# Premium Questions

https://leetcode.com/problems/remove-duplicates-from-an-unsorted-linked-list/


## Doubly linked list

### Addition

![add node](../.././resources/add_doubly_linked_list.png)
```
E.prev = B
E.next = B.next
B.next = E
C.prev = E
``` 
## Removal
![add node](../.././resources/remove_double_linked_list.png)
```
Y.prev.next = Y.next
Y.next.prev = Y.prev
```
