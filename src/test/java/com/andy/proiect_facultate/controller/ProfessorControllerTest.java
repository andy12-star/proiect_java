package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.enums.RoleType;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.impl.ProfessorServiceImpl;
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

public class ProfessorControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProfessorServiceImpl professorService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private ProfessorController professorController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "Bearer dummy.token.value";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(professorController)
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
    void getAllProfessors_returnsOk() throws Exception {
        when(professorService.getAllProfessors()).thenReturn(List.of(new Professor(), new Professor()));

        mockMvc.perform(get("/professors")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(professorService).getAllProfessors();
    }

    @Test
    void getProfessorById_returnsOk() throws Exception {
        Professor professor = new Professor();
        professor.setId(1L);

        when(professorService.getProfessorById(1L)).thenReturn(professor);

        mockMvc.perform(get("/professors/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(professorService).getProfessorById(1L);
    }

    @Test
    void addProfessor_returnsCreated() throws Exception {
        Professor professor = new Professor();
        professor.setFirstName("Ada");
        professor.setLastName("Lovelace");
        professor.setEmail("ada@university.com");
        professor.setDepartment("Informatics");
        professor.setRole(RoleType.PROFESSOR);

        when(professorService.addProfessor(any(Professor.class))).thenReturn(professor);

        mockMvc.perform(post("/professors")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(professor)))
                .andExpect(status().isCreated());

        verify(professorService).addProfessor(any(Professor.class));
    }

    @Test
    void updateProfessor_returnsOk() throws Exception {
        Professor professor = new Professor();
        professor.setFirstName("Updated");

        when(professorService.updateProfessor(eq(1L), any(Professor.class))).thenReturn(professor);

        mockMvc.perform(put("/professors/1")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(professor)))
                .andExpect(status().isOk());

        verify(professorService).updateProfessor(eq(1L), any(Professor.class));
    }

    @Test
    void deleteProfessor_returnsNoContent() throws Exception {
        doNothing().when(professorService).deleteProfessor(1L);

        mockMvc.perform(delete("/professors/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isNoContent());

        verify(professorService).deleteProfessor(1L);
    }
}
