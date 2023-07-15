package ru.hogwarts.school.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    FacultyServiceImpl out = new FacultyServiceImpl();

    @AfterEach
    public void beforeEach() {
        out = new FacultyServiceImpl();
    }

    @Test

    void add() {
        Faculty expected = new Faculty(1, "faculty1", "color1");
        assertEquals(expected, out.add(new Faculty(123, "faculty1", "color1")));
        assertTrue(out.getAll().contains(expected));
        assertNull(out.add(new Faculty(456, "faculty1", "color1")));

        out.removeFacultyById(1);
    }

    @Test
    void getFacultyById() {
        Faculty expected = new Faculty(1, "faculty1", "color1");
        out.add(new Faculty(123, "faculty1", "color1"));
        assertEquals(expected, out.getFacultyById(1));
        assertNull(out.getFacultyById(2));

        out.removeFacultyById(1);
    }

    @Test
    void removeFacultyById() {
        Faculty expected = new Faculty(1, "faculty1", "color1");
        out.add(new Faculty(345, "faculty1", "color1"));
        assertEquals(expected, out.removeFacultyById(1));
        assertFalse(out.getAll().contains(expected));
    }

    @Test
    void editFaculty() {
        Faculty expected = new Faculty(1, "faculty2", "color2");
        out.add(new Faculty(435, "faculty1", "color1"));
        assertEquals(expected, out.editFaculty(1, expected));
        assertTrue(out.getAll().contains(expected));

        out.removeFacultyById(1);
    }

    @Test
    void getSameColorFaculties() {
        Faculty faculty1 = new Faculty(1, "faculty1", "color1");
        Faculty faculty2 = new Faculty(1, "faculty2", "color2");
        Faculty faculty3 = new Faculty(1, "faculty3", "color3");
        Faculty faculty4 = new Faculty(1, "faculty4", "color1");
        Faculty faculty5 = new Faculty(1, "faculty5", "color2");
        out.add(faculty1);
        out.add(faculty2);
        out.add(faculty3);
        out.add(faculty4);
        out.add(faculty5);
        Collection<Faculty> color1 = List.of(faculty1, faculty4);
        Collection<Faculty> color2 = List.of(faculty2, faculty5);
        Collection<Faculty> color3 = List.of(faculty3);

        assertEquals(color1, out.getSameColorFaculties("color1"));
        assertEquals(color2, out.getSameColorFaculties("color2"));
        assertEquals(color3, out.getSameColorFaculties("color3"));

        for (int i = 1; i < 6; i++) {
            out.removeFacultyById(i);
        }
    }

    @Test
    void getAll() {
        Faculty faculty1 = new Faculty(1, "faculty1", "color1");
        Faculty faculty2 = new Faculty(2, "faculty2", "color2");
        Faculty faculty3 = new Faculty(3, "faculty3", "color3");
        Faculty faculty4 = new Faculty(4, "faculty4", "color1");
        Faculty faculty5 = new Faculty(5, "faculty5", "color2");
        out.add(faculty1);
        out.add(faculty2);
        out.add(faculty3);
        out.add(faculty4);
        out.add(faculty5);
        Collection<Faculty> faculties = List.of(faculty1, faculty2, faculty3, faculty4, faculty5);

        assertEquals(faculties, out.getAll());

        for (int i = 1; i < 6; i++) {
            out.removeFacultyById(i);
        }
    }
}