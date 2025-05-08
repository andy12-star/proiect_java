package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.model.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void canSaveAndRetrieveStudent() {
        Student student = Student.builder()
                .firstName("Ana")
                .lastName("Pop")
                .email("ana.pop@example.com")
                .password("123456")
                .role(RoleType.STUDENT)
                .year(1)
                .specialization("CS")
                .build();

        Student saved = studentRepository.save(student);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("ana.pop@example.com");
    }
}
