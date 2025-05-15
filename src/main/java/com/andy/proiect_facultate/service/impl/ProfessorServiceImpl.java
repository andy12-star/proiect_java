package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.service.api.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    @Override
    public List<Professor> getAllProfessors() {
        log.info("Get all professors");
        return professorRepository.findAll();
    }

    @Override
    public Professor getProfessorById(Long id) {
        log.info("Get professor {}", id);
        return professorRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Professor with id " + id + " not found"));
    }

    @Override
    public Professor addProfessor(Professor professor) {
        log.info("Add professor {}", professor);
        return professorRepository.save(professor);
    }

    @Override
    public Professor updateProfessor(Long id, Professor professor) {
        log.info("Update professor {}", professor);
        Professor updatedProfessor = getProfessorById(id);
        updatedProfessor.setFirstName(professor.getFirstName());
        updatedProfessor.setLastName(professor.getLastName());
        updatedProfessor.setEmail(professor.getEmail());
        updatedProfessor.setDepartment(professor.getDepartment());
        return professorRepository.save(updatedProfessor);
    }

    @Override
    public void deleteProfessor(Long id) {
        log.info("Delete professor {}", id);
        professorRepository.deleteById(id);
    }
}
