package com.andy.proiect_facultate.service;


import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.FeedbackRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {
    @InjectMocks
    private FeedbackServiceImpl feedbackService;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @Test
    void GivenFeedbacksInRepository_WhenGetAllFeedbacks_ThenReturnAllFeedbacks() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback2, feedback1));

        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

        assertNotNull(feedbacks);
        assertEquals(2, feedbacks.size());
        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    void givenValidRequest_WhenAddFeedback_ThenSaveAndReturnFeedback() {
        AddFeedbackRequest request = new AddFeedbackRequest(1L, 1L, "Great course", 5);
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(1L);
        Feedback feedback = new Feedback();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback savedFeedback = feedbackService.addFeedback(request);

        assertNotNull(savedFeedback);
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void givenInvalidStudent_WhenAddFeedback_ThenThrowException() {
        AddFeedbackRequest request = new AddFeedbackRequest(1L, 1L, "Great course", 5);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> feedbackService.addFeedback(request));

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidCourse_WhenAddFeedback_ThenThrowException() {
        AddFeedbackRequest request = new AddFeedbackRequest(1L, 1L, "Great course", 5);
        Student student = new Student();
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> feedbackService.addFeedback(request));

        assertEquals("Course not found", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void givenCourseId_WhenGetFeedbackByCourseId_ThenReturnFeedbacks() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        when(feedbackRepository.findByCourseId(1L)).thenReturn(List.of(feedback1, feedback2));

        List<Feedback> feedbacks = feedbackService.getFeedbackByCourseId(1L);

        assertNotNull(feedbacks);
        assertEquals(2, feedbacks.size());
        verify(feedbackRepository, times(1)).findByCourseId(1L);
    }

    @Test
    void givenStudentId_WhenGetFeedbackByStudent_ThenReturnFeedbacks() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        when(feedbackRepository.findByStudentId(1L)).thenReturn(List.of(feedback1, feedback2));

        List<Feedback> feedbacks = feedbackService.getFeedbackByStudent(1L);

        assertNotNull(feedbacks);
        assertEquals(2, feedbacks.size());
        verify(feedbackRepository, times(1)).findByStudentId(1L);
    }

}
