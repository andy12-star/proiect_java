package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;


    @Test
    void givenCoursesInRepository_WhenGetAllCourses_ThenReturnAllCourses() {
        Course course1 = new Course();
        Course course2 = new Course();
        when(courseRepository.findAll()).thenReturn(List.of(course1, course2));

        List<Course> courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void givenCourseExists_WhenGetCourseById_ThenReturnCourse() {
        Course course = new Course();
        course.setId(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course foundCourse = courseService.getCourseById(1L);

        assertNotNull(foundCourse);
        assertEquals(1L, foundCourse.getId());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void givenCourseDoesNotExist_WhenGetCourseById_ThenThrowException() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> courseService.getCourseById(1L));

        assertEquals("Course with id:1 not found", exception.getMessage());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void givenNewCourse_WhenAddCourse_ThenSaveAndReturnCourse() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        Course savedCourse = courseService.addCourse(course);

        assertNotNull(savedCourse);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void givenExistingCourse_WhenUpdateCourse_ThenUpdateAndReturnCourse() {
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setCourseName("Old Course");
        existingCourse.setCredits(3);

        Course updatedData = new Course();
        updatedData.setCourseName("New Course");
        updatedData.setCredits(4);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

        Course updatedCourse = courseService.updateCourse(1L, updatedData);

        assertNotNull(updatedCourse);
        assertEquals("New Course", updatedCourse.getCourseName());
        assertEquals(4, updatedCourse.getCredits());
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    void givenExistingCourse_WhenUpdateCourseWithProfessor_ThenUpdateWithProfessorAndReturnCourse() {
        Course existingCourse = new Course();
        Professor existingProfessor = Professor.builder().firstName("ExistingProfessor").build();
        existingCourse.setId(1L);
        existingCourse.setCourseName("Old Course");
        existingCourse.setCredits(3);
        existingCourse.setProfessor(existingProfessor);

        Course updatedData = new Course();
        Professor updatedProfessor = Professor.builder().firstName("UpdatedProfessor").build();
        updatedData.setCourseName("New Course");
        updatedData.setProfessor(updatedProfessor);
        updatedData.setCredits(4);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

        Course updatedCourse = courseService.updateCourse(1L, updatedData);

        assertNotNull(updatedCourse);
        assertEquals("New Course", updatedCourse.getCourseName());
        assertEquals(4, updatedCourse.getCredits());
        assertEquals(updatedCourse.getProfessor().getFirstName(), "UpdatedProfessor");
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    void givenCourseId_WhenDeleteCourse_ThenVerifyDeletion() {
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void givenCourseAndExamDate_WhenScheduleExam_ThenSetDateAndReturnCourse() {
        Course course = new Course();
        course.setId(1L);
        LocalDate examDate = LocalDate.of(2025, 1, 15);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course scheduledCourse = courseService.scheduleExam(1L, examDate);

        assertNotNull(scheduledCourse);
        assertEquals(examDate, scheduledCourse.getExamDate());
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(course);
    }
}