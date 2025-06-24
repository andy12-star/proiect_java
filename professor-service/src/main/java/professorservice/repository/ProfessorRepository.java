package professorservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import professorservice.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Page<Professor> findAll(Pageable pageable);
}