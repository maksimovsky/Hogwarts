package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final HashMap<Integer, Faculty> faculties = new HashMap<>();
    private Integer id = 0;

    public Collection<Faculty> getAll() {
        return faculties.values().stream().toList();
    }

    @Override
    public Faculty add(Faculty faculty) {
        if (faculties.containsValue(faculty)) {
            return null;
        }
        faculty.setId(++id);
        faculties.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        return faculties.get(id);
    }

    @Override
    public Faculty removeFacultyById(Integer id) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        return faculties.remove(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> getSameColorFaculties(String color) {
        Collection<Faculty> sameColorFaculties = new ArrayList<>();
        for (Faculty f : faculties.values()) {
            if (f.getColor().equals(color)) {
                sameColorFaculties.add(f);
            }
        }
        return sameColorFaculties;
    }
}
