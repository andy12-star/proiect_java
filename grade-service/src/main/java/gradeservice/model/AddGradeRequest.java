package gradeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddGradeRequest {
    private Long studentId;
    private Long courseId;
    private Double grade;
}
