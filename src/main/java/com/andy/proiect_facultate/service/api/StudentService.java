package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
