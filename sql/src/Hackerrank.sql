-- https://www.hackerrank.com/challenges/revising-the-select-query-2/problem
SELECT NAME
FROM City
WHERE COUNTRYCODE = 'USA'
  AND POPULATION > 120000

-- https://www.hackerrank.com/challenges/select-all-sql/problem
SELECT *
FROM CITY

-- https://www.hackerrank.com/challenges/select-by-id/problem
SELECT *
FROM CITY
WHERE ID = 1661

-- https://www.hackerrank.com/challenges/japanese-cities-attributes/problem
SELECT *
FROM CITY
WHERE COUNTRYCODE = 'JPN'

-- https://www.hackerrank.com/challenges/japanese-cities-name/problem
SELECT NAME
FROM CITY
WHERE COUNTRYCODE = "JPN"

-- https://www.hackerrank.com/challenges/weather-observation-station-1/problem
SELECT CITY, STATE
FROM STATION

-- https://www.hackerrank.com/challenges/weather-observation-station-3/problem
SELECT DISTINCT CITY
FROM STATION
WHERE ID%2=0;

-- https://www.hackerrank.com/challenges/weather-observation-station-4/problem
SELECT COUNT(CITY) - COUNT(DISTINCT CITY)
FROM STATION

-- https://www.hackerrank.com/challenges/weather-observation-station-5/problem
SELECT CITY, LENGTH(CITY)
FROM (SELECT * FROM STATION ORDER BY CITY ASC) AS ST
WHERE LENGTH(CITY) IN (SELECT MAX(LENGTH(CITY)) FROM STATION);
SELECT CITY, LENGTH(CITY)
FROM (SELECT * FROM STATION ORDER BY CITY ASC) AS ST
WHERE LENGTH(CITY) IN (SELECT MIN(LENGTH(CITY)) FROM STATION) LIMIT 1;

-- https://www.hackerrank.com/challenges/weather-observation-station-6/problem
SELECT DISTINCT CITY
FROM STATION
WHERE CITY LIKE "a%"
   OR CITY LIKE "e%"
   OR CITY LIKE "i%"
   OR CITY LIKE "o%"
   OR CITY LIKE "u%";

-- https://www.hackerrank.com/challenges/weather-observation-station-7/problem
SELECT DISTINCT CITY
FROM STATION
WHERE CITY LIKE "%a"
   OR CITY LIKE "%e"
   OR CITY LIKE "%i"
   OR CITY LIKE "%o"
   OR CITY LIKE "%u";

-- https://www.hackerrank.com/challenges/weather-observation-station-8/problem
SELECT DISTINCT CITY
FROM STATION
WHERE (CITY LIKE "%a" OR CITY LIKE "%e" OR CITY LIKE "%i" OR CITY LIKE "%o" OR CITY LIKE "%u")
  AND (CITY LIKE "a%" OR CITY LIKE "e%" OR CITY LIKE "i%" OR CITY LIKE "o%" OR CITY LIKE "u%");

-- https://www.hackerrank.com/challenges/weather-observation-station-9/problem
SELECT DISTINCT CITY
FROM STATION
WHERE (CITY NOT LIKE "a%" AND CITY NOT LIKE "e%" AND CITY NOT LIKE "i%" AND CITY NOT LIKE "o%" AND CITY NOT LIKE "u%");

-- https://www.hackerrank.com/challenges/weather-observation-station-10/problem
SELECT DISTINCT CITY
FROM STATION
WHERE (CITY NOT LIKE "%a" AND CITY NOT LIKE "%e" AND CITY NOT LIKE "%i" AND CITY NOT LIKE "%o" AND CITY NOT LIKE "%u");

-- https://www.hackerrank.com/challenges/weather-observation-station-11/problem
SELECT DISTINCT CITY
FROM STATION
WHERE (CITY NOT LIKE "%a" AND CITY NOT LIKE "%e" AND CITY NOT LIKE "%i" AND CITY NOT LIKE "%o" AND CITY NOT LIKE "%u")
   OR (CITY NOT LIKE "a%" AND CITY NOT LIKE "e%" AND CITY NOT LIKE "i%" AND CITY NOT LIKE "o%" AND CITY NOT LIKE "u%");

-- https://www.hackerrank.com/challenges/salary-of-employees/problem
SELECT E.name
FROM Employee AS E
WHERE E.salary > 2000
  AND E.months < 10
ORDER BY E.employee_id ASC

-- https://www.hackerrank.com/challenges/earnings-of-employees/problem
SELECT M.totalSalary, COUNT(M.totalSalary)
FROM (SELECT salary * months AS totalSalary FROM Employee) AS M
WHERE M.totalSalary = (SELECT MAX(salary * months) FROM Employee)
GROUP BY M.totalSalary

-- https://www.hackerrank.com/challenges/population-density-difference/problem ( explain why two aggregation function work in one select)
SELECT MAX(POPULATION) - MIN(POPULATION)
FROM CITY

-- https://www.hackerrank.com/challenges/japan-population/problem
SELECT SUM(POPULATION)
FROM CITY
WHERE COUNTRYCODE = "JPN"

-- https://www.hackerrank.com/challenges/average-population/problem
SELECT ROUND(AVG(POPULATION))
FROM CITY

-- https://www.hackerrank.com/challenges/revising-aggregations-the-average-function/problem
SELECT AVG(POPULATION)
FROM CITY
WHERE DISTRICT = "California";

-- https://www.hackerrank.com/challenges/revising-aggregations-sum/problem
SELECT SUM(POPULATION)
FROM CITY
WHERE DISTRICT = "California";

-- https://www.hackerrank.com/challenges/african-cities/problem
SELECT CITY.NAME
FROM CITY
         LEFT JOIN COUNTRY
                   ON COUNTRY.CODE = CITY.COUNTRYCODE
WHERE COUNTRY.CONTINENT = 'Africa';

-- https://www.hackerrank.com/challenges/average-population-of-each-continent/problem
SELECT COUNTRY.CONTINENT, FLOOR(AVG(CITY.POPULATION))
FROM COUNTRY
         INNER JOIN CITY
                    ON COUNTRY.CODE = CITY.COUNTRYCODE
GROUP BY COUNTRY.CONTINENT;

-- https://www.hackerrank.com/challenges/the-report/problem
SELECT IF(G.Grade < 8, "NULL", S.Name), G.Grade, S.Marks
FROM Students AS S
         INNER JOIN
     Grades AS G
     ON G.Min_Mark <= S.Marks AND S.Marks <= G.Max_Mark
ORDER BY G.Grade DESC, S.Name ASC, S.Marks ASC

