package com.andy.proiect_facultate.repository;

import com.andy.proiect_facultate.model.entity.User;
import com.andy.proiect_facultate.model.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUserExists_whenFindByEmail_thenReturnsUser() {
        User user = new User();
        user.setFirstName("Ana");
        user.setLastName("Popescu");
        user.setEmail("ana@example.com");
        user.setPassword("password");
        user.setRole(RoleType.STUDENT);

        userRepository.save(user);

        User foundUser = userRepository.findByEmail("ana@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("ana@example.com");
        assertThat(foundUser.getFirstName()).isEqualTo("Ana");
    }

    @Test
    void givenUserDoesNotExist_whenFindByEmail_thenReturnsNull() {
        User foundUser = userRepository.findByEmail("ghost@example.com");

        assertThat(foundUser).isNull();
    }
}
