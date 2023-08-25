package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    public Collection<Faculty> getAll() {
        logger.debug("Getting all faculties");
        return repository.findAll();
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.debug("Adding faculty {}", faculty.getName());
        return repository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        logger.debug("Getting faculty by id {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public void removeFacultyById(Integer id) {
        logger.debug("Removing faculty by id {}", id);
        repository.deleteById(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Editing faculty with id {}", faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public Collection<Faculty> getSameColorFaculties(String color) {
        logger.debug("Getting {} color faculties", color);
        return repository.findByColorIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String s) {
        logger.debug("Getting faculty by name or color: {}", s);
        return repository.findByNameIgnoreCaseContainsOrColorIgnoreCaseContains(s, s);
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(int id) {
        logger.debug("Getting students by faculty id {}", id);
        Faculty faculty = getFacultyById(id);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }

    @Override
    public String getLongestName() {
        logger.debug("Getting the longest name");
        return repository.findAll()
                .stream()
                .parallel()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).orElse("");
    }
}
