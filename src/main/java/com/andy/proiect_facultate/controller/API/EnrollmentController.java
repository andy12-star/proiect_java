package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.service.api.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@Tag(name = "Enrollment Controller", description = "API for managing enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @Operation(summary = "Get all enrollments", description = "Retrieve a list of all enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        log.info("GET /enrollments");
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update enrollment details", description = "Update the details of an existing enrollment")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(@PathVariable Long id, @RequestBody String status) {
        log.info("PUT /enrollments/{id}", id);
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an enrollment", description = "Delete an enrollment record by ID")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        log.info("DELETE /enrollments/{id}", id);
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    @Operation(summary = "Enroll a student in a course", description = "Enrolls a student in a specific course")
    public ResponseEntity<Enrollment> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        log.info("POST /students/{studentId}/courses/{courseId}", studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(studentId, courseId));
    }

}
