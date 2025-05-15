package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.dto.CourseReportDTO;
import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.service.api.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@Tag(name="Report Controller", description = "api for managing students")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/students/{studentId}")
    @Operation(summary = "get a students report",description = "retrieve the report for a given student")
    public ResponseEntity<StudentReportDTO> getStudentReport(@PathVariable Long studentId) {
        log.info("GET /reports/students/{}", studentId);
        return ResponseEntity.ok(reportService.generateStudentReport(studentId));
    }

    @GetMapping("/courses/{courseId}")
    @Operation(summary = "get a course report",description = "retrieve the report for a given course")
    public ResponseEntity<CourseReportDTO> getCourseReport(@PathVariable Long courseId) {

        log.info("GET /reports/courses/{}", courseId);
        return ResponseEntity.ok(reportService.generateCourseReport(courseId));
    }
}
