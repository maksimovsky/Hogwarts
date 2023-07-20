package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository repositoryMock;
    @InjectMocks
    private StudentServiceImpl out;


    @Test
    void add() {
        when(repositoryMock.save(new Student(1, "st1", 14)))
                .thenReturn(new Student(1, "st1", 14));
        when(repositoryMock.findAll()).thenReturn(List.of(new Student(1, "st1", 14)));

        Student expected = new Student(1, "st1", 14);
        assertEquals(expected, out.add(new Student(1, "st1", 14)));
        assertTrue(out.getAll().contains(expected));
    }

    @Test
    void getStudentById() {
        when(repositoryMock.findById(1)).thenReturn(Optional.of(new Student(123, "st1", 14)));

        Student expected = new Student(1, "st1", 14);
        out.add(new Student(1, "st1", 14));
        assertEquals(expected, out.getStudentById(1));
    }

    @Test
    void removeStudentById() {
        Student expected = new Student(1, "st1", 14);
        when(repositoryMock.findAll()).thenReturn(List.of(expected));
        assertTrue(out.getAll().contains(expected));
        out.removeStudentById(1);

        when(repositoryMock.findAll()).thenReturn(List.of());
        assertFalse(out.getAll().contains(expected));
    }

    @Test
    void editStudent() {
        Student expected = new Student(1, "st2", 16);
        when(repositoryMock.save(expected)).thenReturn(expected);
        assertEquals(expected, out.editStudent(expected));
    }

    @Test
    void getSameAgeStudents() {
        Student st1 = new Student(1, "st1", 14);
        Student st2 = new Student(1, "st2", 15);
        Student st3 = new Student(1, "st3", 16);
        Student st4 = new Student(1, "st4", 14);
        Student st5 = new Student(1, "st5", 15);

        Collection<Student> age14 = List.of(st1, st4);
        Collection<Student> age15 = List.of(st2, st5);
        Collection<Student> age16 = List.of(st3);

        when(repositoryMock.findByAge(14)).thenReturn(age14);
        when(repositoryMock.findByAge(15)).thenReturn(age15);
        when(repositoryMock.findByAge(16)).thenReturn(age16);

        assertEquals(age14, out.getSameAgeStudents(14));
        assertEquals(age15, out.getSameAgeStudents(15));
        assertEquals(age16, out.getSameAgeStudents(16));
    }

    @Test
    void getAll() {
        Student st1 = new Student(1, "st1", 14);
        Student st2 = new Student(2, "st2", 15);
        Student st3 = new Student(3, "st3", 16);
        Student st4 = new Student(4, "st4", 14);
        Student st5 = new Student(5, "st5", 15);

        List<Student> students = List.of(st1, st2, st3, st4, st5);

        when(repositoryMock.findAll()).thenReturn(students);
        assertEquals(students, out.getAll());
    }
}