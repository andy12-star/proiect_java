package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.entity.Enrollment;
import com.andy.proiect_facultate.service.api.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@Tag(name = "Enrollment Controller", description = "API for managing enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    @Operation(summary = "Get all enrollments", description = "Retrieve a list of all enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @PostMapping
    @Operation(summary = "Add a new enrollment", description = "Create a new enrollment record")
    public ResponseEntity<Enrollment> addEnrollment(@RequestBody @Valid Enrollment enrollment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.addEnrollment(enrollment));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update enrollment details", description = "Update the details of an existing enrollment")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an enrollment", description = "Delete an enrollment record by ID")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    @Operation(summary = "Enroll a student in a course", description = "Enrolls a student in a specific course")
    public ResponseEntity<Enrollment> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(studentId, courseId));
    }

}
