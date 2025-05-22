package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Materia;       // Updated import
import com.example.demo.dto.MateriaDTO;     // Updated import

// Mapper para convertir entre la entidad Materia y MateriaDTO.
@Mapper(componentModel = "spring")
public interface MateriaMapper { // Interface name updated
    MateriaDTO toDTO(Materia materia); // Method signature updated

    Materia toEntity(MateriaDTO materiaDTO); // Method signature updated

    List<MateriaDTO> toDTOList(List<Materia> materias); // Method signature updated
}
