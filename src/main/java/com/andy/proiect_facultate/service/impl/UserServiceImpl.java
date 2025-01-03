package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.dto.request.RegisterRequest;
import com.andy.proiect_facultate.model.entity.Administrator;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.model.entity.User;
import com.andy.proiect_facultate.repository.UserRepository;
import com.andy.proiect_facultate.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        User user;

        switch (registerRequest.getRole()) {
            case STUDENT -> {
                Student student = new Student();
                student.setYear(registerRequest.getYear());
                student.setSpecialization(registerRequest.getSpecialization());
                user = student;
            }
            case PROFESSOR -> {
                Professor professor = new Professor();
                professor.setDepartment(registerRequest.getDepartment());
                user = professor;
            }
            case ADMINISTRATOR -> user = new Administrator();
            default -> throw new IllegalArgumentException("Invalid role: " + registerRequest.getRole());
        }

        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return userRepository.save(user);
    }
}