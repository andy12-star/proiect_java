package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.GradeRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.impl.GradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    @Test
    void givenGradesInRepository_WhenGetAllGrades_ThenReturnAllGrades() {
        Grade grade = new Grade();
        when(gradeRepository.findAll()).thenReturn(List.of(grade));

        List<Grade> grades = gradeService.getAllGrades();

        assertNotNull(grades);
        assertEquals(1, grades.size());
        verify(gradeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void givenGradesExists_WhenGetGradeById_ThenReturnGrade() {
        Grade grade = new Grade();
        grade.setId(1L);
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        Grade foundGrade = gradeService.getGradeById(1L);

        assertNotNull(foundGrade);
        assertEquals(1L, foundGrade.getId());
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    void givenGradeDoesNotExist_WhenGetGradeById_ThenThrowException() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> gradeService.getGradeById(1L));

        assertEquals("Could not find grade with id: 1", exception.getMessage());
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    void givenValidRequest_WhenAddGrade_ThenSaveAndReturnGrade() {
        AddGradeRequest request = new AddGradeRequest(1L, 1L, 9.5);
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(1L);
        Grade grade = new Grade();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        Grade savedGrade = gradeService.addGrade(request);

        assertNotNull(savedGrade);
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void givenInvalidStudent_WhenAddGrade_ThenThrowException() {
        AddGradeRequest request = new AddGradeRequest(1L, 1L, 9.5);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gradeService.addGrade(request));

        assertEquals("The student with id 1 does not exist", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidCourse_WhenAddGrade_ThenThrowException() {
        AddGradeRequest request = new AddGradeRequest(1L, 1L, 9.5);
        Student student = new Student();
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gradeService.addGrade(request));

        assertEquals("The course with id 1 does not exist", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void givenGradeExists_WhenUpdateGrade_ThenUpdateAndReturnGrade() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setGrade(8.0);

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(gradeRepository.save(grade)).thenReturn(grade);

        Grade updatedGrade = gradeService.updateGrade(1L, 9.0);

        assertNotNull(updatedGrade);
        assertEquals(9.0, updatedGrade.getGrade());
        verify(gradeRepository, times(1)).findById(1L);
        verify(gradeRepository, times(1)).save(grade);
    }

    @Test
    void givenGradeId_WhenDeleteGrade_ThenDeleteGrade() {
        doNothing().when(gradeRepository).deleteById(1L);

        gradeService.deleteGrade(1L);

        verify(gradeRepository, times(1)).deleteById(1L);
    }

    @Test
    void givenStudentId_WhenGetGradesByStudentId_ThenReturnGrades() {
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        when(gradeRepository.findByStudentId(1L)).thenReturn(List.of(grade1, grade2));

        List<Grade> grades = gradeService.getGradesByStudentId(1L);

        assertNotNull(grades);
        assertEquals(2, grades.size());
        verify(gradeRepository, times(1)).findByStudentId(1L);
    }


}
