package gradeservice.service;

import gradeservice.model.Grade;
import gradeservice.model.GradeRequest;
import gradeservice.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find grade with id: " + id));
    }

    @Override
    public Grade addGrade(GradeRequest request) {
        Grade grade = Grade.builder()
                .studentId(request.getStudentId())
                .courseId(request.getCourseId())
                .grade(request.getGrade())
                .build();
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
