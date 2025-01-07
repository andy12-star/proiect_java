package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.dto.request.RegisterRequest;
import com.andy.proiect_facultate.model.entity.Administrator;
import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.model.entity.User;
import com.andy.proiect_facultate.model.enums.RoleType;
import com.andy.proiect_facultate.repository.UserRepository;
import com.andy.proiect_facultate.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenStudentRole_WhenRegisterUser_ThenReturnSavedStudent() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole(RoleType.STUDENT);
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setYear(2);
        registerRequest.setSpecialization("Computer Science");

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setRole(RoleType.STUDENT);
        student.setYear(2);
        student.setSpecialization("Computer Science");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        when(userRepository.save(any(Student.class))).thenReturn(student);

        User registeredUser = userService.registerUser(registerRequest);

        assertNotNull(registeredUser);
        assertTrue(registeredUser instanceof Student);
        assertEquals("John", registeredUser.getFirstName());
        assertEquals("Doe", registeredUser.getLastName());
        assertEquals("john.doe@example.com", registeredUser.getEmail());
        assertEquals(RoleType.STUDENT, registeredUser.getRole());
        assertEquals(2, ((Student) registeredUser).getYear());
        assertEquals("Computer Science", ((Student) registeredUser).getSpecialization());
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(Student.class));
    }

    @Test
    void givenProfessorRole_WhenRegisterUser_ThenReturnSavedProfessor() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole(RoleType.PROFESSOR);
        registerRequest.setFirstName("Jane");
        registerRequest.setLastName("Smith");
        registerRequest.setEmail("jane.smith@example.com");
        registerRequest.setPassword("securePassword");
        registerRequest.setDepartment("Mathematics");

        Professor professor = new Professor();
        professor.setFirstName("Jane");
        professor.setLastName("Smith");
        professor.setEmail("jane.smith@example.com");
        professor.setRole(RoleType.PROFESSOR);
        professor.setDepartment("Mathematics");

        when(passwordEncoder.encode("securePassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(Professor.class))).thenReturn(professor);

        User registeredUser = userService.registerUser(registerRequest);

        assertNotNull(registeredUser);
        assertTrue(registeredUser instanceof Professor);
        assertEquals("Jane", registeredUser.getFirstName());
        assertEquals("Smith", registeredUser.getLastName());
        assertEquals("jane.smith@example.com", registeredUser.getEmail());
        assertEquals(RoleType.PROFESSOR, registeredUser.getRole());
        assertEquals("Mathematics", ((Professor) registeredUser).getDepartment());
        verify(passwordEncoder, times(1)).encode("securePassword");
        verify(userRepository, times(1)).save(any(Professor.class));
    }

    @Test
    void givenAdministratorRole_WhenRegisterUser_ThenReturnSavedAdministrator() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole(RoleType.ADMINISTRATOR);
        registerRequest.setFirstName("Admin");
        registerRequest.setLastName("User");
        registerRequest.setEmail("admin@example.com");
        registerRequest.setPassword("adminPassword");

        Administrator administrator = new Administrator();
        administrator.setFirstName("Admin");
        administrator.setLastName("User");
        administrator.setEmail("admin@example.com");
        administrator.setRole(RoleType.ADMINISTRATOR);

        when(passwordEncoder.encode("adminPassword")).thenReturn("encodedAdminPassword");
        when(userRepository.save(any(Administrator.class))).thenReturn(administrator);

        User registeredUser = userService.registerUser(registerRequest);

        assertNotNull(registeredUser);
        assertTrue(registeredUser instanceof Administrator);
        assertEquals("Admin", registeredUser.getFirstName());
        assertEquals("User", registeredUser.getLastName());
        assertEquals("admin@example.com", registeredUser.getEmail());
        assertEquals(RoleType.ADMINISTRATOR, registeredUser.getRole());
        verify(passwordEncoder, times(1)).encode("adminPassword");
        verify(userRepository, times(1)).save(any(Administrator.class));
    }

    @Test
    void givenInvalidRole_WhenRegisterUser_ThenThrowException() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(registerRequest));
        assertEquals("Invalid role: null", exception.getMessage());
        verifyNoInteractions(passwordEncoder, userRepository);
    }
}