package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Profesor;
import com.example.demo.dto.ProfesorDTO;

// Interfaz Mapper para convertir entre la entidad Profesor y ProfesorDTO
@Mapper(componentModel = "spring")
public interface ProfesorMapper {

    // Convierte una entidad Profesor a un ProfesorDTO
    @Mapping(source = "identificadorProfesor", target = "identificadorProfesor")
    @Mapping(source = "materiaId", target = "materiaId")
    ProfesorDTO toDTO(Profesor profesor);

    // Convierte un ProfesorDTO a una entidad Profesor
    @Mapping(source = "identificadorProfesor", target = "identificadorProfesor")
    @Mapping(source = "materiaId", target = "materiaId")
    Profesor toEntity(ProfesorDTO profesorDTO);

    // Convierte una lista de entidades Profesor a una lista de ProfesorDTO
    List<ProfesorDTO> toDTOList(List<Profesor> profesores);
}
