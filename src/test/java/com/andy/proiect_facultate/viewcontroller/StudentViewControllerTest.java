package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.StudentViewController;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentViewController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(StudentViewControllerTest.MockBeansConfig.class)
class StudentViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Test
    void testListStudents() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Maria");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/view/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    void testShowStudentDetails() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Maria");

        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/view/students/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/details"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/view/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/add"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void testSaveStudent() throws Exception {
        mockMvc.perform(post("/view/students")
                        .param("firstName", "Maria")
                        .param("lastName", "Pop")
                        .param("email", "maria.pop@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/students"));
    }

    @Test
    void testShowEditForm() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Maria");

        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/view/students/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/update"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        mockMvc.perform(post("/view/students/update/1")
                        .param("firstName", "Maria")
                        .param("lastName", "Pop")
                        .param("email", "maria.pop@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/students"));
    }

    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }

        @Bean
        public JwtUtil jwtUtil() {
            JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);
            Mockito.when(jwtUtil.extractUsername(Mockito.anyString())).thenReturn("testuser");
            Mockito.when(jwtUtil.isTokenValid(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
            return jwtUtil;
        }

        @Bean
        public CustomUserDetailsService customUserDetailsService() {
            CustomUserDetailsService uds = Mockito.mock(CustomUserDetailsService.class);
            Mockito.when(uds.loadUserByUsername(Mockito.anyString()))
                    .thenReturn(org.springframework.security.core.userdetails.User
                            .withUsername("testuser")
                            .password("password")
                            .authorities("ROLE_USER")
                            .build());
            return uds;
        }
    }
}
