package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/students")
@RequiredArgsConstructor
public class StudentViewController {

    private final StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    @GetMapping("/{id}")
    public String showStudentDetails(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "students/details";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/add";
    }

    @PostMapping
    public String saveStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/view/students";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "students/update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student updatedStudent) {
        studentService.updateStudent(id, updatedStudent);
        return "redirect:/view/students";
    }

}
