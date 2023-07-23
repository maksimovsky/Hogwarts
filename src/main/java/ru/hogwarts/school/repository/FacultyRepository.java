package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    Collection<Faculty> findByColorIgnoreCase(String color);

    Collection<Faculty> findByNameIgnoreCaseContainsOrColorIgnoreCaseContains(String name, String color);
}