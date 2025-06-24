package feedbackservice.service;


import feedbackservice.model.AddFeedbackRequest;
import feedbackservice.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedbacks();

    Feedback addFeedback(AddFeedbackRequest request);
    List<Feedback> getFeedbackByStudent(Long studentId);

    List<Feedback> getFeedbackForCourse(Long courseId);
    Page<Feedback> getFeedbackPage(Pageable pageable);
}