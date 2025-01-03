package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.dto.StudentReportDTO;
import com.andy.proiect_facultate.dto.CourseReportDTO;

public interface ReportService {

    StudentReportDTO generateStudentReport(Long studentId);
    CourseReportDTO generateCourseReport(Long courseId);
}
