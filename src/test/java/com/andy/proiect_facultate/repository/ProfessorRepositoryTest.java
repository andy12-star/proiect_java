package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class ProfessorRepositoryTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    void canSaveAndRetrieveProfessor() {
        Professor prof = new Professor();
        prof.setFirstName("Elena");
        prof.setLastName("Vasilescu");
        prof.setEmail("elena@univ.ro");
        prof.setPassword("secure");
        prof.setDepartment("Mathematics");
        prof.setRole(RoleType.PROFESSOR);

        Professor saved = professorRepository.save(prof);

        assertThat(saved.getId()).isNotNull();
        assertThat(professorRepository.findById(saved.getId())).isPresent();
    }
}
