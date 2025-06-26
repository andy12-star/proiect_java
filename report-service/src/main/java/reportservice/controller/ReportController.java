package reportservice.controller;

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
public class ReportController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentReportDTO> getStudentReport(@PathVariable Long studentId) {
        return ResponseEntity.ok(reportService.generateStudentReport(studentId));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseReportDTO> getCourseReport(@PathVariable Long courseId) {

        log.info("GET /reports/courses/{}", courseId);
        return ResponseEntity.ok(reportService.generateCourseReport(courseId));
    }
}