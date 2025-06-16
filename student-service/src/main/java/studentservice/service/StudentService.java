package studentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studentservice.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Page<Student> getStudentsPage(Pageable pageable);
}
