package com.andy.proiect_facultate.model.dto;

import java.util.List;

public class CourseReportDTO {
    private String courseName;
    private List<String> studentNames;
    private List<Double> grades;

    public CourseReportDTO(String courseName, List<String> studentNames, List<Double> grades) {
        this.courseName = courseName;
        this.studentNames = studentNames;
        this.grades = grades;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }
}
