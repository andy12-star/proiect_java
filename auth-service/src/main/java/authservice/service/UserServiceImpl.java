package authservice.service;

import authservice.dto.request.RegisterRequest;
import authservice.entity.Administrator;
import authservice.entity.Professor;
import authservice.entity.Student;
import authservice.entity.User;
import authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest request) {
        log.info("Registering user: {}", request);
        User user;

        switch (request.getRole()) {
            case STUDENT -> {
                Student student = new Student();
                student.setYear(request.getYear());
                student.setSpecialization(request.getSpecialization());
                user = student;
            }
            case PROFESSOR -> {
                Professor prof = new Professor();
                prof.setDepartment(request.getDepartment());
                user = prof;
            }
            case ADMINISTRATOR -> user = new Administrator();
            default -> throw new IllegalArgumentException("Invalid role");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    @Override
    public Page<User> getUsersPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
