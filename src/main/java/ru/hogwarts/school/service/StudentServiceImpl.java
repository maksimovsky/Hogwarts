package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Collection<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Student add(Student student) {
        return repository.save(student);
    }

    @Override
    public Student getStudentById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void removeStudentById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Student editStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Collection<Student> getSameAgeStudents(Integer age) {
        return repository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int from, int to) {
        return repository.findByAgeBetween(from, to);
    }

    @Override
    public Faculty getFacultyByStudentId(int id) {
        return getStudentById(id).getFaculty();
    }

    @Override
    public int getCount() {
        return repository.getCount();
    }

    @Override
    public double getAverageAge() {
        return repository.getAverageAge();
    }

    @Override
    public Collection<Student> getLast5Students() {
        return repository.getLast5Students();
    }
}
