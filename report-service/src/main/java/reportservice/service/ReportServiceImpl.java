package reportservice.service;

import courseservice.model.Course;
import courseservice.repository.CourseRepository;
import enrollmentservice.repository.EnrollmentRepository;
import gradeservice.model.Grade;
import gradeservice.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reportservice.model.CourseReportDTO;
import reportservice.model.StudentReportDTO;
import studentservice.entity.Student;
import studentservice.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;

    @Override
    public StudentReportDTO generateStudentReport(Long studentId) {
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