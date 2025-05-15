package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.model.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();

    Enrollment getEnrollmentById(Long id);

    Enrollment addEnrollment(Enrollment enrollment);

    Enrollment updateEnrollment(Long id, String status);

    void deleteEnrollment(Long id);

    Enrollment enrollStudent(Long studentId, Long courseId);

    Page<Enrollment> getEnrollmentsPage(Pageable pageable);
}
