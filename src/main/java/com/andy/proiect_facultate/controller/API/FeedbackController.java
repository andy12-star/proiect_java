package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.service.api.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@Tag(name="Feedback Controller",description = "api for managing feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(summary = "add feedback",description = "create a new feedback")
    public ResponseEntity<Feedback> addFeedback(@RequestBody @Valid AddFeedbackRequest addFeedbackRequest) {
        log.info("POST /feedbacks - add feedback request: {}", addFeedbackRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(addFeedbackRequest));
    }

    @GetMapping("/courses/{courseId}")
    @Operation(summary = "get feedback for a course")
    public ResponseEntity<List<Feedback>> getFeedbackForCourse(@PathVariable Long courseId) {
        log.info("GET /feedbacks for course {}", courseId);
        return ResponseEntity.ok(feedbackService.getFeedbackForCourse(courseId));
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "get feedback by student")
    public ResponseEntity<?> getFeedbackByStudent(@PathVariable Long studentId) {
        log.info("GET /feedbacks by student {}", studentId);
        try {
            return ResponseEntity.ok(feedbackService.getFeedbackByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // paging
    @GetMapping("/page")
    public ResponseEntity<Page<Feedback>> getFeedbacksPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(feedbackService.getFeedbackPage(pageable));
    }
}
