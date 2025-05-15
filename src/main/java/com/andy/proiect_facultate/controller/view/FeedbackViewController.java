package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.dto.request.AddFeedbackRequest;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.service.api.CourseService;
import com.andy.proiect_facultate.service.api.FeedbackService;
import com.andy.proiect_facultate.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/view/feedbacks")
@RequiredArgsConstructor
public class FeedbackViewController {

    private final FeedbackService feedbackService;
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping
    public String listFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedbacks/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "feedbacks/add";
    }

    @PostMapping("/add")
    public String addFeedback(@RequestParam Long studentId,
                              @RequestParam Long courseId,
                              @RequestParam String comment,
                              @RequestParam Integer rating) {
        AddFeedbackRequest request = new AddFeedbackRequest(studentId, courseId, comment, rating);
        feedbackService.addFeedback(request);
        return "redirect:/view/feedbacks";
    }
}
