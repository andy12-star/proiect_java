package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.controller.API.CourseController;
import com.andy.proiect_facultate.model.dto.request.CreateCourseRequest;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.enums.RoleType;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private CourseController courseController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "Bearer dummy.token.value";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController)
                .addFilter(jwtAuthenticationFilter)
                .build();
        objectMapper.registerModule(new JavaTimeModule());
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(USERNAME)
                .password("password")
                .roles("ADMIN")
                .build();

        when(jwtUtil.extractUsername(anyString())).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(jwtUtil.isTokenValid(anyString(), eq(USERNAME))).thenReturn(true);
    }

    @Test
    void getAllCourses_returnsOk() throws Exception {
        when(courseService.getAllCourses()).thenReturn(List.of(new Course(), new Course()));

        mockMvc.perform(get("/courses")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(courseService).getAllCourses();
    }

    @Test
    void getCourseById_returnsCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/courses/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(courseService).getCourseById(1L);
    }

    @Test
    void addCourse_returnsCreated() throws Exception {
        Professor professor = new Professor();
        professor.setId(1L);
        professor.setEmail("prof@example.com");
        professor.setRole(RoleType.PROFESSOR);

        Course course = Course.builder()
                .id(1L)
                .courseName("Mathematics")
                .credits(5)
                .professor(professor)
                .build();

        CreateCourseRequest request = new CreateCourseRequest();
        request.setCourseName("Mathematics");
        request.setCredits(5);
        request.setProfessorId(1L);

        when(professorRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(professor));
        when(courseService.addCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/courses")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(courseService).addCourse(any(Course.class));
    }

    @Test
    void updateCourse_returnsOk() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Updated Course");

        when(courseService.updateCourse(eq(1L), any(Course.class))).thenReturn(course);

        mockMvc.perform(put("/courses/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk());

        verify(courseService).updateCourse(eq(1L), any(Course.class));
    }

    @Test
    void deleteCourse_returnsNoContent() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/courses/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isNoContent());

        verify(courseService).deleteCourse(1L);
    }

    @Test
    void scheduleExam_returnsOk() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setExamDate(LocalDate.now().plusDays(10));

        when(courseService.scheduleExam(eq(1L), any(LocalDate.class))).thenReturn(course);

        mockMvc.perform(put("/courses/1/schedule-exam")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LocalDate.now().plusDays(10))))
                .andExpect(status().isOk());

        verify(courseService).scheduleExam(eq(1L), any(LocalDate.class));
    }
}
