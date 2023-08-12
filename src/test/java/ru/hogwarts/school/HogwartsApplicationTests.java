package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class HogwartsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private StudentController studentController;

    private final static Student student1 = new Student(1, "student1", 13);
    private final static Student student2 = new Student(2, "student2", 15);
    private final static Student student3 = new Student(3, "student3", 14);
    private final static Student student4 = new Student(4, "student4", 15);
    private final static Student student5 = new Student(5, "student5", 13);

    private final static Faculty faculty1 = new Faculty(1, "faculty1", "color1");
    private final static Faculty faculty2 = new Faculty(2, "faculty2", "color2");
    private final static Faculty faculty3 = new Faculty(3, "faculty3", "color1");
    private final static Faculty faculty4 = new Faculty(4, "faculty4", "color_y2");

    @BeforeAll
    public static void setUp() {
        student1.setFaculty(faculty1);
        student2.setFaculty(faculty3);
        student3.setFaculty(faculty2);
        student4.setFaculty(faculty2);
        student5.setFaculty(faculty1);

        faculty1.setStudents(List.of(student1, student5));
        faculty2.setStudents(List.of(student3, student4));
        faculty3.setStudents(List.of(student2));
    }

    //////////////////////// StudentController tests ////////////////////////

    @Test
    public void getAllStudentsTest() throws Exception {
        List<Student> students = List.of(student1, student2, student3);

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student1.getName()))
                .andExpect(jsonPath("$.[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$.[1].name").value(student2.getName()))
                .andExpect(jsonPath("$.[1].age").value(student2.getAge()))
                .andExpect(jsonPath("$.[2].name").value(student3.getName()))
                .andExpect(jsonPath("$.[2].age").value(student3.getAge()));
    }

    @Test
    public void addStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student1.getName());
        studentObject.put("age", student1.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));
    }

    @Test
    public void getStudentById() throws Exception {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student2.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student2.getId()))
                .andExpect(jsonPath("$.name").value(student2.getName()))
                .andExpect(jsonPath("$.age").value(student2.getAge()));
    }

    @Test
    public void removeStudentByIdTest() throws Exception {
        List<Student> students = List.of(student1, student2, student3, student4);
        List<Student> students2 = List.of(student1, student2, student4);

        when(studentRepository.findAll())
                .thenReturn(students)
                .thenReturn(students2);
        doNothing().when(studentRepository).deleteById(student3.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + student3.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void editStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student5.getName());
        studentObject.put("age", student5.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student5);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student5.getId()))
                .andExpect(jsonPath("$.name").value(student5.getName()))
                .andExpect(jsonPath("$.age").value(student5.getAge()));
    }

    @Test
    public void getSameAgeStudents() throws Exception {
        int age = 15;
        Collection<Student> students = List.of(student2, student4);

        when(studentRepository.findByAge(anyInt())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age")
                        .param("age", String.valueOf(age))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].age").value(age))
                .andExpect(jsonPath("$.[1].age").value(age));
    }

    @Test
    public void getByAgeStudents() throws Exception {
        int from = 10;
        int to = 14;
        Collection<Student> students = List.of(student1, student3, student5);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/ages")
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$.[1].age").value(student3.getAge()))
                .andExpect(jsonPath("$.[2].age").value(student5.getAge()));
    }

    @Test
    public void getFacultyByStudentIdTest() throws Exception {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student1));
        when(studentRepository.findById(2)).thenReturn(Optional.of(student2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + 1 + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student1.getFaculty().getName()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + 2 + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student2.getFaculty().getName()));
    }

    //////////////////////// FacultyController tests ////////////////////////

    @Test
    public void getAllFacultiesTest() throws Exception {
        List<Faculty> faculties = List.of(faculty1, faculty2, faculty3);

        when(facultyRepository.findAll()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty2.getColor()))
                .andExpect(jsonPath("$.[2].name").value(faculty3.getName()))
                .andExpect(jsonPath("$.[2].color").value(faculty3.getColor()));
    }

    @Test
    public void addFacultyTest() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty1.getName());
        facultyObject.put("color", faculty1.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty1.getId()))
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }

    @Test
    public void getFacultyById() throws Exception {
        when(facultyRepository.findById(anyInt())).thenReturn(Optional.of(faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty2.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty2.getId()))
                .andExpect(jsonPath("$.name").value(faculty2.getName()))
                .andExpect(jsonPath("$.color").value(faculty2.getColor()));
    }

    @Test
    public void removeFacultyByIdTest() throws Exception {
        List<Faculty> faculties = List.of(faculty1, faculty2, faculty3, faculty4);
        List<Faculty> faculties2 = List.of(faculty1, faculty2, faculty4);

        when(facultyRepository.findAll())
                .thenReturn(faculties)
                .thenReturn(faculties2);
        doNothing().when(facultyRepository).deleteById(faculty3.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + faculty3.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void editFacultyTest() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty4.getName());
        facultyObject.put("color", faculty4.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty4);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty4.getId()))
                .andExpect(jsonPath("$.name").value(faculty4.getName()))
                .andExpect(jsonPath("$.color").value(faculty4.getColor()));
    }

    @Test
    public void getSameColorFacultiesTest() throws Exception {
        String color = "color1";

        when(facultyRepository.findByColorIgnoreCase(anyString())).thenReturn(List.of(faculty1, faculty3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color")
                        .param("color", color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("faculty1"))
                .andExpect(jsonPath("$.[1].name").value("faculty3"));
    }

    @Test
    public void getByNameOrColorTest() throws Exception {
        String s = "y2";

        when(facultyRepository.findByNameIgnoreCaseContainsOrColorIgnoreCaseContains(s, s))
                .thenReturn(List.of(faculty2, faculty4));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find")
                        .param("s", s)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("faculty2"))
                .andExpect(jsonPath("$.[1].name").value("faculty4"));
    }

    @Test
    public void getStudentsByFacultyIdTest() throws Exception {
        int id = 1;

        when(facultyRepository.findById(anyInt())).thenReturn(Optional.of(faculty1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id + "/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student1.getName()))
                .andExpect(jsonPath("$.[1].name").value(student5.getName()));
    }
}
