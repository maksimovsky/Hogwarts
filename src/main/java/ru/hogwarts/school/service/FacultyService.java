package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty add(Faculty faculty);

    Faculty getFacultyById(Integer id);

    void removeFacultyById(Integer id);

    Faculty editFaculty(Faculty faculty);

    Collection<Faculty> getSameColorFaculties(String color);

    Collection<Faculty> getByNameOrColor(String s);

    Collection<Student> getStudentsByFacultyId(int id);

    String getLongestName();
}
