package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        assertThat(studentController).isNotNull();
        assertThat(facultyController).isNotNull();
    }

    ////////////////////// StudentController Tests //////////////////////

    @Test
    public void testGetAllStudents() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotEmpty();
    }

    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setName("studentName");
        student.setAge(15);

        ResponseEntity<Student> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    public void testGetStudentById() {
        int id = 8;
        String name = "student3";
        int age = 14;
        Student student = new Student(id, name, age);

        ResponseEntity<Student> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + id, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    public void removeStudentById() {
        int id = 9;
        String name = "student4";
        int age = 16;
        Student student = new Student(id, name, age);

        ResponseEntity<Student> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + id, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(student);

        this.restTemplate.delete("http://localhost:" + port + "/student/" + id);

        ResponseEntity<Student> response2 = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + id, Student.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testEditStudent() {
        int id = 10;
        String name = "student5";
        int age = 14;
        Student studentBefore = new Student(id, name, age);

        ResponseEntity<Student> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + id, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studentBefore);

        int id2 = 10;
        String name2 = "student5";
        int age2 = 15;
        Student studentAfter = new Student(id2, name2, age2);

        this.restTemplate.put("http://localhost:" + port + "/student", studentAfter);

        ResponseEntity<Student> response2 = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + id, Student.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo(studentAfter);
    }

    @Test
    public void testGetSameAgeStudents() {
        int age = 15;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Student>> response = restTemplate
                .exchange("http://localhost:" + port + "/student/age",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Collection<Student>>() {
                }, age);

        Collection<Student> students = response.getBody();
        System.out.println(students);

//        ResponseEntity<Collection<Student>> response = this.restTemplate
//                .exchange("http://localhost:" + port + "/student/age",
//                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

//        ResponseEntity<Student[]> response = this.restTemplate
//                .getForEntity("http://localhost:" + port + "/student/age", Student[].class, age);


//        System.out.println();
//        System.out.println(response.getBody());
//        System.out.println();
    }

    @Test
    public void testGetByAge() {

    }

    @Test
    public void testGetFacultyByStudentId() {
        int id = 12;
        Faculty faculty = new Faculty();
        faculty.setName("faculty3");
        faculty.setColor("color3");

        ResponseEntity<Faculty> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + id + "/faculty", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(faculty);
    }

    ////////////////////// FacultyController Tests //////////////////////

    @Test
    public void testGetAllFaculties() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
    }

    @Test
    public void testAddFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("facultyName");
        faculty.setColor("facultyColor");

        ResponseEntity<Faculty> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(faculty);
    }

    @Test
    public void testGetFacultyById() {
        int id = 3;
        String name = "faculty3";
        String color = "color3";
        Faculty faculty = new Faculty(id, name, color);

        ResponseEntity<Faculty> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(faculty);
    }

    @Test
    public void testRemoveFacultyById() {
        int id = 4;
        String name = "facultyName";
        String color = "facultyColor";
        Faculty faculty = new Faculty(id, name, color);

        ResponseEntity<Faculty> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(faculty);

        this.restTemplate.delete("http://localhost:" + port + "/faculty/" + id);

        ResponseEntity<Faculty> response2 = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testEditFaculty() {
        int id = 3;
        String name = "faculty3";
        String color = "color4";
        Faculty facultyBefore = new Faculty(id, name, color);

        ResponseEntity<Faculty> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(facultyBefore);

        int id2 = 3;
        String name2 = "faculty3";
        String color2 = "color3";
        Faculty facultyAfter = new Faculty(id2, name2, color2);

        this.restTemplate.put("http://localhost:" + port + "/faculty", facultyAfter);

        ResponseEntity<Faculty> response2 = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo(facultyAfter);
    }

    @Test
    public void testGetSameColorFaculties() {

    }

    @Test
    public void testGetByNameOrColor() {

    }

    @Test
    public void testGetStudentsByFacultyId() {

    }
}
