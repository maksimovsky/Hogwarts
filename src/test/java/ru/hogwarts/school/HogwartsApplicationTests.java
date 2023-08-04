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

import static org.assertj.core.api.Assertions.assertThat;

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
        Student student1 = new Student(1, "student1", 15);
        Student student3 = new Student(8, "student3", 14);
        Student student5 = new Student(10, "student5", 15);
        Student student7 = new Student(12, "student7", 13);
        Student student8 = new Student(13, "student8", 14);
        Student student9 = new Student(14, "student9", 15);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Student>> response = restTemplate
                .exchange("/student",
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(student8, student3,
                student9, student1, student5, student7);
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
        Student student3 = new Student(8, "student3", 14);
        Student student8 = new Student(13, "student8", 14);

        int age = 14;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Student>> response = restTemplate
                .exchange("/student/age?age=" + age,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(student8, student3);
    }

    @Test
    public void testGetByAge() {
        Student student3 = new Student(8, "student3", 14);
        Student student7 = new Student(12, "student7", 13);
        Student student8 = new Student(13, "student8", 14);

        int from = 10;
        int to = 14;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Student>> response = restTemplate
                .exchange("/student/ages?from=" + from + "&to=" + to,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(student7, student3, student8);
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
        Faculty faculty1 = new Faculty(1, "faculty1", "color1");
        Faculty faculty2 = new Faculty(2, "faculty2", "color2");
        Faculty faculty3 = new Faculty(3, "faculty3", "color3");
        Faculty faculty4 = new Faculty(4, "faculty4", "color1");
        Faculty faculty5 = new Faculty(5, "faculty5", "test_y2");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Faculty>> response = restTemplate
                .exchange("/faculty",
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(faculty3, faculty4, faculty2, faculty1, faculty5);
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
        Faculty faculty1 = new Faculty(1, "faculty1", "color1");
        Faculty faculty4 = new Faculty(4, "faculty4", "color1");

        String color = "color1";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Faculty>> response = restTemplate
                .exchange("/faculty/color?color=" + color,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(faculty1, faculty4);
    }

    @Test
    public void testGetByNameOrColor() {
        Faculty faculty2 = new Faculty(2, "faculty2", "color2");
        Faculty faculty5 = new Faculty(5, "faculty5", "test_y2");

        String s = "y2";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Faculty>> response = restTemplate
                .exchange("/faculty/find?s=" + s,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(faculty2, faculty5);
    }

    @Test
    public void testGetStudentsByFacultyId() {
        Student student1 = new Student(1, "student1", 15);
        Student student8 = new Student(13, "student8", 14);

        int facultyId = 2;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer JWT TOKEN HERE");
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Collection<Student>> response = restTemplate
                .exchange("/faculty/" + facultyId + "/students",
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getBody()).containsExactlyInAnyOrder(student1, student8);
    }
}
