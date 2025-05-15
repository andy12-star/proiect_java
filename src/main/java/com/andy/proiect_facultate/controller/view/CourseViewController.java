package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.service.api.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view/courses")
@RequiredArgsConstructor
public class CourseViewController {

    private final CourseService courseService;
    private final ProfessorRepository professorRepository;

    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "courses/details";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("professors", professorRepository.findAll());
        return "courses/add";
    }

    @PostMapping
    public String addCourse(@ModelAttribute Course course) {
        courseService.addCourse(course);
        return "redirect:/view/courses";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("professors", professorRepository.findAll());
        return "courses/update";
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course) {
        courseService.updateCourse(id, course);
        return "redirect:/view/courses";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/view/courses";
    }
}
