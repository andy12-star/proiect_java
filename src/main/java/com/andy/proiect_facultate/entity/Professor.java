package com.andy.proiect_facultate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @Email(message = "Email is required")
    private String email;

    @NotNull
    private String department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "First name is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "First name is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "Last name is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Last name is required") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email is required") String email) {
        this.email = email;
    }

    public @NotNull String getDepartment() {
        return department;
    }

    public void setDepartment(@NotNull String department) {
        this.department = department;
    }
}
