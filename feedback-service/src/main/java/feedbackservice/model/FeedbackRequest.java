package feedbackservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackRequest {
    private Long studentId;
    private Long courseId;
    private String comment;
    private Integer rating;
}