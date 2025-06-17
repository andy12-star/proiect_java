package feedbackservice.service;

import feedbackservice.model.Feedback;
import feedbackservice.model.FeedbackRequest;
import feedbackservice.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback addFeedback(FeedbackRequest request) {
        Feedback feedback = Feedback.builder()
                .studentId(request.getStudentId())
                .courseId(request.getCourseId())
                .comment(request.getComment())
                .rating(request.getRating())
                .build();
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackForCourse(Long courseId) {
        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public List<Feedback> getFeedbackByStudent(Long studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }

    @Override
    public Page<Feedback> getFeedbackPage(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }
}