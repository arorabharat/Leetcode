###

## Business Requirement
1. person outside the elevator could press up and down button 
2. person inside the elevator could press the floor number on which person wants to go
3. There is maximum weight limit for the elevator
4. we need a solution to optimally assign lift to the people
5. There are N elevator in the building and each has up/down button
6. if lift is at the ground floor , it has been requested by the person at 6th and 3rd floor . Lift should go to 6th floor first and Elevator shouldn't go in the opposite direction , otherwise they will get confused 
7. First come first request
8. Starvation of resources should not be there

## Solution 
If each elevator has the separate button then the problem is simple.  
it should serve the request on first come first basis.  
assuming somebody on the 2nd floor press down and elevator is going up then it should keep going up serve the client and then pick the next request to go down.  
if lets say during going up it receive one more request to go up, it should server that as well and then server the down request.  
So we could priority queue to solve this kind of design  
if life is going up:  
case 1 : we get request for higher floor, place in the min queue  
case 2 : we get request for lower floor, place in the temporary set   
if lift is going down:  
case 3 : we get request for higher floor, place in the temporary set  
case 4 : we get request for lower floor, place in the max queue  
if lift is standing do choose the direction required for first request :  
once all the request are severed from the min / max queue, build the queue again from the temp set and repeat the process until temp set is not empty.

## Complex solution 
Assuming multiple lift have common up and down button and we could server the rquest from any of them.

lets say X lifts are going int he up direciton.
Y lifts are going in the down direction.
and Z lifts are standing.

if the requested 

Then we should check in which lifts 
 

