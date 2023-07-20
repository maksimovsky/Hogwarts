package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    Student getStudentById(Integer id);

    void removeStudentById(Integer id);

    Student editStudent(Student student);

    Collection<Student> getSameAgeStudents(Integer age);
}
