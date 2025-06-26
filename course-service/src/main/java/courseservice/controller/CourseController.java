package courseservice.controller;

import courseservice.model.Course;
import courseservice.model.dto.CreateCourseRequest;
import courseservice.service.CourseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Slf4j
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody @Valid CreateCourseRequest req) {
        Course course = new Course();
        course.setCourseName(req.getCourseName());
        course.setCredits(req.getCredits());
        course.setProfessorId(req.getProfessorId());

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/schedule-exam")
    public ResponseEntity<Course> scheduleExam(
            @PathVariable Long id,
            @RequestBody LocalDate examDate) {
        return ResponseEntity.ok(courseService.scheduleExam(id, examDate));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Course>> getCoursesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(courseService.getCoursesPage(pageable));
    }
}
