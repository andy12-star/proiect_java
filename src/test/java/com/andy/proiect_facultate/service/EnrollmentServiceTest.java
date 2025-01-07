package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Enrollment;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.EnrollmentRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @Test
    void givenEnrollmentsExist_WhenGetAllEnrollments_ThenReturnAllEnrollments() {
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment1, enrollment2));

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void givenEnrollmentExists_WhenGetEnrollmentById_ThenReturnEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        Enrollment foundEnrollment = enrollmentService.getEnrollmentById(1L);

        assertNotNull(foundEnrollment);
        assertEquals(1L, foundEnrollment.getId());
        verify(enrollmentRepository, times(1)).findById(1L);
    }


    @Test
    void givenEnrollmentDoesNotExist_WhenGetEnrollmentById_ThenThrowException() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> enrollmentService.getEnrollmentById(1L));

        assertEquals("Enrollment with id 1 not found", exception.getMessage());
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void givenValidEnrollment_WhenAddEnrollment_ThenSaveAndReturnEnrollment() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        Enrollment savedEnrollment = enrollmentService.addEnrollment(enrollment);

        assertNotNull(savedEnrollment);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    void givenEnrollmentExists_WhenUpdateEnrollment_ThenSaveAndReturnUpdatedEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStatus("Pending");

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(1L, "Approved");

        assertNotNull(updatedEnrollment);
        assertEquals("Approved", updatedEnrollment.getStatus());
        verify(enrollmentRepository, times(1)).findById(1L);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    void givenEnrollmentId_WhenDeleteEnrollment_ThenVerifyDeletion() {
        doNothing().when(enrollmentRepository).deleteById(1L);

        enrollmentService.deleteEnrollment(1L);

        verify(enrollmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void givenValidStudentAndCourseIds_WhenEnrollStudent_ThenSaveAndReturnEnrollment() {
        Student student = new Student();
        student.setId(1L);

        Course course = new Course();
        course.setId(1L);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("Pending");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment savedEnrollment = enrollmentService.enrollStudent(1L, 1L);

        assertNotNull(savedEnrollment);
        assertEquals(student, savedEnrollment.getStudent());
        assertEquals(course, savedEnrollment.getCourse());
        assertEquals("Pending", savedEnrollment.getStatus());
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void givenInvalidStudentId_WhenEnrollStudent_ThenThrowException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> enrollmentService.enrollStudent(1L, 1L));

        assertEquals("Student not found with ID: 1", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
        verifyNoInteractions(courseRepository, enrollmentRepository);
    }

    @Test
    void givenInvalidCourseId_WhenEnrollStudent_ThenThrowException() {
        Student student = new Student();
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> enrollmentService.enrollStudent(1L, 1L));

        assertEquals("Course not found with ID: 1", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verifyNoInteractions(enrollmentRepository);
    }
}
