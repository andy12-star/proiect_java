package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.dto.request.CreateCourseRequest;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.service.api.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course Controller", description = "API for managing students")
public class CourseController {

    private final CourseService courseService;
    private final ProfessorRepository professorRepository;

    public CourseController(CourseService courseService, ProfessorRepository professorRepository) {
        this.courseService = courseService;
        this.professorRepository = professorRepository;
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve a list of all courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the course with a specific id: ", description = "Retrieve a course")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @Operation(summary = "Add a new course", description = "Create a new course record")
    public ResponseEntity<Course> addCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        Course course = Course.builder()
                .courseName(createCourseRequest.getCourseName())
                .credits(createCourseRequest.getCredits())
                .professor(professorRepository.findById(createCourseRequest.getProfessorId()).orElse(null))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course details", description = "Update the details of an existing course")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", description = "Delete a course record by ID")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/schedule-exam")
    @Operation(summary = "Schedule an exam", description = "Schedules an exam for a specific course")
    public ResponseEntity<Course> scheduleExam(
            @PathVariable Long id,
            @RequestBody LocalDate examDate) {
        return ResponseEntity.ok(courseService.scheduleExam(id, examDate));
    }

}
