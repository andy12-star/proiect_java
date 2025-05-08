package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.model.enums.RoleType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Rollback
    void canSaveAndRetrieveEnrollment() {
        Professor professor = Professor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("prof@example.com")
                .password("pass123")
                .department("Chemistry")
                .role(RoleType.PROFESSOR)
                .build();
        professorRepository.save(professor);

        Student student = Student.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("student@example.com")
                .password("pass123")
                .role(RoleType.STUDENT)
                .specialization("Informatics")
                .year(2)
                .build();
        studentRepository.save(student);

        Course course = Course.builder()
                .courseName("Algorithms")
                .credits(5)
                .professor(professor)
                .build();
        courseRepository.save(course);

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status("ENROLLED")
                .build();
        enrollmentRepository.save(enrollment);

        List<Enrollment> found = enrollmentRepository.findAll();
        assertEquals(3, found.size());
        assertEquals("ENROLLED", found.get(2).getStatus());
    }

}