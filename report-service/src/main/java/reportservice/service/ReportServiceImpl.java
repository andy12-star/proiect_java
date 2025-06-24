package reportservice.service;


import courseservice.model.dto.CourseDto;
import gradeservice.model.GradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reportservice.client.CourseClient;
import reportservice.client.GradeClient;
import reportservice.client.StudentClient;
import reportservice.model.CourseReportDTO;
import reportservice.model.StudentReportDTO;
import studentservice.entity.dto.StudentDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final GradeClient gradeClient;
    private final StudentClient studentClient;
    private final CourseClient courseClient;

    @Override
    public StudentReportDTO generateStudentReport(Long studentId) {
        StudentDto student = studentClient.getStudentById(studentId);
        List<GradeDto> grades = gradeClient.getGradesByStudent(studentId);

        List<String> courseNames = grades.stream()
                .map(grade -> courseClient.getCourseById(grade.getCourseId()).getCourseName())
                .toList();

        List<Double> gradeValues = grades.stream().map(GradeDto::getGrade).toList();

        return StudentReportDTO.builder()
                .studentName(student.getFirstName() + " " + student.getLastName())
                .email(student.getEmail())
                .courseNames(courseNames)
                .grades(gradeValues)
                .build();
    }

    @Override
    public CourseReportDTO generateCourseReport(Long courseId) {
        CourseDto course = courseClient.getCourseById(courseId);
        List<GradeDto> grades = gradeClient.getGradesByCourse(courseId);

        List<String> studentNames = grades.stream()
                .map(g -> {
                    StudentDto s = studentClient.getStudentById(g.getStudentId());
                    return s.getFirstName() + " " + s.getLastName();
                }).toList();

        List<Double> gradeValues = grades.stream().map(GradeDto::getGrade).toList();

        return CourseReportDTO.builder()
                .courseName(course.getCourseName())
                .studentNames(studentNames)
                .grades(gradeValues)
                .build();
    }
}