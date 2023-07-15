package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty add(Faculty faculty);

    Faculty getFacultyById(Integer id);

    Faculty removeFacultyById(Integer id);

    Faculty editFaculty(Faculty faculty);

    Collection<Faculty> getSameColorFaculties(String color);
}
