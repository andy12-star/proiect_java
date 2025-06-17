package gradeservice.service;

import gradeservice.model.Grade;
import gradeservice.model.GradeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GradeService {
    List<Grade> getAllGrades();

    Grade getGradeById(Long id);

    Grade addGrade(GradeRequest request);

    Grade updateGrade(Long id, Double grade);

    void deleteGrade(Long id);

    List<Grade> getGradesByStudentId(Long studentId);

    Page<Grade> getGradesPage(Pageable pageable);
}
