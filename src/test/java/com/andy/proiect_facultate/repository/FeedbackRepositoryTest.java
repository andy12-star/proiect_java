package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Feedback;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.model.enums.RoleType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    @Rollback
    void canSaveAndFindByCourseIdAndStudentId() {

        Professor professor = Professor.builder()
                .firstName("Prof")
                .lastName("Test")
                .email("prof@test.com")
                .password("pass")
                .department("CS")
                .role(RoleType.PROFESSOR)
                .build();
        professorRepository.save(professor);

        Student student = Student.builder()
                .firstName("Student")
                .lastName("Test")
                .email("student@test.com")
                .password("pass")
                .specialization("Math")
                .year(2)
                .role(RoleType.STUDENT)
                .build();
        studentRepository.save(student);

        Course course = Course.builder()
                .courseName("Test Course")
                .credits(3)
                .professor(professor)
                .build();
        courseRepository.save(course);

        Feedback feedback = Feedback.builder()
                .student(student)
                .course(course)
                .comment("Great course!")
                .rating(5)
                .build();
        feedbackRepository.save(feedback);

        List<Feedback> byCourse = feedbackRepository.findByCourseId(course.getId());
        assertThat(byCourse).hasSize(1);
        assertThat(byCourse.get(0).getComment()).isEqualTo("Great course!");

        List<Feedback> byStudent = feedbackRepository.findByStudentId(student.getId());
        assertThat(byStudent).hasSize(1);
        assertThat(byStudent.get(0).getRating()).isEqualTo(5);
    }
}
