package com.andy.proiect_facultate.model.dto.request;

import lombok.Data;

@Data
public class CreateCourseRequest {
    private String courseName;
    private Integer credits;
    private long professorId;
}
