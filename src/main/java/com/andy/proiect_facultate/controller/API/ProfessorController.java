package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.service.api.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professors")
@Tag(name = "Professor Controller", description = "API for managing professors")
@RequiredArgsConstructor
@Slf4j
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    @Operation(summary="Get all professors",description = "Retrieve a list of all professors")
    public ResponseEntity<List<Professor>> getAllProfessors() {
        log.info("GET /professors");
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the professor with a specific id: ", description = "Retrieve a professor")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        log.info("GET /professors/{} ", id);
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }

    @PostMapping
    @Operation(summary = "Add a new professor", description = "Create a new professor record")
    public ResponseEntity<Professor> addProfessor(@RequestBody @Valid Professor professor) {
        log.info("POST /professors {} - add a new professor ", professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.addProfessor(professor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update professor details", description = "Update the details of an existing professor")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        log.info("PUT /professors/{} - updated professor ", id);
        return ResponseEntity.ok(professorService.updateProfessor(id, professor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a professor", description = "Delete a professor record by ID")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        log.info("DELETE /professors/{} - delete professor with id ", id);
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
