package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.dto.CourseReportDTO;
import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.service.api.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/reports")
public class ReportViewController {

    private final ReportService reportService;

    public ReportViewController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/students/{studentId}")
    public String showStudentReport(@PathVariable Long studentId, Model model) {
        StudentReportDTO studentReport = reportService.generateStudentReport(studentId);
        model.addAttribute("studentReport", studentReport);
        return "reports/student";
    }

    @GetMapping("/courses/{courseId}")
    public String showCourseReport(@PathVariable Long courseId, Model model) {
        CourseReportDTO courseReport = reportService.generateCourseReport(courseId);
        model.addAttribute("courseReport", courseReport);
        return "reports/course";
    }
}
