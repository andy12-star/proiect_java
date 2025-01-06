package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.model.dto.request.AddGradeRequest;
import com.andy.proiect_facultate.model.entity.Grade;

import java.util.List;

public interface GradeService {
    List<Grade> getAllGrades();

    Grade getGradeById(Long id);

    Grade addGrade(AddGradeRequest addGradeRequest);

    Grade updateGrade(Long id,Double value);

    void deleteGrade(Long id);

    List<Grade> getGradesByStudentId(Long studentId);
}
