package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.entity.Course;

import java.time.LocalDate;
import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course addCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    public Course scheduleExam(Long id, LocalDate examDate);
}
