package com.andy.proiect_facultate.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 4, message = "Year must be at most 5")
    private int year;

    @NotBlank(message = "Specialization is required")
    private String specialization;

}
