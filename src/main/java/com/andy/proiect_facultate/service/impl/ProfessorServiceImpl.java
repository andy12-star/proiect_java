package com.andy.proiect_facultate.service.impl;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.service.api.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return professorRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Professor with id " + id + " not found"));
    }

    @Override
    public Professor addProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor updateProfessor(Long id, Professor professor) {
        Professor updatedProfessor = getProfessorById(id);
        updatedProfessor.setFirstName(professor.getFirstName());
        updatedProfessor.setLastName(professor.getLastName());
        updatedProfessor.setEmail(professor.getEmail());
        updatedProfessor.setDepartment(professor.getDepartment());
        return professorRepository.save(updatedProfessor);
    }

    @Override
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
