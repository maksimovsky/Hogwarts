package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(int from, int to);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int getCount();

    @Query(value = "SELECT AVG(AGE) FROM student", nativeQuery = true)
    double getAverageAge();

    @Query(value = "SELECT * FROM (SELECT * FROM student ORDER BY id DESC LIMIT 5) as last5 ORDER BY id",
            nativeQuery = true)
    Collection<Student> getLast5Students();

    @Query(value = "SELECT name FROM student ORDER BY id", nativeQuery = true)
    String[] findNamesOrderById();
}