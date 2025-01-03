package com.andy.proiect_facultate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message="First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(length = 100, unique = true)
    private String email;

    @Min(value=1, message = "Year must be at least 1")
    private int year;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Min(value = 1, message = "Year must be at least 1")
    public int getYear() {
        return year;
    }

    public void setYear(@Min(value = 1, message = "Year must be at least 1") int year) {
        this.year = year;
    }

    public @NotNull(message = "Specialization is required") String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(@NotNull(message = "Specialization is required") String specialization) {
        this.specialization = specialization;
    }
}
