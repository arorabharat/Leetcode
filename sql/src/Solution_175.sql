# https://leetcode.com/problems/combine-two-tables/

SELECT P.FirstName, P.LastName, A.City, A.State
FROM Person AS P
         LEFT JOIN Address AS A
                   ON P.PersonId = A.PersonId
