package gradeservice.repository;

import gradeservice.model.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Page<Grade> findAll(Pageable pageable);

    List<Grade> findByStudentId(Long studentId);
}