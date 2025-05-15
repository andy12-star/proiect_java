package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.service.api.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        log.info("Getting all courses");
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        log.info("Getting course with id {}", id);
       return courseRepository.findById(id).
               orElseThrow(() -> new RuntimeException("Course with id:"+id+" not found"));
    }

    @Override
    public Course addCourse(Course course) {
        log.info("Adding course {}", course);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        log.info("Updating course with id {}", id);
        Course updatedCourse = getCourseById(id);
        updatedCourse.setCourseName(course.getCourseName());
        updatedCourse.setCredits(course.getCredits());
        if (course.getProfessor() != null) {
            updatedCourse.setProfessor(course.getProfessor());
        }
        return courseRepository.save(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Deleting course with id {}", id);
        courseRepository.deleteById(id);
    }

    @Override
    public Course scheduleExam(Long id, LocalDate examDate) {
        log.info("Schedule exam with id {}", id);
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
            course.setExamDate(examDate);
            return courseRepository.save(course);

    }
}
