package professorservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import professorservice.model.Professor;
import professorservice.repository.ProfessorRepository;

import java.util.List;

@Slf4j
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    @Override
    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    }

    @Override
    public Professor addProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor updateProfessor(Long id, Professor professor) {
        Professor existing = getProfessorById(id);
        existing.setDepartment(professor.getDepartment());
        return professorRepository.save(existing);
    }

    @Override
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    @Override
    public Page<Professor> getProfessorsPage(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }
}