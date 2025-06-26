package reportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reportservice.model.GradeDto;

import java.util.List;

@FeignClient(name = "grade-service")
public interface GradeClient {
    @GetMapping("/grades/students/{studentId}")
    List<GradeDto> getGradesByStudent(@PathVariable Long studentId);

    @GetMapping("/grades/page")
    List<GradeDto> getAllGrades();

    @GetMapping("/grades/courses/{courseId}")
    List<GradeDto> getGradesByCourse(@PathVariable Long courseId);
}
