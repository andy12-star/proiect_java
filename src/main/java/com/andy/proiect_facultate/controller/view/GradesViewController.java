package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.service.api.CourseService;
import com.andy.proiect_facultate.service.api.GradeService;
import com.andy.proiect_facultate.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/grades")
@RequiredArgsConstructor
public class GradesViewController {

    private final GradeService gradeService;
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("grade", new Grade());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "grades/add";
    }

    @PostMapping("/add")
    public String addGrade(@ModelAttribute AddGradeRequest grade) {
        gradeService.addGrade(grade);
        return "redirect:/view/grades";
    }

    @GetMapping
    public String listGrades(Model model) {
        model.addAttribute("grades", gradeService.getAllGrades());
        return "grades/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Grade grade = gradeService.getGradeById(id);
        model.addAttribute("grade", grade);
        return "grades/update";
    }

    @PostMapping("/update/{id}")
    public String updateGrade(@PathVariable Long id, @RequestParam Double gradeValue) {
        gradeService.updateGrade(id, gradeValue);
        return "redirect:/view/grades";
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return "redirect:/view/grades";
    }
}
