package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Grade;
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
public class GradeRepositoryTest {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Rollback
    void canSaveAndFindByStudentIdAndCourseId() {

        Professor professor = Professor.builder()
                .firstName("Ana")
                .lastName("Popescu")
                .email("ana@facultate.com")
                .password("pass123")
                .department("IT")
                .role(RoleType.PROFESSOR)
                .build();
        professorRepository.save(professor);

        Student student = Student.builder()
                .firstName("Mihai")
                .lastName("Ionescu")
                .email("mihai@student.com")
                .password("pass456")
                .specialization("CS")
                .year(3)
                .role(RoleType.STUDENT)
                .build();
        studentRepository.save(student);

        Course course = Course.builder()
                .courseName("Databases")
                .credits(6)
                .professor(professor)
                .build();
        courseRepository.save(course);

        Grade grade = Grade.builder()
                .student(student)
                .course(course)
                .grade(9.5)
                .build();
        gradeRepository.save(grade);

        List<Grade> byStudent = gradeRepository.findByStudentId(student.getId());
        assertThat(byStudent).hasSize(1);
        assertThat(byStudent.get(0).getGrade()).isEqualTo(9.5);

        List<Grade> byCourse = gradeRepository.findByCourseId(course.getId());
        assertThat(byCourse).hasSize(1);
        assertThat(byCourse.get(0).getStudent().getEmail()).isEqualTo("mihai@student.com");
    }
}
