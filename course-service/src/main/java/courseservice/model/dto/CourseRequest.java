package courseservice.model.dto;

import lombok.Data;

@Data
public class CourseRequest {
    private String courseName;
    private Integer credits;
    private long professorId;
}
