package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.GradeRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Grade> getAllGrades() {
        log.info("Getting all grades");
        return gradeRepository.findAll();
    }

    @Override
    public Grade getGradeById(Long id) {
        log.info("Getting grade by id: {}", id);
        return gradeRepository.findById(id).
                orElseThrow(()->new RuntimeException("Could not find grade with id: "+id));
    }

    @Override
    public Grade addGrade(AddGradeRequest addGradeRequest) {
        log.info("Adding grade: {}", addGradeRequest);
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
        log.info("Updating grade: {}", id);
        Grade updatedGrade = getGradeById(id);
        updatedGrade.setGrade(value);
        return gradeRepository.save(updatedGrade);

    }

    @Override
    public void deleteGrade(Long id) {
        log.info("Deleting grade: {}", id);
        gradeRepository.deleteById(id);
    }

    @Override
    public List<Grade> getGradesByStudentId(Long studentId) {
        log.info("Getting grades by studentId: {}", studentId);
            return gradeRepository.findByStudentId(studentId);
    }
}
