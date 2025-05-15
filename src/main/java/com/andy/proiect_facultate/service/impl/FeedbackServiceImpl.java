package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.FeedbackRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Feedback> getAllFeedbacks() {
        log.info("Getting all feedbacks");
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback addFeedback(AddFeedbackRequest addFeedbackRequest) {
        log.info("Adding feedback");
        Feedback feedback = Feedback.builder()
                .student(studentRepository.findById(addFeedbackRequest.getStudentId())
                        .orElseThrow(() -> new IllegalArgumentException("Student not found")))
                .course(courseRepository.findById(addFeedbackRequest.getCourseId())
                        .orElseThrow(() -> new IllegalArgumentException("Course not found")))
                .comment(addFeedbackRequest.getComment())
                .rating(addFeedbackRequest.getRating())
                .build();
        return feedbackRepository.save(feedback);
    }


    @Override
    public List<Feedback> getFeedbackByCourseId(Long courseId) {
        log.info("Getting feedbacks by course with id {}", courseId);
        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public List<Feedback> getFeedbackForCourse(Long courseId) {
        log.info("Getting feedbacks by course with id {}", courseId);
        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public List<Feedback> getFeedbackByStudent(Long studentId) {
        log.info("Getting feedbacks by student with id {}", studentId);
        return feedbackRepository.findByStudentId(studentId);
    }
}
