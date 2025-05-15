package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.service.api.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@Tag(name = "Grade Controller", description = "API for managing grades")
@RequiredArgsConstructor
@Slf4j
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    @Operation(summary = "Get all grades", description = "Retrieve a list of all grades")
    public ResponseEntity<List<Grade>> getAllGrades() {
        log.info("GET /grades");
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @PostMapping
    @Operation(summary = "Add a new garde", description = "Create a new grade record")
    public ResponseEntity<?> addGrade(@RequestBody @Valid AddGradeRequest addGradeRequest) {
        log.info("POST /grades - add grade");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.addGrade(addGradeRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a grade", description = "Update the details of an existing grade")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Double gradeValue) {
        log.info("PUT /grades - update grade");
        return ResponseEntity.ok(gradeService.updateGrade(id, gradeValue));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a garde", description = "Delete a grade record by ID")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        log.info("DELETE /grades/{id} - delete grade with ID {}", id);
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "Get grades for a student", description = "Retrieve all grades for a specific student")
    public ResponseEntity<List<Grade>> getGradesForStudent(@PathVariable Long studentId) {
        log.info("GET /students/{studentId} - retrieve grade for student with id : {}", studentId);
        return ResponseEntity.ok(gradeService.getGradesByStudentId(studentId));
    }

    // paging
    @GetMapping("/page")
    public ResponseEntity<Page<Grade>> getGradesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(gradeService.getGradesPage(pageable));
    }
}
