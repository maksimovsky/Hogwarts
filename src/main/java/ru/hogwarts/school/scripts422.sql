create table cars (
    id int4 primary key,
    brand varchar,
    model varchar,
    price numeric
)

create table users (
    id int4,
    name varchar primary key,
    age int4,
    license boolean,
    car_id int4 references cars (id)
)