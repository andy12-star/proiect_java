package gradeservice.service;

import gradeservice.model.AddGradeRequest;
import gradeservice.model.Grade;
import gradeservice.repository.GradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    @Override
    public Grade addGrade(AddGradeRequest request) {
        Grade grade = new Grade();
        grade.setGrade(request.getGrade());
        grade.setStudentId(request.getStudentId());
        grade.setCourseId(request.getCourseId());
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(Long id, Double value) {
        Grade grade = getGradeById(id);
        grade.setGrade(value);
        return gradeRepository.save(grade);
    }

    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public Page<Grade> getGradesPage(Pageable pageable) {
        return gradeRepository.findAll(pageable);
    }
}
