package userservice.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleType role;
    private Integer year;
    private String specialization;
    private String department;
}