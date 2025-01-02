package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.entity.Enrollment;
import com.andy.proiect_facultate.repository.EnrollmentRepository;
import com.andy.proiect_facultate.service.api.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Enrollment with id " + id + " not found"));
    }

    @Override
    public Enrollment addEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment updateEnrollment(Long id,String status) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollment.setStatus(status);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

}
