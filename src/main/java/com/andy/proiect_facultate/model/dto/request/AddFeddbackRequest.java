package com.andy.proiect_facultate.model.dto.request;

import lombok.Data;

@Data
public class AddFeddbackRequest {
    private Long studentId;
    private Long courseId;
    private String comment;
    private Integer rating;
}