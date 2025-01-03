package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCourseId(Long courseId);

    List<Feedback> findByStudentId(Long studentId);
}
