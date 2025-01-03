package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.dto.StudentReportDTO;
import com.andy.proiect_facultate.dto.CourseReportDTO;
import com.andy.proiect_facultate.service.api.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@Tag(name="Report Controller", description = "api for managing students")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "get a students report",description = "retrieve the report for a given student")
    public ResponseEntity<StudentReportDTO> getStudentReport(@PathVariable Long studentId) {
        return ResponseEntity.ok(reportService.generateStudentReport(studentId));
    }

    @GetMapping("/courses/{courseId}")
    @Operation(summary = "get a course report",description = "retrieve the report for a given course")
    public ResponseEntity<CourseReportDTO> getCourseReport(@PathVariable Long courseId) {
        return ResponseEntity.ok(reportService.generateCourseReport(courseId));
    }
}
