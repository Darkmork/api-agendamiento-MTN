package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Entities.Disponibilidade;
import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.mapper.DisponibilidadeMapper; // Mapper actualizado
import com.example.demo.repository.IDisponibilidadeRepository;
// Se podría inyectar IProfesorRepository para validar la existencia del profesor si fuera necesario.
// import com.example.demo.repository.IProfesorRepository; 

// Servicio para la lógica de negocio de Disponibilidades de Profesores
@Service
public class DisponibilidadeService {

    @Autowired
    private DisponibilidadeMapper disponibilidadeMapper; // Mapper actualizado

    @Autowired
    private IDisponibilidadeRepository disponibilidadeRepository;

    // @Autowired
    // private IProfesorRepository profesorRepository; // Descomentar si se añade validación de existencia de Profesor

    /**
     * Registra una nueva disponibilidad para un profesor.
     * El profesorId debe estar presente en el DTO.
     * @param disponibilidadDTO DTO con los datos de la disponibilidad, incluyendo profesorId.
     * @return DTO de la disponibilidad guardada.
     * @throws IllegalArgumentException si el profesorId no se proporciona en el DTO.
     */
    public DisponibilidadeDTO registrarDisponibilidade(DisponibilidadeDTO disponibilidadDTO) {
        if (disponibilidadeDTO.getProfesorId() == null) {
            throw new IllegalArgumentException("El ID del profesor es obligatorio para registrar la disponibilidad.");
        }
        // Opcional: Verificar si el profesor existe
        // profesorRepository.findById(disponibilidadeDTO.getProfesorId())
        //         .orElseThrow(() -> new IllegalArgumentException("Profesor con ID " + disponibilidadDTO.getProfesorId() + " no encontrado."));

        Disponibilidade disponibilidade = disponibilidadeMapper.toEntity(disponibilidadeDTO);
        return disponibilidadeMapper.toDTO(disponibilidadeRepository.save(disponibilidade));
    }

    /**
     * Lista todas las disponibilidades para un profesor específico.
     * @param profesorId ID del profesor.
     * @return Lista de DTOs de disponibilidad.
     */
    public List<DisponibilidadeDTO> listarDisponibilidadesPorProfesor(Long profesorId) { // Nombre de método y parámetro actualizados
        if (profesorId == null) {
            throw new IllegalArgumentException("El ID del profesor no puede ser nulo.");
        }
        return disponibilidadeMapper.toDTOList(disponibilidadeRepository.findByProfesorId(profesorId)); // Llamada al repositorio actualizada
    }

    /**
     * Elimina una disponibilidad por su ID.
     * @param id ID de la disponibilidad a eliminar.
     * @throws IllegalArgumentException si la disponibilidad no se encuentra (manejado por Spring Data si se configura, o se puede añadir chequeo).
     */
    public void removerDisponibilidade(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la disponibilidad no puede ser nulo.");
        }
        // Opcional: Verificar si la disponibilidad existe antes de intentar eliminarla
        // if (!disponibilidadeRepository.existsById(id)) {
        //     throw new IllegalArgumentException("Disponibilidad con ID " + id + " no encontrada.");
        // }
        disponibilidadeRepository.deleteById(id);
    }
}