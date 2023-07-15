package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    StudentServiceImpl out = new StudentServiceImpl();


    @Test
    void add() {
        Student expected = new Student(1, "st1", 14);
        assertEquals(expected, out.add(new Student(123, "st1", 14)));
        assertTrue(out.getAll().contains(expected));
        assertNull(out.add(new Student(456, "st1", 14)));

        out.removeStudentById(1);
    }

    @Test
    void getStudentById() {
        Student expected = new Student(1, "st1", 14);
        out.add(new Student(123, "st1", 14));
        assertEquals(expected, out.getStudentById(1));
        assertNull(out.getStudentById(2));

        out.removeStudentById(1);
    }

    @Test
    void removeStudentById() {
        Student expected = new Student(1, "st1", 14);
        out.add(new Student(345, "st1", 14));
        assertEquals(expected, out.removeStudentById(1));
        assertFalse(out.getAll().contains(expected));
    }

    @Test
    void editStudent() {
        Student expected = new Student(1, "st2", 16);
        out.add(new Student(435, "st1", 14));
        assertEquals(expected, out.editStudent(expected));
        assertTrue(out.getAll().contains(expected));

        out.removeStudentById(1);
    }

    @Test
    void getSameAgeStudents() {
        Student st1 = new Student(1, "st1", 14);
        Student st2 = new Student(1, "st2", 15);
        Student st3 = new Student(1, "st3", 16);
        Student st4 = new Student(1, "st4", 14);
        Student st5 = new Student(1, "st5", 15);
        out.add(st1);
        out.add(st2);
        out.add(st3);
        out.add(st4);
        out.add(st5);
        Collection<Student> age14 = List.of(st1, st4);
        Collection<Student> age15 = List.of(st2, st5);
        Collection<Student> age16 = List.of(st3);

        assertEquals(age14, out.getSameAgeStudents(14));
        assertEquals(age15, out.getSameAgeStudents(15));
        assertEquals(age16, out.getSameAgeStudents(16));

        for (int i = 1; i < 6; i++) {
            out.removeStudentById(i);
        }
    }

    @Test
    void getAll() {
        Student st1 = new Student(1, "st1", 14);
        Student st2 = new Student(2, "st2", 15);
        Student st3 = new Student(3, "st3", 16);
        Student st4 = new Student(4, "st4", 14);
        Student st5 = new Student(5, "st5", 15);
        out.add(st1);
        out.add(st2);
        out.add(st3);
        out.add(st4);
        out.add(st5);
        Collection<Student> students = List.of(st1, st2, st3, st4, st5);

        assertEquals(students, out.getAll());

        for (int i = 1; i < 6; i++) {
            out.removeStudentById(i);
        }
    }
}