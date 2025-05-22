package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Profesor; // Updated import
import com.example.demo.dto.ProfesorDTO;   // Updated import

// Mapper para convertir entre la entidad Profesor y ProfesorDTO.
@Mapper(componentModel = "spring")
public interface ProfesorMapper { // Interface name updated
    ProfesorDTO toDTO(Profesor profesor); // Method signature updated

    Profesor toEntity(ProfesorDTO profesorDTO); // Method signature updated

    List<ProfesorDTO> toDTOList(List<Profesor> profesores); // Method signature updated
}
