package com.andy.proiect_facultate.controller.view;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.service.api.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/professors")
@RequiredArgsConstructor
public class ProfessorViewController {

    private final ProfessorService professorService;

    @GetMapping
    public String listProfessors(Model model) {
        model.addAttribute("professors", professorService.getAllProfessors());
        return "professors/list";
    }

    @GetMapping("/{id}")
    public String showProfessorDetails(@PathVariable Long id, Model model) {
        Professor professor = professorService.getProfessorById(id);
        model.addAttribute("professor", professor);
        return "professors/details";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "professors/add";
    }

    @PostMapping
    public String saveProfessor(@ModelAttribute Professor professor) {
        professorService.addProfessor(professor);
        return "redirect:/view/professors";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Professor professor = professorService.getProfessorById(id);
        model.addAttribute("professor", professor);
        return "professors/update";
    }

    @PostMapping("/update/{id}")
    public String updateProfessor(@PathVariable Long id, @ModelAttribute("professor") Professor updatedProfessor) {
        professorService.updateProfessor(id, updatedProfessor);
        return "redirect:/view/professors";
    }
}
