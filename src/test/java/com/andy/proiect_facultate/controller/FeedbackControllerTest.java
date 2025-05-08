package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.security.CustomUserDetailsService;
import com.andy.proiect_facultate.security.JwtAuthenticationFilter;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.api.FeedbackService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedbackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private FeedbackController feedbackController;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "Bearer dummy.token.value";
    private static final String USERNAME = "user@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController)
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
    void addFeedback_returnsCreated() throws Exception {
        AddFeedbackRequest request = new AddFeedbackRequest(1L, 1L, "Great course!", 5);
        Feedback feedback = new Feedback();

        when(feedbackService.addFeedback(any(AddFeedbackRequest.class))).thenReturn(feedback);

        mockMvc.perform(post("/feedbacks")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(feedbackService).addFeedback(any(AddFeedbackRequest.class));
    }

    @Test
    void getFeedbackForCourse_returnsOk() throws Exception {
        when(feedbackService.getFeedbackForCourse(1L)).thenReturn(List.of(new Feedback(), new Feedback()));

        mockMvc.perform(get("/feedbacks/courses/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(feedbackService).getFeedbackForCourse(1L);
    }

    @Test
    void getFeedbackByStudent_returnsOk() throws Exception {
        when(feedbackService.getFeedbackByStudent(1L)).thenReturn(List.of(new Feedback()));

        mockMvc.perform(get("/feedbacks/students/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(feedbackService).getFeedbackByStudent(1L);
    }

    @Test
    void getFeedbackByStudent_returnsBadRequest_whenExceptionThrown() throws Exception {
        when(feedbackService.getFeedbackByStudent(1L)).thenThrow(new RuntimeException("Student not found"));

        mockMvc.perform(get("/feedbacks/students/1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Student not found"));

        verify(feedbackService).getFeedbackByStudent(1L);
    }
}