package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.entity.Feedback;
import com.andy.proiect_facultate.service.api.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@Tag(name="Feedback Controller",description = "api for managing feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @Operation(summary = "add feedback",description = "create a new feedback")
    public ResponseEntity<Feedback> addFeedback(@RequestBody @Valid  Feedback feedback) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(feedback));
    }

    @GetMapping("/courses/{courseId}")
    @Operation(summary = "get feedback for a course")
    public ResponseEntity<List<Feedback>> getFeedbackForCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(feedbackService.getFeedbackForCourse(courseId));
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "get feedback by student")
    public ResponseEntity<List<Feedback>> getFeedbackByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByStudent(studentId));
    }
}
