package com.andy.proiect_facultate.dto;

import java.util.List;

public class StudentReportDTO {
    private String studentName;
    private String email;
    private List<String> courseNames;
    private List<Double> grades;

    public StudentReportDTO(String studentName, String email, List<String> courseNames, List<Double> grades) {
        this.studentName = studentName;
        this.email = email;
        this.courseNames = courseNames;
        this.grades = grades;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }
}
