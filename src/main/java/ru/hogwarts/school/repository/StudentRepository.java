package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(int from, int to);
}