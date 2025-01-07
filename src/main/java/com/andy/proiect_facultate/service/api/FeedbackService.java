package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getAllFeedbacks();

    Feedback addFeedback(AddFeedbackRequest addFeedbackRequest);
    List<Feedback> getFeedbackByCourseId(Long courseId);

    List<Feedback> getFeedbackByStudent(Long studentId);

    List<Feedback> getFeedbackForCourse(Long courseId);
}
