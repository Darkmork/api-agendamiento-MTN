package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Materia;
import com.example.demo.dto.MateriaDTO;

// Interfaz Mapper para convertir entre la entidad Materia y MateriaDTO
@Mapper(componentModel = "spring")
public interface MateriaMapper {

    // Convierte una entidad Materia a un MateriaDTO
    MateriaDTO toDTO(Materia materia);

    // Convierte un MateriaDTO a una entidad Materia
    Materia toEntity(MateriaDTO materiaDTO);

    // Convierte una lista de entidades Materia a una lista de MateriaDTO
    List<MateriaDTO> toDTOList(List<Materia> materias);
}
