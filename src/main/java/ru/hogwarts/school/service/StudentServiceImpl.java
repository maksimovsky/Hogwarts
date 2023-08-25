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
    public double getAverageAge2() {
        logger.debug("Getting average age of students with stream");
        return repository.findAll()
                .stream()
                .parallel()
                .mapToInt(Student::getAge)
                .average().orElse(0);
    }

    @Override
    public Collection<Student> getLast5Students() {
        logger.debug("Getting last 5 students");
        return repository.getLast5Students();
    }

    @Override
    public Collection<String> getNamesWhichStartsWithA() {
        logger.debug("Getting names which starts with 'A'");
        return repository.findAll()
                .stream()
                .parallel()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .toList();
    }

    @Override
    public void getNamesInDifferentStreams() {
        printNameByIndex(0);
        printNameByIndex(1);

        new Thread(() -> {
            printNameByIndex(2);
            printNameByIndex(3);
        }).start();

        new Thread(() -> {
            printNameByIndex(4);
            printNameByIndex(5);
        }).start();
    }

    private void printNameByIndex(int index) {
        String[] names = repository.findNamesOrderById();
        System.out.println(names[index]);

//        For loading
//        String s = "";
//        for (int i = 0; i < 200_000; i++) {
//            s += i;
//        }
    }

    @Override
    public void getNamesInDifferentStreams2() {
        printNameByIndex2(0);
        printNameByIndex2(1);

        new Thread(() -> {
            printNameByIndex2(2);
            printNameByIndex2(3);
        }).start();

        new Thread(() -> {
            printNameByIndex2(4);
            printNameByIndex2(5);
        }).start();
    }

    private synchronized void printNameByIndex2(int index) {
        String[] names = repository.findNamesOrderById();
        System.out.println(names[index]);


//        For loading
//        String s = "";
//        for (int i = 0; i < 100_000; i++) {
//            s += i;
//        }
    }
}
