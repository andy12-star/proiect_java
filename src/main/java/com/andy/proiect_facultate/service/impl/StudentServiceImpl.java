package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).
                orElseThrow(()->new RuntimeException("Student with id " + id + " not found"));
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
       Student updatedStudent = studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student with id " + id + " not found"));
       updatedStudent.setFirstName(student.getFirstName());
       updatedStudent.setLastName(student.getLastName());
       updatedStudent.setEmail(student.getEmail());
       updatedStudent.setYear(student.getYear());
       updatedStudent.setSpecialization(student.getSpecialization());
       return studentRepository.save(updatedStudent);

    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
