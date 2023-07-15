package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;

@RequestMapping("/student")
@RestController
public class StudentController {
    StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> add(Student student) {
        Student student1 = service.add(student);
        return validate(student1);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable Integer id) {
        return validate(service.getStudentById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeStudentById(@PathVariable Integer id) {
        return validate(service.removeStudentById(id));
    }

    @PutMapping
    public ResponseEntity<Object> editStudent(Student student) {
        return validate(service.editStudent(student.getId(), student));
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getSameAgeStudents(@PathVariable Integer age) {
        Collection<Student> students = service.getSameAgeStudents(age);
        if (students.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok(students);
        }
    }

    private ResponseEntity<Object> validate(Student student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok(student);
        }
    }
}
