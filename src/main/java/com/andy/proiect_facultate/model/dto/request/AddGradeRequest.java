package com.andy.proiect_facultate.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddGradeRequest {
    private Long studentId;
    private Long courseId;
    private Double grade;
}
