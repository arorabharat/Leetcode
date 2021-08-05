# https://leetcode.com/problems/second-highest-salary/
SELECT A.Name AS Employee
FROM (
         SELECT E.Name, E.Salary AS EmployeeSalary, M.Salary AS ManagerSalary
         FROM Employee AS E
                  INNER JOIN
              Employee AS M
              ON E.ManagerId = M.Id
     ) AS A
WHERE A.EmployeeSalary > A.ManagerSalary