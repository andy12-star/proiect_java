package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {


        List<Grade> findByStudentId(Long studentId);

        List<Grade> findByCourseId(Long courseId);
}
