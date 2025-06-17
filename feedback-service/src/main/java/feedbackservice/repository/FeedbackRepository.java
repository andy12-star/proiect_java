package feedbackservice.repository;

import feedbackservice.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCourseId(Long courseId);

    List<Feedback> findByStudentId(Long studentId);

    Page<Feedback> findAll(Pageable pageable);
}