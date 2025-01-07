package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.dto.CourseReportDTO;
import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.GradeRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.impl.ReportServiceImpl;
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
public class ReportServiceTest {
    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void givenStudentExists_WhenGenerateStudentReport_ThenReturnReport() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        Grade grade1 = new Grade();
        grade1.setGrade(9.0);
        Course course1 = new Course();
        course1.setCourseName("Math");
        grade1.setCourse(course1);

        Grade grade2 = new Grade();
        grade2.setGrade(8.5);
        Course course2 = new Course();
        course2.setCourseName("Science");
        grade2.setCourse(course2);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(gradeRepository.findByStudentId(1L)).thenReturn(List.of(grade1, grade2));

        StudentReportDTO report = reportService.generateStudentReport(1L);

        assertNotNull(report);
        assertEquals("John Doe", report.getStudentName());
        assertEquals("john.doe@example.com", report.getEmail());
        assertEquals(List.of("Math", "Science"), report.getCourseNames());
        assertEquals(List.of(9.0, 8.5), report.getGrades());
        verify(studentRepository, times(1)).findById(1L);
        verify(gradeRepository, times(1)).findByStudentId(1L);
    }

    @Test
    void givenStudentDoesNotExist_WhenGenerateStudentReport_ThenThrowException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> reportService.generateStudentReport(1L));
        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
        verifyNoInteractions(gradeRepository);
    }

    @Test
    void givenCourseExists_WhenGenerateCourseReport_ThenReturnCourseReport() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Math");

        Grade grade1 = new Grade();
        grade1.setGrade(9.0);
        Student student1 = new Student();
        student1.setFirstName("Alice");
        student1.setLastName("Johnson");
        grade1.setStudent(student1);

        Grade grade2 = new Grade();
        grade2.setGrade(8.5);
        Student student2 = new Student();
        student2.setFirstName("Bob");
        student2.setLastName("Smith");
        grade2.setStudent(student2);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(gradeRepository.findByCourseId(1L)).thenReturn(List.of(grade1, grade2));

        CourseReportDTO report = reportService.generateCourseReport(1L);

        assertNotNull(report);
        assertEquals("Math", report.getCourseName());
        assertEquals(List.of("Alice Johnson", "Bob Smith"), report.getStudentNames());
        assertEquals(List.of(9.0, 8.5), report.getGrades());
        verify(courseRepository, times(1)).findById(1L);
        verify(gradeRepository, times(1)).findByCourseId(1L);
    }

    @Test
    void givenCourseDoesNotExist_WhenGenerateCourseReport_ThenThrowException() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> reportService.generateCourseReport(1L));
        assertEquals("Course not found", exception.getMessage());
        verify(courseRepository, times(1)).findById(1L);
        verifyNoInteractions(gradeRepository);
    }

}
