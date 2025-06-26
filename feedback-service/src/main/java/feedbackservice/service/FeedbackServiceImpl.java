package feedbackservice.service;

import feedbackservice.model.AddFeedbackRequest;
import feedbackservice.model.Feedback;
import feedbackservice.repository.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")

@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback addFeedback(AddFeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setStudentId(request.getStudentId());
        feedback.setCourseId(request.getCourseId());
        feedback.setComment(request.getComment());
        feedback.setRating(request.getRating());
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackByStudent(Long studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }

    @Override
    public List<Feedback> getFeedbackForCourse(Long courseId) {
        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public Page<Feedback> getFeedbackPage(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }
}
