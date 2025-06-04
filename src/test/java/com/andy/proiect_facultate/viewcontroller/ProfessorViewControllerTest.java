package com.andy.proiect_facultate.viewcontroller;

import com.andy.proiect_facultate.controller.view.ProfessorViewController;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.ProfessorService;
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

@WebMvcTest(ProfessorViewController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ProfessorViewControllerTest.MockBeansConfig.class)
class ProfessorViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfessorService professorService;

    @Test
    void testListProfessors() throws Exception {
        Professor professor = new Professor();
        professor.setId(1L);
        professor.setFirstName("Ana");

        when(professorService.getAllProfessors()).thenReturn(List.of(professor));

        mockMvc.perform(get("/view/professors"))
                .andExpect(status().isOk())
                .andExpect(view().name("professors/list"))
                .andExpect(model().attributeExists("professors"));
    }

    @Test
    void testShowProfessorDetails() throws Exception {
        Professor professor = new Professor();
        professor.setId(1L);
        professor.setFirstName("Ana");

        when(professorService.getProfessorById(1L)).thenReturn(professor);

        mockMvc.perform(get("/view/professors/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("professors/details"))
                .andExpect(model().attributeExists("professor"));
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/view/professors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("professors/add"))
                .andExpect(model().attributeExists("professor"));
    }

    @Test
    void testSaveProfessor() throws Exception {
        mockMvc.perform(post("/view/professors")
                        .param("firstName", "Ana")
                        .param("lastName", "Pop"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/professors"));
    }

    @Test
    void testShowEditForm() throws Exception {
        Professor professor = new Professor();
        professor.setId(1L);
        professor.setFirstName("Ana");

        when(professorService.getProfessorById(1L)).thenReturn(professor);

        mockMvc.perform(get("/view/professors/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("professors/update"))
                .andExpect(model().attributeExists("professor"));
    }

    @Test
    void testUpdateProfessor() throws Exception {
        mockMvc.perform(post("/view/professors/update/1")
                        .param("firstName", "Ana")
                        .param("lastName", "Pop"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/professors"));
    }

    @TestConfiguration
    static class MockBeansConfig {

        @Bean
        public ProfessorService professorService() {
            return Mockito.mock(ProfessorService.class);
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
