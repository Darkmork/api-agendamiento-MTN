package com.example.demo.service;

import com.example.demo.Entities.Alumno; // Updated import
import com.example.demo.dto.AlumnoDTO; // Updated import
import com.example.demo.mapper.AlumnoMapper; // Updated import
import com.example.demo.repository.IAlumnoRepository; // Updated import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para operaciones relacionadas con Alumnos.
@Service
public class AlumnoService { // Class name updated

    @Autowired
    private IAlumnoRepository alumnoRepository; // Dependency updated

    @Autowired
    private AlumnoMapper alumnoMapper; // Dependency updated

    public List<AlumnoDTO> listarTodos() {
        return alumnoMapper.toDTOList(alumnoRepository.findAll());
    }

    public Optional<AlumnoDTO> buscarPorId(Long id) {
        return alumnoRepository.findById(id).map(alumnoMapper::toDTO);
    }

    public AlumnoDTO salvar(AlumnoDTO alumnoDTO) { // Parameter type updated
        Alumno alumno = alumnoMapper.toEntity(alumnoDTO); // Local variable type updated
        return alumnoMapper.toDTO(alumnoRepository.save(alumno));
    }

    public void deletar(Long id) {
        alumnoRepository.deleteById(id);
    }
}