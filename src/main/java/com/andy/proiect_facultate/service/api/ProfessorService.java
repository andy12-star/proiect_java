package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.entity.Professor;

import java.util.List;

public interface ProfessorService {

    List<Professor> getAllProfessors();

    Professor getProfessorById(Long id);

    Professor addProfessor(Professor professor);

    Professor updateProfessor(Long id,Professor professor);

    void deleteProfessor(Long id);
}
