package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.FeedbackRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.feedbackRepository = feedbackRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }


    @Override
    public Feedback addFeedback(AddFeedbackRequest addFeedbackRequest) {
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
        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public List<Feedback> getFeedbackForCourse(Long courseId) {
        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public List<Feedback> getFeedbackByStudent(Long studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }
}
