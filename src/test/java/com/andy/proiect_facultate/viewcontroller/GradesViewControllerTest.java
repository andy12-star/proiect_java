package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.GradesViewController;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.CourseService;
import com.andy.proiect_facultate.service.api.GradeService;
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

@WebMvcTest(GradesViewController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GradesViewControllerTest.MockBeansConfig.class)
class GradesViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Test
    void testShowAddForm() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(new Student()));
        when(courseService.getAllCourses()).thenReturn(List.of(new Course()));

        mockMvc.perform(get("/view/grades/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("grades/add"))
                .andExpect(model().attributeExists("grade"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void testAddGrade() throws Exception {
        mockMvc.perform(post("/view/grades/add")
                        .param("studentId", "1")
                        .param("courseId", "2")
                        .param("gradeValue", "9.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/grades"));
    }

    @Test
    void testListGrades() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);
        when(gradeService.getAllGrades()).thenReturn(List.of(grade));

        mockMvc.perform(get("/view/grades"))
                .andExpect(status().isOk())
                .andExpect(view().name("grades/list"))
                .andExpect(model().attributeExists("grades"));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);
        when(gradeService.getGradeById(1L)).thenReturn(grade);

        mockMvc.perform(get("/view/grades/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("grades/update"))
                .andExpect(model().attributeExists("grade"));
    }

    @Test
    void testUpdateGrade() throws Exception {
        mockMvc.perform(post("/view/grades/update/1")
                        .param("gradeValue", "8.75"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/grades"));
    }

    @Test
    void testDeleteGrade() throws Exception {
        mockMvc.perform(get("/view/grades/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/grades"));
    }

    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public GradeService gradeService() {
            return Mockito.mock(GradeService.class);
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
