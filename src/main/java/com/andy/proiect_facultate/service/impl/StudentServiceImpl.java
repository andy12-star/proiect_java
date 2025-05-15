package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        log.info("Fetching student by id: {}", id);
        return studentRepository.findById(id).
                orElseThrow(()->new RuntimeException("Student with id " + id + " not found"));
    }

    @Override
    public Student addStudent(Student student) {
        log.info("Adding student: {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        log.info("Updating student: {}", student);
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
        log.info("Deleting student: {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Page<Student> getStudentsPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
}
