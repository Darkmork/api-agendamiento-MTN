package com.example.demo.service;

import com.example.demo.Entities.Apoderado; // Importar Apoderado
import com.example.demo.dto.ApoderadoDTO;   // Importar ApoderadoDTO
import com.example.demo.mapper.ApoderadoMapper; // Importar ApoderadoMapper
import com.example.demo.repository.IApoderadoRepository; // Importar IApoderadoRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para la lógica de negocio de Apoderados
@Service
public class ApoderadoService {

    @Autowired
    private IApoderadoRepository apoderadoRepository; // Repositorio para Apoderados

    @Autowired
    private ApoderadoMapper apoderadoMapper; // Mapper para Apoderados

    // Lista todos los apoderados
    public List<ApoderadoDTO> listarTodos() {
        return apoderadoMapper.toDTOList(apoderadoRepository.findAll());
    }

    // Busca un apoderado por su ID
    public Optional<ApoderadoDTO> buscarPorId(Long id) {
        return apoderadoRepository.findById(id).map(apoderadoMapper::toDTO);
    }

    // Guarda un nuevo apoderado o actualiza uno existente
    public ApoderadoDTO salvar(ApoderadoDTO apoderadoDTO) {
        Apoderado apoderado = apoderadoMapper.toEntity(apoderadoDTO);
        return apoderadoMapper.toDTO(apoderadoRepository.save(apoderado));
    }

    // Elimina un apoderado por su ID
    public void deletar(Long id) {
        apoderadoRepository.deleteById(id);
    }
}