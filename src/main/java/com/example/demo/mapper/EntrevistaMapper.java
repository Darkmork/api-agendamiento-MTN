package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Entrevista; // Updated import
import com.example.demo.dto.EntrevistaDTO;   // Updated import

// Mapper para convertir entre la entidad Entrevista y EntrevistaDTO.
@Mapper(componentModel = "spring")
public interface EntrevistaMapper { // Interface name updated

    @Mapping(source = "alumno.id", target = "alumnoId")
    @Mapping(source = "profesor.id", target = "profesorId")
    @Mapping(source = "alumno.nombre", target = "nombreAlumno")   // Corrected from "alumno.nome" to "alumno.nombre"
    @Mapping(source = "profesor.nombre", target = "nombreProfesor") // Corrected from "profesor.nome" to "profesor.nombre"
    EntrevistaDTO toDTO(Entrevista entrevista);

    @Mapping(target = "alumno.id", source = "alumnoId")
    @Mapping(target = "profesor.id", source = "profesorId")
    // Note: Mapping for nombreAlumno and nombreProfesor back to entity fields is not needed
    // as these are typically derived/read-only in DTO or set directly if the entity structure allows.
    // Assuming Entrevista entity does not have nombreAlumno/nombreProfesor fields directly,
    // but rather Alumno and Profesor objects.
    Entrevista toEntity(EntrevistaDTO entrevistaDTO);

    List<EntrevistaDTO> toDTOList(List<Entrevista> entrevistas); // Method signature updated
}
