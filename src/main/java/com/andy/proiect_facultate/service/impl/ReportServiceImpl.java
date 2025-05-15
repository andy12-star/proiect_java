package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.dto.CourseReportDTO;
import com.andy.proiect_facultate.model.dto.StudentReportDTO;
import com.andy.proiect_facultate.model.entity.Course;
import com.andy.proiect_facultate.model.entity.Grade;
import com.andy.proiect_facultate.model.entity.Student;
import com.andy.proiect_facultate.repository.CourseRepository;
import com.andy.proiect_facultate.repository.GradeRepository;
import com.andy.proiect_facultate.repository.StudentRepository;
import com.andy.proiect_facultate.service.api.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public StudentReportDTO generateStudentReport(Long studentId) {
        log.info("Generating report for student {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        List<String> courseNames = grades.stream().map(grade -> grade.getCourse().getCourseName()).collect(Collectors.toList());
        List<Double> gradeValues = grades.stream().map(Grade::getGrade).collect(Collectors.toList());

        return new StudentReportDTO(
                student.getFirstName() + " " + student.getLastName(),
                student.getEmail(),
                courseNames,
                gradeValues
        );
    }

    @Override
    public CourseReportDTO generateCourseReport(Long courseId) {
        log.info("Generating report for course {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Grade> grades = gradeRepository.findByCourseId(courseId);
        List<String> studentNames = grades.stream().map(grade -> grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName()).collect(Collectors.toList());
        List<Double> gradeValues = grades.stream().map(Grade::getGrade).collect(Collectors.toList());

        return new CourseReportDTO(
                course.getCourseName(),
                studentNames,
                gradeValues
        );
    }
}
