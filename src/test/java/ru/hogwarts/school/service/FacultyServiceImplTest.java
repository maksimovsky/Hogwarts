package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository repositoryMock;
    @InjectMocks
    private FacultyServiceImpl out;


    @Test
    void add() {
        when(repositoryMock.save(new Faculty(1, "f1", "color1")))
                .thenReturn(new Faculty(1, "f1", "color1"));
        when(repositoryMock.findAll()).thenReturn(List.of(new Faculty(1, "f1", "color1")));

        Faculty expected = new Faculty(1, "f1", "color1");
        assertEquals(expected, out.add(new Faculty(1, "f1", "color1")));
        assertTrue(out.getAll().contains(expected));
    }

    @Test
    void getFacultyById() {
        when(repositoryMock.findById(1)).thenReturn(Optional.of(new Faculty(1, "f1", "color1")));

        Faculty expected = new Faculty(1, "f1", "color1");
        out.add(new Faculty(1, "f1", "color1"));
        assertEquals(expected, out.getFacultyById(1));
    }

    @Test
    void removeFacultyById() {
        Faculty expected = new Faculty(1, "f1", "color1");
        when(repositoryMock.findAll()).thenReturn(List.of(expected));
        assertTrue(out.getAll().contains(expected));
        out.removeFacultyById(1);

        when(repositoryMock.findAll()).thenReturn(List.of());
        assertFalse(out.getAll().contains(expected));
    }

    @Test
    void editFaculty() {
        Faculty expected = new Faculty(1, "f1", "color1");
        when(repositoryMock.save(expected)).thenReturn(expected);
        assertEquals(expected, out.editFaculty(expected));
    }

    @Test
    void getSameColorFaculties() {
        Faculty f1 = new Faculty(1, "f1", "color1");
        Faculty f2 = new Faculty(1, "f2", "color2");
        Faculty f3 = new Faculty(1, "f3", "color3");
        Faculty f4 = new Faculty(1, "f4", "color1");
        Faculty f5 = new Faculty(1, "f5", "color2");

        Collection<Faculty> color1 = List.of(f1, f4);
        Collection<Faculty> color2 = List.of(f2, f5);
        Collection<Faculty> color3 = List.of(f3);

        when(repositoryMock.findByColorIgnoreCase("color1")).thenReturn(color1);
        when(repositoryMock.findByColorIgnoreCase("color2")).thenReturn(color2);
        when(repositoryMock.findByColorIgnoreCase("color3")).thenReturn(color3);

        assertEquals(color1, out.getSameColorFaculties("color1"));
        assertEquals(color2, out.getSameColorFaculties("color2"));
        assertEquals(color3, out.getSameColorFaculties("color3"));
    }

    @Test
    void getAll() {
        Faculty f1 = new Faculty(1, "f1", "color1");
        Faculty f2 = new Faculty(2, "f2", "color2");
        Faculty f3 = new Faculty(3, "f3", "color3");
        Faculty f4 = new Faculty(4, "f4", "color1");
        Faculty f5 = new Faculty(5, "f5", "color2");

        List<Faculty> faculties = List.of(f1, f2, f3, f4, f5);

        when(repositoryMock.findAll()).thenReturn(faculties);
        assertEquals(faculties, out.getAll());
    }

    @Test
    void getByNameOrColor() {
        Collection<Faculty> faculties = List.of(
                new Faculty(1, "f1", "color1"),
                new Faculty(2, "f2", "color2"),
                new Faculty(3, "qwerty", "color-f")
        );
        when(repositoryMock.findByNameIgnoreCaseContainsOrColorIgnoreCaseContains("f", "f"))
                .thenReturn(faculties);

        assertEquals(faculties, out.getByNameOrColor("f"));
    }

    @Test
    void getStudentsByFacultyId() {
        Collection<Student> students = List.of(
                new Student(1, "s1", 12),
                new Student(2,"s2", 13)
        );
        Faculty faculty = new Faculty(1, "f1", "color1");
        faculty.setStudents(students);
        when(repositoryMock.findById(1)).thenReturn(Optional.of(faculty));

        assertEquals(students, out.getStudentsByFacultyId(1));
    }
}