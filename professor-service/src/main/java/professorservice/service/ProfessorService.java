package professorservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import professorservice.model.Professor;

import java.util.List;

public interface ProfessorService {
    List<Professor> getAllProfessors();

    Professor getProfessorById(Long id);

    Professor addProfessor(Professor professor);

    Professor updateProfessor(Long id, Professor professor);

    void deleteProfessor(Long id);

    Page<Professor> getProfessorsPage(Pageable pageable);
}