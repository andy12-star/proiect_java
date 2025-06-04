package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.FeedbackViewController;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.CourseService;
import com.andy.proiect_facultate.service.api.FeedbackService;
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

@WebMvcTest(FeedbackViewController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(FeedbackViewControllerTest.MockBeansConfig.class)
class FeedbackViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Test
    void testListFeedbacks() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        when(feedbackService.getAllFeedbacks()).thenReturn(List.of(feedback));

        mockMvc.perform(get("/view/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(view().name("feedbacks/list"))
                .andExpect(model().attributeExists("feedbacks"));
    }

    @Test
    void testShowAddForm() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(new Student()));
        when(courseService.getAllCourses()).thenReturn(List.of(new Course()));

        mockMvc.perform(get("/view/feedbacks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("feedbacks/add"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void testAddFeedback() throws Exception {
        mockMvc.perform(post("/view/feedbacks/add")
                        .param("studentId", "1")
                        .param("courseId", "2")
                        .param("comment", "Great course!")
                        .param("rating", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/feedbacks"));
    }

    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public FeedbackService feedbackService() {
            return Mockito.mock(FeedbackService.class);
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
