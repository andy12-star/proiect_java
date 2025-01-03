package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.model.dto.CourseReportDTO;

public interface ReportService {

    StudentReportDTO generateStudentReport(Long studentId);
    CourseReportDTO generateCourseReport(Long courseId);
}
