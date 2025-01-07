package com.andy.proiect_facultate.service;

import com.andy.proiect_facultate.model.entity.Professor;
import com.andy.proiect_facultate.repository.ProfessorRepository;
import com.andy.proiect_facultate.service.impl.ProfessorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {
    @InjectMocks
    private ProfessorServiceImpl professorService;
    @Mock
    private ProfessorRepository professorRepository;

    @Test
    void givenProfessor_whenGetAllProfessors_thenReturnAllProfessors() {
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();
        when(professorRepository.findAll()).thenReturn(List.of(professor1, professor2));

        List<Professor> professors = professorService.getAllProfessors();

        assertNotNull(professors);
        assertEquals(professors.size(), 2);
        verify(professorRepository, times(1)).findAll();
    }

    @Test
    void givenProfessorExists_WhenGetProfessorById_ThenReturnProfessor() {
        Professor professor = new Professor();
        professor.setId(1L);
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

        Professor foundProfessor = professorService.getProfessorById(1L);

        assertNotNull(foundProfessor);
        assertEquals(1L, foundProfessor.getId());
        verify(professorRepository, times(1)).findById(1L);
    }

    @Test
    void givenProfessorDoesNotExist_WhenGetProfessorById_ThenThrowException() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> professorService.getProfessorById(1L));
        assertEquals("Professor with id 1 not found", exception.getMessage());
        verify(professorRepository, times(1)).findById(1L);
    }

    @Test
    void givenValidProfessor_WhenAddProfessor_ThenSaveAndReturnProfessor() {
        Professor professor = new Professor();
        when(professorRepository.save(professor)).thenReturn(professor);

        Professor savedProfessor = professorService.addProfessor(professor);

        assertNotNull(savedProfessor);
        verify(professorRepository, times(1)).save(professor);
    }

    @Test
    void givenValidIdAndProfessor_WhenUpdateProfessor_ThenSaveAndReturnUpdatedProfessor() {
        Professor existingProfessor = new Professor();
        existingProfessor.setId(1L);
        existingProfessor.setFirstName("John");
        existingProfessor.setLastName("Doe");
        existingProfessor.setEmail("john.doe@example.com");
        existingProfessor.setDepartment("Math");

        Professor updatedData = new Professor();
        updatedData.setFirstName("Jane");
        updatedData.setLastName("Smith");
        updatedData.setEmail("jane.smith@example.com");
        updatedData.setDepartment("Physics");

        when(professorRepository.findById(1L)).thenReturn(Optional.of(existingProfessor));
        when(professorRepository.save(existingProfessor)).thenReturn(existingProfessor);

        Professor updatedProfessor = professorService.updateProfessor(1L, updatedData);

        // Assert
        assertNotNull(updatedProfessor);
        assertEquals("Jane", updatedProfessor.getFirstName());
        assertEquals("Smith", updatedProfessor.getLastName());
        assertEquals("jane.smith@example.com", updatedProfessor.getEmail());
        assertEquals("Physics", updatedProfessor.getDepartment());
        verify(professorRepository, times(1)).findById(1L);
        verify(professorRepository, times(1)).save(existingProfessor);
    }

    @Test
    void givenValidId_WhenDeleteProfessor_ThenDeleteProfessor() {
        doNothing().when(professorRepository).deleteById(1L);

        professorService.deleteProfessor(1L);

        verify(professorRepository, times(1)).deleteById(1L);
    }
}
