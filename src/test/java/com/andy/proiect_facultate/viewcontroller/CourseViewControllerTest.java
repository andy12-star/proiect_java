package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.CourseViewController;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseViewController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProfessorRepository professorRepository;

    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public CourseService courseService() {
            return Mockito.mock(CourseService.class);
        }

        @Bean
        public ProfessorRepository professorRepository() {
            return Mockito.mock(ProfessorRepository.class);
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

    @Test
    void testListCourses() throws Exception {
        List<Course> mockCourses = List.of(new Course());
        when(courseService.getAllCourses()).thenReturn(mockCourses);

        mockMvc.perform(get("/view/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/list"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("courses", mockCourses));
    }

    @Test
    void testViewCourse() throws Exception {
        Professor prof = new Professor();
        prof.setFirstName("Andrei");
        prof.setLastName("Pop");

        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Fundamentals");
        course.setProfessor(prof);

        when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/view/courses/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/details"))
                .andExpect(model().attribute("course", course));
    }


    @Test
    void testShowAddForm() throws Exception {
        when(professorRepository.findAll()).thenReturn(List.of(new Professor()));

        mockMvc.perform(get("/view/courses/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/add"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("professors"));
    }

    @Test
    void testAddCourse() throws Exception {
        mockMvc.perform(post("/view/courses")
                        .param("name", "Test Course"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/courses"));
    }

    @Test
    void testShowEditForm() throws Exception {
        Course course = new Course();
        course.setId(1L);
        when(courseService.getCourseById(1L)).thenReturn(course);
        when(professorRepository.findAll()).thenReturn(List.of(new Professor()));

        mockMvc.perform(get("/view/courses/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/update"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("professors"));
    }

    @Test
    void testUpdateCourse() throws Exception {
        mockMvc.perform(post("/view/courses/update/1")
                        .param("name", "Updated Course"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/courses"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        mockMvc.perform(post("/view/courses/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/courses"));
    }
}
