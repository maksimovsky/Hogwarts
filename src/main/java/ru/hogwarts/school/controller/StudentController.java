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
    public ResponseEntity<Object> add(@RequestBody Student student) {
        return validate(service.add(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable Integer id) {
        return validate(service.getStudentById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeStudentById(@PathVariable Integer id) {
        service.removeStudentById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> editStudent(@RequestBody Student student) {
        return validate(service.editStudent(student));
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getSameAgeStudents(@RequestParam Integer age) {
        return validate(service.getSameAgeStudents(age));
    }

    @GetMapping("/ages")
    public ResponseEntity<Collection<Student>> getByAge(@RequestParam int from, @RequestParam int to) {
        return validate(service.findByAgeBetween(from, to));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Object> getFacultyByStudentId(@PathVariable int id) {
        return validate(service.getFacultyByStudentId(id));
    }

    @GetMapping("/count")
    public int getCount() {
        return service.getCount();
    }

    @GetMapping("/avg_age")
    public double getAverageAge() {
        return service.getAverageAge();
    }

    @GetMapping("/last5")
    public Collection<Student> getLast5Students() {
        return service.getLast5Students();
    }

    private static ResponseEntity<Object> validate(Object o) {
        if (o == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(o);
    }

    private static ResponseEntity<Collection<Student>> validate(Collection<Student> students) {
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(students);
    }
}
