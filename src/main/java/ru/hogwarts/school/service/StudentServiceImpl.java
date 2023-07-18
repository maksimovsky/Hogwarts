package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentServiceImpl implements StudentService {
    private static final HashMap<Integer, Student> students = new HashMap<>();
    private Integer id = 0;

    public Collection<Student> getAll() {
        return students.values().stream().toList();
    }

    @Override
    public Student add(Student student) {
        if (students.containsValue(student)) {
            return null;
        }
        student.setId(++id);
        students.put(id, student);
        return student;
    }

    @Override
    public Student getStudentById(Integer id) {
        if (!students.containsKey(id)) {
            return null;
        }
        return students.get(id);
    }

    @Override
    public Student removeStudentById(Integer id) {
        if (!students.containsKey(id)) {
            return null;
        }
        return students.remove(id);
    }

    @Override
    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Collection<Student> getSameAgeStudents(Integer age) {
        Collection<Student> sameAgeStudents = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getAge() == age) {
                sameAgeStudents.add(s);
            }
        }
        return sameAgeStudents;
    }
}
