package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.entity.Grade;

import java.util.List;

public interface GradeService {
    List<Grade> getAllGrades();

    Grade getGradeById(Long id);

    Grade addGrade(Grade grade);

    Grade updateGrade(Long id,Double value);

    void deleteGrade(Long id);
}
