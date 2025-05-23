package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.dto.DisponibilidadeDTO;

// Interfaz Mapper para convertir entre la entidad Disponibilidade y DisponibilidadeDTO
@Mapper(componentModel = "spring")
public interface DisponibilidadeMapper { // Nombre de la interfaz corregido

    // Convierte una entidad Disponibilidade a un DisponibilidadeDTO
    // El campo profesorId se mapea automáticamente porque tiene el mismo nombre en ambas clases.
    DisponibilidadeDTO toDTO(Disponibilidade disponibilidade);

    // Convierte un DisponibilidadeDTO a una entidad Disponibilidade
    // El campo profesorId se mapea automáticamente.
    Disponibilidade toEntity(DisponibilidadeDTO disponibilidadeDTO);

    // Convierte una lista de entidades Disponibilidade a una lista de DisponibilidadeDTO
    List<DisponibilidadeDTO> toDTOList(List<Disponibilidade> disponibilidades);
}
