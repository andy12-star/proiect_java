package reportservice.service;

import reportservice.model.CourseReportDTO;
import reportservice.model.StudentReportDTO;

public interface ReportService {

    StudentReportDTO generateStudentReport(Long studentId);

    CourseReportDTO generateCourseReport(Long courseId);
}