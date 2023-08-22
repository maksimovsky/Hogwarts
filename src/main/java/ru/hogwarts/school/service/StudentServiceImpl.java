package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Collection<Student> getAll() {
        logger.debug("Getting all students");
        return repository.findAll();
    }

    @Override
    public Student add(Student student) {
        logger.debug("Adding student {}", student.getName());
        return repository.save(student);
    }

    @Override
    public Student getStudentById(Integer id) {
        logger.debug("Getting student by id {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public void removeStudentById(Integer id) {
        logger.debug("Removing student by id {}", id);
        repository.deleteById(id);
    }

    @Override
    public Student editStudent(Student student) {
        logger.debug("Editing student with id {}", student.getId());
        return repository.save(student);
    }

    @Override
    public Collection<Student> getSameAgeStudents(Integer age) {
        logger.debug("Getting {} years old students", age);
        return repository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int from, int to) {
        logger.debug("Getting students with age from {} to {}", from, to);
        return repository.findByAgeBetween(from, to);
    }

    @Override
    public Faculty getFacultyByStudentId(int id) {
        logger.debug("Getting faculty by student id {}", id);
        return getStudentById(id).getFaculty();
    }

    @Override
    public int getCount() {
        logger.debug("Getting count of students");
        return repository.getCount();
    }

    @Override
    public double getAverageAge() {
        logger.debug("Getting average age of students");
        return repository.getAverageAge();
    }

    @Override
    public Collection<Student> getLast5Students() {
        logger.debug("Getting last 5 students");
        return repository.getLast5Students();
    }
}
