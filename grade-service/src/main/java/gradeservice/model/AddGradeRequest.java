package gradeservice.model;

import lombok.Data;

@Data
public class AddGradeRequest {
    private Long studentId;
    private Long courseId;
    private Double grade;

    public AddGradeRequest() {
    }

    public AddGradeRequest(Long studentId, Long courseId, Double grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
