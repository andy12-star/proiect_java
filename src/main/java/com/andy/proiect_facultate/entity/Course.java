package com.andy.proiect_facultate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Course name is required")
    private String courseName;

    @Min(value=1, message = "Credit value must be at least 1")
    private int credits;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @NotNull(message = "Professor is required")
    private Professor professor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Course name is required") String getCourseName() {
        return courseName;
    }

    public void setCourseName(@NotNull(message = "Course name is required") String courseName) {
        this.courseName = courseName;
    }

    @Min(value = 1, message = "Credit value must be at least 1")
    public int getCredits() {
        return credits;
    }

    public void setCredits(@Min(value = 1, message = "Credit value must be at least 1") int credits) {
        this.credits = credits;
    }

    public @NotNull(message = "Professor is required") Professor getProfessor() {
        return professor;
    }

    public void setProfessor(@NotNull(message = "Professor is required") Professor professor) {
        this.professor = professor;
    }
}
