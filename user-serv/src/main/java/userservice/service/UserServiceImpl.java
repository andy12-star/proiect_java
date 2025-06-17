package userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import professorservice.model.Professor;
import professorservice.repository.ProfessorRepository;
import studentservice.entity.Student;
import studentservice.repository.StudentRepository;
import userservice.model.RegisterRequest;
import userservice.model.User;
import userservice.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest request) {
        User user;
        switch (request.getRole()) {
            case STUDENT -> {
                Student student = new Student();
                student.setYear(request.getYear());
                student.setSpecialization(request.getSpecialization());
                user = student;
            }
            case PROFESSOR -> {
                Professor professor = new Professor();
                professor.setDepartment(request.getDepartment());
                user = professor;
            }
            case ADMINISTRATOR -> user = new User();
            default -> throw new IllegalArgumentException("Invalid role");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userRepository.save(user);
    }
}
