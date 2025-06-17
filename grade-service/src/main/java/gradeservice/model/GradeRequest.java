package gradeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeRequest {
    private Long studentId;
    private Long courseId;
    private Double grade;
}
