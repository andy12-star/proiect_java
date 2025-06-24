package reportservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reportservice.model.CourseReportDTO;
import reportservice.model.StudentReportDTO;
import reportservice.service.ReportService;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentReportDTO> getStudentReport(@PathVariable Long studentId) {
        log.info("GET /reports/students/{}", studentId);
        return ResponseEntity.ok(reportService.generateStudentReport(studentId));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseReportDTO> getCourseReport(@PathVariable Long courseId) {

        log.info("GET /reports/courses/{}", courseId);
        return ResponseEntity.ok(reportService.generateCourseReport(courseId));
    }
}