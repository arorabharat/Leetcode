
## Binary Search
1. Element may or may not exist
2. Element exist
3. Multiple instance of the element exist
4. If element do not exist then return the first element lower than given than current element (upper bound)
4. If element do not exist then return the first element greater than given than current element (lower bound)


## Mid strategy
```
int mid = low + (high - low) / 2;
```
0 1 2 3 4 5  
(0,5) -> mid = 0 + 2 = 2, on even number it will give left mid  
(1,5) -> mid = 1 + 2 = 3 , on odd number it will give the one and only mid  
So this mid will never overlap with the high. If minimum array size is 2 i.e., low < high
```
int mid = low + (high + 1 - low) / 2;
```
0 1 2 3 4 5  
(0,5) -> mid = 0 + 3 = 3, on even number it will give right mid  
(1,5) -> mid = 1 + 2 = 3 , on odd number it will give the one and only mid  
So this mid will never overlap with the low. If minimum array size is 2 i.e., low < high

## Thumb rule of binary search
1. Ensure that after every iteration size of the interval would reduce
2. If high = mid then mid should not overlap with the high. 
3. If low = mid then mid should not overlap with the low.

## TODO
https://leetcode.com/discuss/interview-question/351782/Google-or-Phone-Screen-or-Kth-Largest-Element-of-Two-Sorted-Arrays

