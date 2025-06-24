package reportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import studentservice.entity.dto.StudentDto;

@FeignClient(name = "student-service")
public interface StudentClient {
    @GetMapping("/students/{id}")
    StudentDto getStudentById(@PathVariable Long id);
}
