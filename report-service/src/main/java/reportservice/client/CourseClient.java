package reportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reportservice.model.CourseDto;

@FeignClient(name = "course-service")
public interface CourseClient {
    @GetMapping("/courses/{id}")
    CourseDto getCourseById(@PathVariable Long id);
}
