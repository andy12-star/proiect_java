package enrollmentservice.controller;

import enrollmentservice.model.Enrollment;
import enrollmentservice.service.EnrollmentService;
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
@RequestMapping("/enrollments")
@Slf4j
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Enrollment>> getEnrollmentsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(enrollmentService.getEnrollmentsPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Enrollment> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentService.enrollStudent(studentId, courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(
            @PathVariable Long id,
            @RequestBody String status) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
