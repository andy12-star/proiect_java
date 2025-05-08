package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.GradeService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GradeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GradeService gradeService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private GradeController gradeController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "Bearer dummy.token.value";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(gradeController)
                .addFilter(jwtAuthenticationFilter)
                .build();

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
    void getAllGrades_returnsOk() throws Exception {
        when(gradeService.getAllGrades()).thenReturn(List.of(new Grade(), new Grade()));

        mockMvc.perform(get("/grades")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(gradeService).getAllGrades();
    }

    @Test
    void addGrade_returnsCreated() throws Exception {
        AddGradeRequest request = new AddGradeRequest(1L, 2L, 9.5);
        Grade grade = new Grade();

        when(gradeService.addGrade(any(AddGradeRequest.class))).thenReturn(grade);

        mockMvc.perform(post("/grades")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(gradeService).addGrade(any(AddGradeRequest.class));
    }

    @Test
    void addGrade_returnsBadRequest_onException() throws Exception {
        AddGradeRequest request = new AddGradeRequest(1L, 2L, 9.5);

        when(gradeService.addGrade(any(AddGradeRequest.class))).thenThrow(new RuntimeException("Grade already exists"));

        mockMvc.perform(post("/grades")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateGrade_returnsOk() throws Exception {
        Grade grade = new Grade();
        when(gradeService.updateGrade(eq(1L), eq(8.0))).thenReturn(grade);

        mockMvc.perform(put("/grades/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("8.0"))
                .andExpect(status().isOk());

        verify(gradeService).updateGrade(1L, 8.0);
    }

    @Test
    void deleteGrade_returnsNoContent() throws Exception {
        doNothing().when(gradeService).deleteGrade(1L);

        mockMvc.perform(delete("/grades/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isNoContent());

        verify(gradeService).deleteGrade(1L);
    }

    @Test
    void getGradesForStudent_returnsGrades() throws Exception {
        when(gradeService.getGradesByStudentId(1L)).thenReturn(List.of(new Grade()));

        mockMvc.perform(get("/grades/students/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(gradeService).getGradesByStudentId(1L);
    }
}
