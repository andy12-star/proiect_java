package com.andy.proiect_facultate.model.dto.request;

import lombok.Data;

@Data
public class AddGradeRequest {
    private Long studentId;
    private Long courseId;
    private Double grade;
}
