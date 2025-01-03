package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.entity.Grade;
import com.andy.proiect_facultate.repository.GradeRepository;
import com.andy.proiect_facultate.service.api.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
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
    public Grade addGrade(Grade grade) {
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
