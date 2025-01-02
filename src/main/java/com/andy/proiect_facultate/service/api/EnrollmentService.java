package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();

    Enrollment getEnrollmentById(Long id);

    Enrollment addEnrollment(Enrollment enrollment);

    Enrollment updateEnrollment(Long id, String status);

    void deleteEnrollment(Long id);

}
