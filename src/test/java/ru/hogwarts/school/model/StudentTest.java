package ru.hogwarts.school.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student out = new Student(1, "studentName", 15);

    @Test
    void getId() {
        assertEquals(1, out.getId());
    }

    @Test
    void setId() {
        out.setId(111);
        assertEquals(111, out.getId());
    }

    @Test
    void getName() {
        assertEquals("studentName", out.getName());
    }

    @Test
    void setName() {
        out.setName("changedName");
        assertEquals("changedName", out.getName());
    }

    @Test
    void getAge() {
        assertEquals(15, out.getAge());
    }

    @Test
    void setAge() {
        out.setAge(16);
        assertEquals(16, out.getAge());
    }
}