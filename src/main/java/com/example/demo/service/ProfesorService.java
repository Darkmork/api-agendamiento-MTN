package com.example.demo.service;

import com.example.demo.Entities.Profesor;
import com.example.demo.dto.ProfesorDTO;
import com.example.demo.mapper.ProfesorMapper;
import com.example.demo.repository.IProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para operaciones relacionadas con Profesores.
@Service
public class ProfesorService {

    @Autowired
    private IProfesorRepository profesorRepository;

    @Autowired
    private ProfesorMapper profesorMapper;

    public List<ProfesorDTO> listarTodos() {
        return profesorMapper.toDTOList(profesorRepository.findAll());
    }

    public Optional<ProfesorDTO> buscarPorId(Long id) {
        return profesorRepository.findById(id).map(profesorMapper::toDTO);
    }

    public ProfesorDTO salvar(ProfesorDTO profesorDTO) {
        Profesor profesor = profesorMapper.toEntity(profesorDTO);
        return profesorMapper.toDTO(profesorRepository.save(profesor));
    }

    public void deletar(Long id) {
        profesorRepository.deleteById(id);
    }
}
