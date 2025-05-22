package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.DisponibilidadProfesor; // Updated import
import com.example.demo.dto.DisponibilidadProfesorDTO; // Updated import
import com.example.demo.mapper.DisponibilidadProfesorMapper; // Updated import (corrected name)
import com.example.demo.repository.IDisponibilidadProfesorRepository; // Updated import

// Servicio para gestionar la disponibilidad de los profesores.
@Service
public class DisponibilidadProfesorService { // Class name updated

    @Autowired
    private DisponibilidadProfesorMapper disponibilidadProfesorMapper; // Dependency updated

    @Autowired
    private IDisponibilidadProfesorRepository disponibilidadProfesorRepository; // Dependency updated

    // Registra una nueva disponibilidad para un profesor.
    // El ID del profesor se obtiene del DTO.
    public DisponibilidadProfesorDTO registrarDisponibilidad(DisponibilidadProfesorDTO disponibilidadProfesorDTO) {
        // El profesorId ya está en disponibilidadProfesorDTO,
        // el mapper se encarga de mapearlo a la entidad.
        DisponibilidadProfesor disponibilidad = disponibilidadProfesorMapper.toEntity(disponibilidadProfesorDTO);
        return disponibilidadProfesorMapper.toDTO(disponibilidadProfesorRepository.save(disponibilidad));
    }

    // Lista todas las disponibilidades registradas para un profesor específico.
    public List<DisponibilidadProfesorDTO> listarDisponibilidadPorProfesor(Long profesorId) { // Method signature updated
        return disponibilidadProfesorMapper.toDTOList(disponibilidadProfesorRepository.findByProfesorId(profesorId)); // Repository method updated
    }

    // Elimina un registro de disponibilidad por su ID.
    public void removerDisponibilidade(Long id) { // Parameter name changed to lowercase 'id' for consistency
        disponibilidadProfesorRepository.deleteById(id); // Ensured correct repository field name
    }
}