package courseservice.service;

import courseservice.model.Course;
import courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existing = getCourseById(id);
        existing.setCourseName(course.getCourseName());
        existing.setCredits(course.getCredits());
        if (course.getProfessorId() != null) {
            existing.setProfessorId(course.getProfessorId());
        }
        return courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course scheduleExam(Long id, LocalDate examDate) {
        Course course = getCourseById(id);
        course.setExamDate(examDate);
        return courseRepository.save(course);
    }

    @Override
    public Page<Course> getCoursesPage(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}
