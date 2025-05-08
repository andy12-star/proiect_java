package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.model.dto.CourseReportDTO;
import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.ReportService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private ReportController reportController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String TOKEN = "Bearer dummy.token";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController)
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
    void getStudentReport_returnsOk() throws Exception {
        StudentReportDTO report = StudentReportDTO.builder()
                .studentName("John Doe")
                .email("john@example.com")
                .courseNames(List.of("Math", "Physics"))
                .grades(List.of(9.5, 8.7))
                .build();

        when(reportService.generateStudentReport(1L)).thenReturn(report);

        mockMvc.perform(get("/reports/students/1")
                        .header("Authorization", TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reportService).generateStudentReport(1L);
    }

    @Test
    void getCourseReport_returnsOk() throws Exception {
        CourseReportDTO report = CourseReportDTO.builder()
                .courseName("Mathematics")
                .studentNames(List.of("Alice Smith", "Bob Jones"))
                .grades(List.of(9.0, 8.5))
                .build();

        when(reportService.generateCourseReport(1L)).thenReturn(report);

        mockMvc.perform(get("/reports/courses/1")
                        .header("Authorization", TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(reportService).generateCourseReport(1L);
    }
}
