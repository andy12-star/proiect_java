package com.andy.proiect_facultate.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
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
    private User student;

    @ManyToOne
    @NotNull(message = "course required")
    @JoinColumn(name = "course_id")
    private Course course;


    @Column(length = 500)
    @NotNull(message = "comment required")
    private String comment;

    @Range(min = 1, max = 5, message = "Rating must be between 1 and 5")
    @NotNull(message = "rating required")
    private Integer rating;
}
