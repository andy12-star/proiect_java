package courseservice.model.dto;

import lombok.Data;

@Data
public class CreateCourseRequest {
    private String courseName;
    private Integer credits;
    private long professorId;
}
