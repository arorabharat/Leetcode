# https://leetcode.com/problems/department-highest-salary/

SELECT T.Department, E2.Name AS "Employee", T.Salary
FROM (
         SELECT D.Id AS "Id", D.Name AS "Department", MAX(E.Salary) AS "Salary"
         FROM Employee AS E
                  INNER JOIN Department AS D
                             ON E.DepartmentId = D.Id
         GROUP BY D.Name
     ) AS T
         INNER JOIN Employee AS E2
                    ON E2.DepartmentId = T.Id AND E2.Salary = T.Salary


# Approach 2

SELECT Department.name AS "Department",
       Employee.name   AS "Employee",
       Salary
FROM Employee
         JOIN
     Department ON Employee.DepartmentId = Department.Id
WHERE (Employee.DepartmentId, Salary) IN
      (SELECT DepartmentId,
              MAX(Salary)
       FROM Employee
       GROUP BY DepartmentId
      )
;