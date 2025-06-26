package reportservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reportservice.client.CourseClient;
import reportservice.client.GradeClient;
import reportservice.client.StudentClient;
import reportservice.model.*;

import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final GradeClient gradeClient;
    private final StudentClient studentClient;
    private final CourseClient courseClient;

    public ReportServiceImpl(GradeClient gradeClient, StudentClient studentClient, CourseClient courseClient) {
        this.gradeClient = gradeClient;
        this.studentClient = studentClient;
        this.courseClient = courseClient;
    }

    @Override
    public StudentReportDTO generateStudentReport(Long studentId) {
        StudentDto student = studentClient.getStudentById(studentId);
        List<GradeDto> grades = gradeClient.getGradesByStudent(studentId);

        List<String> courseNames = grades.stream()
                .map(grade -> courseClient.getCourseById(grade.getCourseId()).getCourseName())
                .toList();

        List<Double> gradeValues = grades.stream().map(GradeDto::getGrade).toList();
        StudentReportDTO studentReportDTO = new StudentReportDTO(student.getFirstName() + " " + student.getLastName(), student.getEmail(),
                courseNames, gradeValues);
        return studentReportDTO;
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
        CourseReportDTO courseReportDTO = new CourseReportDTO(course.getCourseName(), studentNames, gradeValues);
        return courseReportDTO;
    }
}