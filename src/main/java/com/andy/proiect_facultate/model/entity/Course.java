package com.andy.proiect_facultate.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Course name is required")
    private String courseName;

    @Min(value=1, message = "Credit value must be at least 1")
    private int credits;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @NotNull(message = "Professor is required")
    private User professor;

    @Future(message = "Exam date must be in the future")
    @Column(name = "exam_date")
    private LocalDate examDate;
}
