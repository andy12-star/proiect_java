package studentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studentservice.entity.Student;
import studentservice.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    public final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student updated = getStudentById(id);
        updated.setFirstName(student.getFirstName());
        updated.setLastName(student.getLastName());
        updated.setEmail(student.getEmail());
        updated.setStudyYear(student.getStudyYear());
        updated.setSpecialization(student.getSpecialization());
        return studentRepository.save(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Page<Student> getStudentsPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
}
