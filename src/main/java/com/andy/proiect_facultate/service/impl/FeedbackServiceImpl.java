package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.model.entity.Student;
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
    public Feedback addFeedback(Feedback feedback) {
        Student student = studentRepository.findById(feedback.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(feedback.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        feedback.setStudent(student);
        feedback.setCourse(course);
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
