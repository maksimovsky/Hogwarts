package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
        return validate(service.removeFacultyById(id));
    }

    @PutMapping
    public ResponseEntity<Object> editFaculty(@RequestBody Faculty faculty) {
        return validate(service.editFaculty(faculty));
    }

    @GetMapping("color/{color}")
    public ResponseEntity<Collection<Faculty>> getSameColorFaculties(@PathVariable String color) {
        Collection<Faculty> faculties = service.getSameColorFaculties(color);
        if (faculties.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculties);
    }

    private ResponseEntity<Object> validate(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }
}
