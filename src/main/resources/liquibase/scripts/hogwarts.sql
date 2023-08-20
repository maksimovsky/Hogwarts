-- liquibase formatted sql

-- changeset romax:1
create index student_name_index on student (name)

--changeset romax:2
create index faculty_nc_idx on faculty (name, color)