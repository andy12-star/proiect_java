package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.GradeRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id).
                orElseThrow(()->new RuntimeException("Could not find grade with id: "+id));
    }

    @Override
    public Grade addGrade(AddGradeRequest addGradeRequest) {
        Grade grade = Grade.builder()
                .student(studentRepository.findById(addGradeRequest.getStudentId())
                        .orElseThrow(() -> new IllegalArgumentException(("The student with id " + addGradeRequest.getStudentId() + " does not exist"))))
                .course(courseRepository.findById(addGradeRequest.getCourseId())
                        .orElseThrow(() -> new IllegalArgumentException(("The course with id " + addGradeRequest.getCourseId() + " does not exist"))))
                .grade(addGradeRequest.getGrade())
                .build();
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(Long id, Double value) {
        Grade updatedGrade = getGradeById(id);
        updatedGrade.setGrade(value);
        return gradeRepository.save(updatedGrade);

    }

    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public List<Grade> getGradesByStudentId(Long studentId) {
            return gradeRepository.findByStudentId(studentId);
    }
}
