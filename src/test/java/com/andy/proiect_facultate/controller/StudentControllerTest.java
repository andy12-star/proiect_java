package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.model.enums.RoleType;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.impl.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private StudentController studentController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "Bearer dummy.token.value";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .addFilter(jwtAuthenticationFilter)
                .build();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(USERNAME)
                .password("password")
                .roles("CLIENT")
                .build();

        when(jwtUtil.extractUsername(anyString())).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(jwtUtil.isTokenValid(anyString(), eq(USERNAME))).thenReturn(true);
    }

    @Test
    void getAllStudents_returnsOk() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(new Student(), new Student()));

        mockMvc.perform(get("/students")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentById_returnsStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);

        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(studentService).getStudentById(1L);
    }


    @Test
    void updateStudent_returnsOk() throws Exception {
        Student student = new Student();
        student.setFirstName("Updated");

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/students/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());

        verify(studentService).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    void deleteStudent_returnsNoContent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isNoContent());

        verify(studentService).deleteStudent(1L);
    }

    @Test
    void addStudent_returnsCreated() throws Exception {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setYear(2);
        student.setSpecialization("Informatics");
        student.setRole(RoleType.STUDENT);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());

        verify(studentService).addStudent(any(Student.class));
    }


}
