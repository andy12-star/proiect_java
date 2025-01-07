package com.andy.proiect_facultate.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddFeedbackRequest {
    private Long studentId;
    private Long courseId;
    private String comment;
    private Integer rating;
}