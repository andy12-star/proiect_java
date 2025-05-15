package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCourseId(Long courseId);

    List<Feedback> findByStudentId(Long studentId);

    Page<Feedback> findAll(Pageable pageable);
}
