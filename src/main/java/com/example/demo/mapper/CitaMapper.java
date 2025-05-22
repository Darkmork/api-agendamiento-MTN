package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Cita;
import com.example.demo.dto.CitaDTO;

// Interfaz Mapper para convertir entre la entidad Cita y CitaDTO
@Mapper(componentModel = "spring")
public interface CitaMapper {

    // Convierte una entidad Cita a un CitaDTO
    @Mapping(source = "apoderado.id", target = "apoderadoId")
    @Mapping(source = "profesor.id", target = "profesorId")
    @Mapping(source = "apoderado.nome", target = "nombreApoderado") // 'nome' es el campo en Apoderado.java
    @Mapping(source = "profesor.nome", target = "nombreProfesor")   // 'nome' es el campo en Profesor.java
    CitaDTO toDTO(Cita cita);

    // Convierte un CitaDTO a una entidad Cita
    // Los campos nombreApoderado y nombreProfesor del DTO no se mapean de nuevo a la entidad Cita,
    // ya que la entidad Cita espera objetos completos Apoderado y Profesor,
    // que se cargarían usando apoderadoId y profesorId.
    @Mapping(target = "apoderado.id", source = "apoderadoId")
    @Mapping(target = "profesor.id", source = "profesorId")
    Cita toEntity(CitaDTO citaDTO);

    // Convierte una lista de entidades Cita a una lista de CitaDTO
    List<CitaDTO> toDTOList(List<Cita> citas);
}
