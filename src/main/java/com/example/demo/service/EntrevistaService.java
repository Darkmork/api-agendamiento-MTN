package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entities.Entrevista; // Updated import
import com.example.demo.Entities.DisponibilidadProfesor; // Updated import
import com.example.demo.Entities.Profesor; // Updated import
import com.example.demo.Entities.Alumno; // Updated import
import com.example.demo.dto.EntrevistaDTO; // Updated import
import com.example.demo.mapper.EntrevistaMapper; // Updated import
import com.example.demo.repository.IEntrevistaRepository; // Updated import
import com.example.demo.repository.IDisponibilidadProfesorRepository; // Updated import
import com.example.demo.repository.IProfesorRepository; // Updated import
import com.example.demo.repository.IAlumnoRepository; // Updated import

// Servicio para gestionar la lógica de negocio de las Entrevistas.
@Service
public class EntrevistaService { // Class name updated

    @Autowired
    private IEntrevistaRepository entrevistaRepository; // Dependency updated

    @Autowired
    private IAlumnoRepository alumnoRepository; // Dependency updated

    @Autowired
    private IProfesorRepository profesorRepository; // Dependency updated

    @Autowired
    private IDisponibilidadProfesorRepository disponibilidadProfesorRepository; // Dependency updated

    @Autowired
    private EntrevistaMapper entrevistaMapper; // Dependency updated

    // Constantes para el estado de la entrevista
    public static final String STATUS_PROGRAMADA = "programada"; // Status updated and translated
    public static final String STATUS_CANCELADA = "cancelada";   // Status (value same, constant name updated for clarity if needed, but value is key)
    public static final String STATUS_COMPLETADA = "completada"; // Status updated and translated

    /**
     * Programa una nueva entrevista.
     * Valida la existencia del alumno y profesor, la disponibilidad del profesor y conflictos de horario.
     * @param entrevistaDTO DTO con la información de la entrevista a programar.
     * @return EntrevistaDTO de la entrevista programada.
     * @throws IllegalArgumentException Si alguna validación falla.
     */
    @Transactional
    public EntrevistaDTO programar(EntrevistaDTO entrevistaDTO) { // Method name and parameter updated
        Alumno alumno = alumnoRepository.findById(entrevistaDTO.getAlumnoId()) // DTO field updated
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado")); // Error message translated

        Profesor profesor = profesorRepository.findById(entrevistaDTO.getProfesorId()) // DTO field updated
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado")); // Error message translated

        LocalDateTime dataHora = entrevistaDTO.getDataHora();

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la entrevista debe ser futura"); // Error message translated
        }

        LocalTime horario = dataHora.toLocalTime();
        // Ejemplo de validación de horario (opcional) - Comentario traducido
        // if (horario.isBefore(LocalTime.of(8, 0)) || horario.isAfter(LocalTime.of(18, 0))) {
        //     throw new IllegalArgumentException("Las entrevistas solo pueden ser programadas entre 8h y 18h");
        // }

        String diaSemana = dataHora.getDayOfWeek().toString();
        // Usa el repositorio de disponibilidad del profesor
        List<DisponibilidadProfesor> disponibilidades = disponibilidadProfesorRepository.findByProfesorId(profesor.getId()); // Repository and method updated

        boolean horarioDisponivel = false;
        for (DisponibilidadProfesor disp : disponibilidades) { // Type updated
            if (disp.getDiaDaSemana().equalsIgnoreCase(diaSemana) && !horario.isBefore(disp.getHorarioInicio())
                    && !horario.isAfter(disp.getHorarioFim())) {
                horarioDisponivel = true;
                break;
            }
        }

        if (!horarioDisponivel) {
            throw new IllegalArgumentException("Profesor no disponible en este horario"); // Error message translated
        }

        LocalDateTime inicio = dataHora.minusMinutes(30); // Considerar la duración de la entrevista si varía
        LocalDateTime fim = dataHora.plusMinutes(30);

        // Usa el repositorio de entrevistas para buscar conflictos
        List<Entrevista> entrevistasConflitantes = entrevistaRepository.findByProfesorIdAndDataHoraBetween(profesor.getId(), // Repository and method updated
                inicio, fim);

        if (!entrevistasConflitantes.isEmpty()) {
            throw new IllegalArgumentException("Profesor ya tiene una entrevista programada en este horario"); // Error message translated
        }

        Entrevista entrevista = new Entrevista(); // Type updated
        entrevista.setAlumno(alumno); // Setter updated
        entrevista.setProfesor(profesor); // Setter updated
        entrevista.setDataHora(dataHora);
        entrevista.setStatus(STATUS_PROGRAMADA); // Status constant updated
        entrevista.setObservacoes(entrevistaDTO.getObservacoes());

        return entrevistaMapper.toDTO(entrevistaRepository.save(entrevista));
    }

    /**
     * Lista todas las entrevistas con filtros opcionales.
     * @param profesorId ID del profesor para filtrar (opcional).
     * @param alumnoId ID del alumno para filtrar (opcional).
     * @param status Estado para filtrar (opcional: programada, cancelada, completada).
     * @param dataInicio Fecha de inicio del rango para filtrar (opcional).
     * @param dataFim Fecha de fin del rango para filtrar (opcional).
     * @return Lista de EntrevistaDTO.
     */
    public List<EntrevistaDTO> listar(Long profesorId, Long alumnoId, String status, LocalDateTime dataInicio, // Parameters renamed for clarity
            LocalDateTime dataFim) {
        List<String> statusList = List.of(STATUS_PROGRAMADA, STATUS_CANCELADA, STATUS_COMPLETADA); // Status constants updated
        if (status != null && (!statusList.contains(status) || status.isEmpty())) {
            throw new IllegalArgumentException("Estado inválido. Use: programada, cancelada o completada"); // Error message translated
        }

        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin"); // Error message translated
        }

        List<Entrevista> entrevistas = entrevistaRepository.findAllUsing(profesorId, alumnoId, status, dataInicio, dataFim); // Repository and parameters updated

        return entrevistaMapper.toDTOList(entrevistas);
    }

    /**
     * Busca una entrevista por su ID.
     * @param id ID de la entrevista.
     * @return Optional con EntrevistaDTO si se encuentra.
     */
    public Optional<EntrevistaDTO> buscarPorId(Long id) {
        return entrevistaRepository.findById(id).map(entrevistaMapper::toDTO);
    }

    /**
     * Actualiza el estado de una entrevista.
     * @param id ID de la entrevista.
     * @param nuevoStatus Nuevo estado (programada, cancelada, completada).
     * @return EntrevistaDTO actualizada.
     */
    @Transactional
    public EntrevistaDTO actualizarStatus(Long id, String nuevoStatus) { // Parameter is 'nuevoStatus'
        // Using 'nuevoStatus' in the condition
        if (!STATUS_PROGRAMADA.equals(nuevoStatus) && !STATUS_CANCELADA.equals(nuevoStatus) 
                && !STATUS_COMPLETADA.equals(nuevoStatus)) {
            throw new IllegalArgumentException("Estado inválido. Use: programada, cancelada o completada");
        }

        Entrevista entrevista = entrevistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrevista no encontrada"));

        entrevista.setStatus(nuevoStatus); // Using 'nuevoStatus' to set status

        return entrevistaMapper.toDTO(entrevistaRepository.save(entrevista));
    }

    /**
     * Cancela una entrevista.
     * Solo permitido con al menos 24 horas de anticipación.
     * @param id ID de la entrevista a cancelar.
     * @throws IllegalArgumentException Si la entrevista no se encuentra, ya está cancelada o no cumple el requisito de anticipación.
     */
    @Transactional
    public void cancelar(Long id) {
        Entrevista entrevista = entrevistaRepository.findById(id) // Type updated
                .orElseThrow(() -> new IllegalArgumentException("Entrevista no encontrada")); // Error message translated

        if (STATUS_CANCELADA.equals(entrevista.getStatus())) {
            throw new IllegalArgumentException("La entrevista ya está cancelada"); // Error message translated
        }

        if (entrevista.getDataHora().minusHours(24).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La cancelación solo se permite con al menos 24 horas de anticipación"); // Error message translated
        }

        entrevista.setStatus(STATUS_CANCELADA);
        entrevistaRepository.save(entrevista);
    }
}
