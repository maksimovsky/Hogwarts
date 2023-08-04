package ru.hogwarts.school;

import org.json.JSONObject;
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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

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

    private final Student student1 = new Student(1, "name1", 13);
    private final Student student2 = new Student(2, "name2", 15);
    private final Student student3 = new Student(3, "name3", 14);
    private final Student student4 = new Student(4, "name4", 15);
    private final Student student5 = new Student(5, "name5", 13);

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
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student2));

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
}
