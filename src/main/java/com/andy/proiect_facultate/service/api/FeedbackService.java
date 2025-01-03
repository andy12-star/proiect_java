package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getAllFeedbacks();
    Feedback addFeedback(Feedback feedback);
    List<Feedback> getFeedbackByCourseId(Long courseId);

    List<Feedback> getFeedbackByStudent(Long studentId);

    List<Feedback> getFeedbackForCourse(Long courseId);
}
