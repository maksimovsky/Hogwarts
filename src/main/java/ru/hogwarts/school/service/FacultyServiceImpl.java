package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    public Collection<Faculty> getAll() {
        return repository.findAll();
    }

    @Override
    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void removeFacultyById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Collection<Faculty> getSameColorFaculties(String color) {
        return repository.findByColorIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String s) {
        return repository.findByNameIgnoreCaseContainsOrColorIgnoreCaseContains(s, s);
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(int id) {
        Faculty faculty = getFacultyById(id);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }
}
