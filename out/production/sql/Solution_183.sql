# https://leetcode.com/problems/customers-who-never-order/
# where column = null is incorrect syntax, instead use where column is NULL
SELECT C.Customers
FROM (
         SELECT A.Name AS Customers, B.Id AS OrderId
         FROM Customers AS A
                  LEFT JOIN Orders AS B
                            ON A.Id = B.CustomerId
     ) AS C
WHERE C.OrderId IS NULL;

