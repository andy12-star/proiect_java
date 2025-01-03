package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.entity.Course;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.service.api.CourseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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
       return courseRepository.findById(id).
               orElseThrow(() -> new RuntimeException("Course with id:"+id+" not found"));
    }

    @Override
    public Course addCourse(Course course) {
       return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course updatedCourse = getCourseById(id);
        updatedCourse.setCourseName(course.getCourseName());
        updatedCourse.setCredits(course.getCredits());
        updatedCourse.setProfessor(course.getProfessor());
        return courseRepository.save(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course scheduleExam(Long id, LocalDate examDate) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));

            course.setExamDate(examDate);
            return courseRepository.save(course);

    }
}
