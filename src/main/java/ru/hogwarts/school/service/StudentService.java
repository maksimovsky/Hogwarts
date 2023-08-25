package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    Student getStudentById(Integer id);

    void removeStudentById(Integer id);

    Student editStudent(Student student);

    Collection<Student> getSameAgeStudents(Integer age);

    Collection<Student> findByAgeBetween(int from, int to);

    Faculty getFacultyByStudentId(int id);

    int getCount();

    double getAverageAge();

    Collection<Student> getLast5Students();

    Collection<String> getNamesWhichStartsWithA();

    double getAverageAge2();
}
