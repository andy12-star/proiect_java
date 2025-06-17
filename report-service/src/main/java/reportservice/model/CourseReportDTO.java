package reportservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CourseReportDTO {
    private String courseName;
    private List<String> studentNames;
    private List<Double> grades;
}