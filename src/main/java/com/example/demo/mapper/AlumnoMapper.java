package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Alumno;     // Updated import
import com.example.demo.dto.AlumnoDTO;     // Updated import

// Mapper para convertir entre la entidad Alumno y AlumnoDTO.
@Mapper(componentModel = "spring")
public interface AlumnoMapper { // Interface name updated
    AlumnoDTO toDTO(Alumno alumno); // Method signature updated

    Alumno toEntity(AlumnoDTO alumnoDTO); // Method signature updated

    List<AlumnoDTO> toDTOList(List<Alumno> alumnos); // Method signature updated
}
