package gradeservice.model;

import lombok.Data;

@Data
public class GradeDto {
    private Long studentId;
    private Long courseId;
    private Double grade;
}
