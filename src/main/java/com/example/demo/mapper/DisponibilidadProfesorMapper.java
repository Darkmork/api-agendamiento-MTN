package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.DisponibilidadProfesor; // Updated import
import com.example.demo.dto.DisponibilidadProfesorDTO;   // Updated import

// Mapper para convertir entre la entidad DisponibilidadProfesor y DisponibilidadProfesorDTO.
@Mapper(componentModel = "spring")
public interface DisponibilidadProfesorMapper { // Interface name updated
    DisponibilidadProfesorDTO toDTO(DisponibilidadProfesor disponibilidadProfesor); // Method signature updated

    DisponibilidadProfesor toEntity(DisponibilidadProfesorDTO disponibilidadProfesorDTO); // Method signature updated

    List<DisponibilidadProfesorDTO> toDTOList(List<DisponibilidadProfesor> disponibilidadProfesores); // Method signature updated
}
