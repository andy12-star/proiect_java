package courseservice.service;

import courseservice.model.Course;
import courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course with id: " + id + " not found"));
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course updated = getCourseById(id);
        updated.setCourseName(course.getCourseName());
        updated.setCredits(course.getCredits());
        if (course.getProfessorId() != null) {
            updated.setProfessorId(course.getProfessorId());
        }
        return courseRepository.save(updated);
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
