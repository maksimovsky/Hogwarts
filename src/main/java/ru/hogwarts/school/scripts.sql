--1
select *
from student
where age > 10
  and age < 14
order by id;

--2
select name
from student;

--3
select *
from student
where name like ('%o%');

--4
select *
from student
where age < id;

--5
select *
from student
order by age