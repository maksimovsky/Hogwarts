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
        Faculty faculty = faculties.get(id);
        faculties.remove(id);
        return faculty;
    }

    @Override
    public Faculty editFaculty(Integer id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        faculties.get(id).setName(faculty.getName());
        faculties.get(id).setColor(faculty.getColor());
        return faculties.get(id);
    }

    @Override
    public Collection<Faculty> getSameColorFaculties(String color) {
        Collection<Faculty> sameColorFaculties = new ArrayList<>();
        for (Faculty s : faculties.values()) {
            if (s.getColor().equals(color)) {
                sameColorFaculties.add(s);
            }
        }
        return sameColorFaculties;
    }
}
