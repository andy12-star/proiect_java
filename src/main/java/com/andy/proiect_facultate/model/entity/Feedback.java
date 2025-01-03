package com.andy.proiect_facultate.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "student required")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @NotNull(message = "course required")
    @JoinColumn(name = "course_id")
    private Course course;


    @Column(length = 500)
    @NotNull(message = "comment required")
    private String comment;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    @NotNull(message = "rating required")
    private Integer rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "student required") Student getStudent() {
        return student;
    }

    public void setStudent(@NotNull(message = "student required") Student student) {
        this.student = student;
    }

    public @NotNull(message = "course required") Course getCourse() {
        return course;
    }

    public void setCourse(@NotNull(message = "course required") Course course) {
        this.course = course;
    }

    public @NotNull(message = "comment required") String getComment() {
        return comment;
    }

    public void setComment(@NotNull(message = "comment required") String comment) {
        this.comment = comment;
    }

    public @Min(value = 1, message = "Rating must be at least 1") @Max(value = 5, message = "Rating must not exceed 5") @NotNull(message = "rating required") Integer getRating() {
        return rating;
    }

    public void setRating(@Min(value = 1, message = "Rating must be at least 1") @Max(value = 5, message = "Rating must not exceed 5") @NotNull(message = "rating required") Integer rating) {
        this.rating = rating;
    }
}
