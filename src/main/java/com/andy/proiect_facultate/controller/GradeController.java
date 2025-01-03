package com.andy.proiect_facultate.controller;

import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.service.api.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@Tag(name = "Grade Controller", description = "API for managing grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    @Operation(summary = "Get all grades", description = "Retrieve a list of all grades")
    public ResponseEntity<List<Grade>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @PostMapping
    @Operation(summary = "Add a new garde", description = "Create a new grade record")
    public ResponseEntity<Grade> addGrade(@RequestBody @Valid Grade grade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.addGrade(grade));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a grade", description = "Update the details of an existing grade")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Double gradeValue) {
        return ResponseEntity.ok(gradeService.updateGrade(id, gradeValue));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a garde", description = "Delete a grade record by ID")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "Get grades for a student", description = "Retrieve all grades for a specific student")
    public ResponseEntity<List<Grade>> getGradesForStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudentId(studentId));
    }

}
