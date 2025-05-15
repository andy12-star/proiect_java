package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.EnrollmentRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Enrollment> getAllEnrollments() {
        log.info("Getting all enrollments");
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentById(Long id) {
        log.info("Getting enrollment with id {}", id);
        return enrollmentRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Enrollment with id " + id + " not found"));
    }

    @Override
    public Enrollment addEnrollment(Enrollment enrollment) {
        log.info("Adding enrollment {}", enrollment);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment updateEnrollment(Long id,String status) {
        log.info("Updating enrollment with id {}", id);
        Enrollment enrollment = getEnrollmentById(id);
        enrollment.setStatus(status);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        log.info("Deleting enrollment with id {}", id);
        enrollmentRepository.deleteById(id);
    }

    @Override
    public Enrollment enrollStudent(Long studentId, Long courseId) {
        log.info("Enrolling student with id {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("Pending");

        return enrollmentRepository.save(enrollment);
    }

}
