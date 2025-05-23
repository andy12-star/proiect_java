package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.service.api.StudentService;
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
@RequestMapping("/students")
@Tag(name = "Student Controller", description = "API for managing students")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("GET /studnents - Get all students");
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the student with a specific id: ", description = "Retrieve a student")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        log.info("GET /studnents/{}", id);
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    @Operation(summary = "Add a new student", description = "Create a new student record")
    public ResponseEntity<Student> addStudent(@RequestBody @Valid Student student) {
        log.info("POST /studnents/{} - add a new student", student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(student));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student details", description = "Update the details of an existing student")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        log.info("PUT /studnents/{} - update student", id);
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Delete a student record by ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.info("DELETE /studnents/{} - delete student", id);
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // paging
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getStudentsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(studentService.getStudentsPage(pageable));
    }
}
