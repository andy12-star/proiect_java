package feedbackservice.controller;

import feedbackservice.model.AddFeedbackRequest;
import feedbackservice.model.Feedback;
import feedbackservice.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody AddFeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(request));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<Feedback>> getFeedbackForCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(feedbackService.getFeedbackForCourse(courseId));
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<Feedback>> getFeedbackByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByStudent(studentId));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Feedback>> getFeedbacksPage(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(feedbackService.getFeedbackPage(pageable));
    }
}
