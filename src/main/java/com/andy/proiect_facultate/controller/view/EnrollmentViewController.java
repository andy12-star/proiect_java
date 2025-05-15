package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.service.api.CourseService;
import com.andy.proiect_facultate.service.api.EnrollmentService;
import com.andy.proiect_facultate.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view/enrollments")
@RequiredArgsConstructor
public class EnrollmentViewController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping
    public String listEnrollments(Model model) {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        model.addAttribute("enrollments", enrollments);
        return "enrollments/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("enrollment", new Enrollment());
        return "enrollments/add";
    }

    @PostMapping("/add")
    public String addEnrollment(@RequestParam Long studentId,
                                @RequestParam Long courseId) {
        enrollmentService.enrollStudent(studentId, courseId);
        return "redirect:/view/enrollments";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        model.addAttribute("enrollment", enrollment);
        return "enrollments/update";
    }

    @PostMapping("/update/{id}")
    public String updateEnrollment(@PathVariable Long id,
                                   @RequestParam String status) {
        enrollmentService.updateEnrollment(id, status);
        return "redirect:/view/enrollments";
    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return "redirect:/view/enrollments";
    }
}
