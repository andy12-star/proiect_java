package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.EnrollmentViewController;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.CourseService;
import com.andy.proiect_facultate.service.api.EnrollmentService;
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

@WebMvcTest(EnrollmentViewController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EnrollmentViewControllerTest.MockBeansConfig.class)
class EnrollmentViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Test
    void testListEnrollments() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        when(enrollmentService.getAllEnrollments()).thenReturn(List.of(enrollment));

        mockMvc.perform(get("/view/enrollments"))
                .andExpect(status().isOk())
                .andExpect(view().name("enrollments/list"))
                .andExpect(model().attributeExists("enrollments"));
    }

    @Test
    void testShowAddForm() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(new Student()));
        when(courseService.getAllCourses()).thenReturn(List.of(new Course()));

        mockMvc.perform(get("/view/enrollments/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("enrollments/add"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("enrollment"));
    }

    @Test
    void testAddEnrollment() throws Exception {
        mockMvc.perform(post("/view/enrollments/add")
                        .param("studentId", "1")
                        .param("courseId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/enrollments"));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment);

        mockMvc.perform(get("/view/enrollments/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("enrollments/update"))
                .andExpect(model().attributeExists("enrollment"));
    }

    @Test
    void testUpdateEnrollment() throws Exception {
        mockMvc.perform(post("/view/enrollments/update/1")
                        .param("status", "APPROVED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/enrollments"));
    }

    @Test
    void testDeleteEnrollment() throws Exception {
        mockMvc.perform(get("/view/enrollments/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/enrollments"));
    }

    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public EnrollmentService enrollmentService() {
            return Mockito.mock(EnrollmentService.class);
        }

        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }

        @Bean
        public CourseService courseService() {
            return Mockito.mock(CourseService.class);
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
