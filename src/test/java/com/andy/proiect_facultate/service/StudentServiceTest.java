package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenStudentsExist_WhenGetAllStudents_ThenReturnAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void givenStudentExists_WhenGetStudentById_ThenReturnStudent() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals(1L, foundStudent.getId());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void givenStudentDoesNotExist_WhenGetStudentById_ThenThrowException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> studentService.getStudentById(1L));
        assertEquals("Student with id 1 not found", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void givenValidStudent_WhenAddStudent_ThenSaveAndReturnStudent() {

        Student student = new Student();
        when(studentRepository.save(student)).thenReturn(student);

        Student savedStudent = studentService.addStudent(student);

        assertNotNull(savedStudent);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void givenValidIdAndStudent_WhenUpdateStudent_ThenSaveAndReturnUpdatedStudent() {

        Student existingStudent = new Student();
        existingStudent.setId(1L);
        existingStudent.setFirstName("John");
        existingStudent.setLastName("Doe");
        existingStudent.setEmail("john.doe@example.com");
        existingStudent.setYear(2);
        existingStudent.setSpecialization("Computer Science");

        Student updatedData = new Student();
        updatedData.setFirstName("Jane");
        updatedData.setLastName("Smith");
        updatedData.setEmail("jane.smith@example.com");
        updatedData.setYear(3);
        updatedData.setSpecialization("Mathematics");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Student updatedStudent = studentService.updateStudent(1L, updatedData);

        assertNotNull(updatedStudent);
        assertEquals("Jane", updatedStudent.getFirstName());
        assertEquals("Smith", updatedStudent.getLastName());
        assertEquals("jane.smith@example.com", updatedStudent.getEmail());
        assertEquals(3, updatedStudent.getYear());
        assertEquals("Mathematics", updatedStudent.getSpecialization());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void givenValidId_WhenDeleteStudent_ThenVerifyDeletion() {

        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}