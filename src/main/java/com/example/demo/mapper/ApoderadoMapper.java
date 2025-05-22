package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Apoderado;
import com.example.demo.dto.ApoderadoDTO;

// Interfaz Mapper para convertir entre la entidad Apoderado y ApoderadoDTO
@Mapper(componentModel = "spring")
public interface ApoderadoMapper {

    // Convierte una entidad Apoderado a un ApoderadoDTO
    @Mapping(source = "ativo", target = "activo")
    ApoderadoDTO toDTO(Apoderado apoderado);

    // Convierte un ApoderadoDTO a una entidad Apoderado
    @Mapping(source = "activo", target = "ativo")
    Apoderado toEntity(ApoderadoDTO apoderadoDTO);

    // Convierte una lista de entidades Apoderado a una lista de ApoderadoDTO
    List<ApoderadoDTO> toDTOList(List<Apoderado> apoderados);
}
