package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.ReportViewController;
import com.andy.proiect_facultate.model.dto.CourseReportDTO;
import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.ReportService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportViewController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ReportViewControllerTest.MockBeansConfig.class)
class ReportViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportService reportService;

    @Test
    void testShowStudentReport() throws Exception {
        StudentReportDTO report = StudentReportDTO.builder()
                .studentName("Ion Popescu")
                .email("ion.popescu@example.com")
                .courseNames(List.of("Matematică", "Fizică"))
                .grades(List.of(9.5, 8.0))
                .build();

        when(reportService.generateStudentReport(1L)).thenReturn(report);

        mockMvc.perform(get("/view/reports/students/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("reports/student"))
                .andExpect(model().attributeExists("studentReport"));
    }

    @Test
    void testShowCourseReport() throws Exception {
        CourseReportDTO report = CourseReportDTO.builder()
                .courseName("Programare Java")
                .studentNames(List.of("Ion Popescu", "Ana Ionescu"))
                .grades(List.of(9.0, 10.0))
                .build();

        when(reportService.generateCourseReport(2L)).thenReturn(report);

        mockMvc.perform(get("/view/reports/courses/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("reports/course"))
                .andExpect(model().attributeExists("courseReport"));
    }


    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public ReportService reportService() {
            return Mockito.mock(ReportService.class);
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
