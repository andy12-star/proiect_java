package reportservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StudentReportDTO {
    private String studentName;
    private String email;
    private List<String> courseNames;
    private List<Double> grades;
}