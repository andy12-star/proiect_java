package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.controller.API.EnrollmentController;
import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.EnrollmentService;
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

class EnrollmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "Bearer dummy.token.value";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController)
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
    void getAllEnrollments_returnsOk() throws Exception {
        when(enrollmentService.getAllEnrollments()).thenReturn(List.of(new Enrollment(), new Enrollment()));

        mockMvc.perform(get("/enrollments")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(enrollmentService).getAllEnrollments();
    }

    @Test
    void updateEnrollmentStatus_returnsOk() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentService.updateEnrollment(eq(1L), eq("APPROVED"))).thenReturn(enrollment);

        mockMvc.perform(put("/enrollments/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"APPROVED\""))
                .andExpect(status().isOk());

        verify(enrollmentService).updateEnrollment(1L, "\"APPROVED\"");
    }


    @Test
    void deleteEnrollment_returnsNoContent() throws Exception {
        doNothing().when(enrollmentService).deleteEnrollment(1L);

        mockMvc.perform(delete("/enrollments/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isNoContent());

        verify(enrollmentService).deleteEnrollment(1L);
    }

    @Test
    void enrollStudent_returnsCreated() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);

        when(enrollmentService.enrollStudent(1L, 2L)).thenReturn(enrollment);

        mockMvc.perform(post("/enrollments/students/1/courses/2")
                        .header("Authorization", TOKEN))
                .andExpect(status().isCreated());

        verify(enrollmentService).enrollStudent(1L, 2L);
    }
}
