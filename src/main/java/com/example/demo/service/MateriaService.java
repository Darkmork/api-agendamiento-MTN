package com.example.demo.service;

import com.example.demo.Entities.Materia;
import com.example.demo.dto.MateriaDTO;
import com.example.demo.mapper.MateriaMapper;
import com.example.demo.repository.IMateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para operaciones relacionadas con Materias.
@Service
public class MateriaService {

    @Autowired
    private IMateriaRepository materiaRepository;

    @Autowired
    private MateriaMapper materiaMapper;

    public List<MateriaDTO> listarTodos() {
        return materiaMapper.toDTOList(materiaRepository.findAll());
    }

    public Optional<MateriaDTO> buscarPorId(Long id) {
        return materiaRepository.findById(id).map(materiaMapper::toDTO);
    }

    public MateriaDTO salvar(MateriaDTO materiaDTO) {
        Materia materia = materiaMapper.toEntity(materiaDTO);
        return materiaMapper.toDTO(materiaRepository.save(materia));
    }

    public void deletar(Long id) {
        materiaRepository.deleteById(id);
    }
}
