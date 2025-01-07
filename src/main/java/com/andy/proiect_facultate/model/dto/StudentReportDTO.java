package com.andy.proiect_facultate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class StudentReportDTO {
    private String studentName;
    private String email;
    private List<String> courseNames;
    private List<Double> grades;

}
