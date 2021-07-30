https://leetcode.com/discuss/general-discussion/458695/Dynamic-Programming-Patterns

https://leetcode.com/discuss/general-discussion/1000929/solved-all-dynamic-programming-dp-problems-in-7-months

https://www.youtube.com/watch?v=N7e4CTfimkU&t=1s

https://cses.fi/problemset/

Coreman
Chapter 15

# Algorithm

1. Knapsack problem
2. Edit distance
3. Coin change
4. Longest common subsequence (LCS)
5. Largest subset sum
6. Longest increasing subsequence
7. Longest palindrome
8. Word break problem
9. Longest common substring
10. Buy and sell stock
11. kadane's algorithm
12. Minimum jumps to reach the end
13. Equal sum partition
14. Target sum 15 matrix chain multiplication


## Categories

1. Knapsack
   a. Bounded
   b. Unbounded
   c. 0-1
2. LCS
3. LIS
4. Matrix chain multiplication
5. DP on trees
6. DP on grid
7. Kadane's algorithm
8. DP on kth lexigraphical string 
9. DP + Segment tree
10. DP + Binary search 
11. Dp on


### Bounded Knapsack
Only one instance of the item could be added from the available list.  
Given : Price (Pi) , Weight (Wi) of n item and Max weight a bag can hold (WB)  
Find : What is the maximum worth of item we can put in the bag.  
f(n,w) = Max of ( include the nth item, do not include the nth item )  
f(n,w) = max(Pn + f(n-1,w-Wn) , f(n-1,w) )  
As we can see there are overlapping sub problems hence we need to use dynamic programming.  
Last part f the equation is to find the base case.  

youtube : https://www.youtube.com/watch?v=ReYPIilhrIo&list=PLEJXowNB4kPxBwaXtRO1qFLpCzF75DYrS&index=3

## Unbounded knapsack