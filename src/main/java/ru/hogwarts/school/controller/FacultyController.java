package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Collection;

@RequestMapping("/faculty")
@RestController
public class FacultyController {
    private final FacultyServiceImpl service;

    public FacultyController(FacultyServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Faculty faculty) {
        return validate(service.add(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getFacultyById(@PathVariable Integer id) {
        return validate(service.getFacultyById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeFacultyById(@PathVariable Integer id) {
        service.removeFacultyById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> editFaculty(@RequestBody Faculty faculty) {
        return validate(service.editFaculty(faculty));
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getSameColorFaculties(@RequestParam String color) {
        return validate(service.getSameColorFaculties(color));
    }

    @GetMapping("find")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam String s) {
        return validate(service.getByNameOrColor(s));
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsByFacultyId(@PathVariable int id) {
        Collection<Student> students = service.getStudentsByFacultyId(id);
        if (students == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(students);
    }

    private static ResponseEntity<Object> validate(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(faculty);
    }

    private static ResponseEntity<Collection<Faculty>> validate(Collection<Faculty> faculties) {
        if (faculties.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(faculties);
    }
}
