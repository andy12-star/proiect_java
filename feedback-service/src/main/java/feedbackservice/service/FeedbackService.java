package feedbackservice.service;


import feedbackservice.model.Feedback;
import feedbackservice.model.FeedbackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedbacks();

    Feedback addFeedback(FeedbackRequest request);

    List<Feedback> getFeedbackForCourse(Long courseId);

    List<Feedback> getFeedbackByStudent(Long studentId);

    Page<Feedback> getFeedbackPage(Pageable pageable);
}
