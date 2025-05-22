package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // Ensure Mapping is imported

import com.example.demo.Entities.Profesor;
import com.example.demo.dto.ProfesorDTO;

// Mapper para convertir entre la entidad Profesor y ProfesorDTO.
@Mapper(componentModel = "spring")
public interface ProfesorMapper { 

    @Mapping(source = "nombre", target = "nome") // Map Profesor.nombre to ProfesorDTO.nome
    ProfesorDTO toDTO(Profesor profesor); 

    @Mapping(source = "nome", target = "nombre") // Map ProfesorDTO.nome to Profesor.nombre
    Profesor toEntity(ProfesorDTO profesorDTO); 

    List<ProfesorDTO> toDTOList(List<Profesor> profesores); 
}
