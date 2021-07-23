# https://leetcode.com/problems/duplicate-emails/

SELECT B.Email
FROM (
         SELECT Email, COUNT(Email) AS freq
         FROM Person
         GROUP BY Email
     ) AS B
WHERE B.freq > 1;

# Approach 2 : Optimizing on the previous approach

SELECT Email
FROM Person
GROUP BY Email
HAVING COUNT(Email) > 1;

