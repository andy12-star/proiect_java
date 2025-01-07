package com.andy.proiect_facultate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CourseReportDTO {
    private String courseName;
    private List<String> studentNames;
    private List<Double> grades;

}
