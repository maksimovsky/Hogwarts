package ru.hogwarts.school.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacultyTest {

    Faculty out = new Faculty(1, "facultyName", "white");

    @Test
    void getId() {
        assertEquals(1, out.getId());
    }

    @Test
    void setId() {
        out.setId(123);
        assertEquals(123, out.getId());
    }

    @Test
    void getName() {
        assertEquals("facultyName", out.getName());
    }

    @Test
    void setName() {
        out.setName("changedName");
        assertEquals("changedName", out.getName());
    }

    @Test
    void getColor() {
        assertEquals("white", out.getColor());
    }

    @Test
    void setColor() {
        out.setColor("black");
        assertEquals("black", out.getColor());
    }
}