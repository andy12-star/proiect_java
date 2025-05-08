package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    void canSaveAndRetrieveCourse() {
        Professor professor = new Professor();
        professor.setFirstName("Mihai");
        professor.setLastName("Ionescu");
        professor.setEmail("mihai@uni.com");
        professor.setPassword("pass123");
        professor.setRole(RoleType.PROFESSOR);
        professor.setDepartment("Mathematics");

        professor = professorRepository.save(professor);

        Course course = Course.builder()
                .courseName("Algebra")
                .credits(5)
                .examDate(LocalDate.now().plusDays(10))
                .professor(professor)
                .build();

        Course saved = courseRepository.save(course);
        Course found = courseRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getCourseName()).isEqualTo("Algebra");
        assertThat(found.getProfessor().getEmail()).isEqualTo("mihai@uni.com");
    }
}
