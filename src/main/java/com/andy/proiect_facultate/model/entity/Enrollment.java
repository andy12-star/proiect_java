package com.andy.proiect_facultate.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @NotNull(message = "student is required")
    private User student;

    @ManyToOne
    @NotNull(message = "course is resuired")
    private Course course;

    @NotBlank(message = "status is required")
    private String status;
}
