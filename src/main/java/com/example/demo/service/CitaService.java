package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entities.Cita; // Actualizado
import com.example.demo.Entities.Disponibilidade; // Se asume que se refactorizará o se mantendrá compatible
import com.example.demo.Entities.Profesor; // Actualizado
import com.example.demo.Entities.Apoderado; // Actualizado
import com.example.demo.dto.CitaDTO; // Actualizado
import com.example.demo.mapper.CitaMapper; // Actualizado
import com.example.demo.repository.ICitaRepository; // Actualizado
import com.example.demo.repository.IDisponibilidadeRepository;
import com.example.demo.repository.IProfesorRepository; // Actualizado
import com.example.demo.repository.IApoderadoRepository; // Actualizado

@Service
public class CitaService { // Nombre de clase actualizado

    @Autowired
    private ICitaRepository citaRepository; // Repositorio actualizado

    @Autowired
    private IApoderadoRepository apoderadoRepository; // Repositorio actualizado

    @Autowired
    private IProfesorRepository profesorRepository; // Repositorio actualizado

    @Autowired
    private IDisponibilidadeRepository disponibilidadeRepository; // Se mantiene, revisar su refactorización después

    @Autowired
    private CitaMapper citaMapper; // Mapper actualizado

    // Constantes para estados de cita
    public static final String ESTADO_PROGRAMADA = "programada";
    public static final String ESTADO_CANCELADA = "cancelada";
    public static final String ESTADO_COMPLETADA = "completada";

    /**
     * Programa una nueva cita
     */
    @Transactional
    public CitaDTO agendar(CitaDTO citaDTO) { // DTO y nombre de método actualizados
        Apoderado apoderado = apoderadoRepository.findById(citaDTO.getApoderadoId()) // Lógica actualizada
                .orElseThrow(() -> new IllegalArgumentException("Apoderado no encontrado"));

        Profesor profesor = profesorRepository.findById(citaDTO.getProfesorId()) // Lógica actualizada
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));

        LocalDateTime dataHora = citaDTO.getDataHora();

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la cita debe ser futura");
        }

        LocalTime horario = dataHora.toLocalTime();
        // Ejemplo de validación de horario (opcional)
        // if (horario.isBefore(LocalTime.of(8, 0)) || horario.isAfter(LocalTime.of(18,
        // 0))) {
        // throw new IllegalArgumentException("Las citas sólo pueden ser programadas entre 8h
        // y 18h");
        // }

        String diaSemana = dataHora.getDayOfWeek().toString();
        // Asumiendo que Disponibilidade también se refactorizará para usar profesorId o un mecanismo compatible
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByProfesorId(profesor.getId());

        boolean horarioDisponivelDentroDeDisponibilidade = false;
        for (Disponibilidade disp : disponibilidades) {
            // Verifica si el intervalo completo de 35 minutos de la cita está dentro del horario de disponibilidad
            if (disp.getDiaDaSemana().equalsIgnoreCase(diaSemana) && 
                !horario.isBefore(disp.getHorarioInicio()) && 
                !horario.plusMinutes(35).isAfter(disp.getHorarioFim())) {
                horarioDisponivelDentroDeDisponibilidade = true;
                break;
            }
        }

        if (!horarioDisponivelDentroDeDisponibilidade) {
            throw new IllegalArgumentException("Profesor no disponible para un intervalo de 35 minutos en este horario según su disponibilidad general.");
        }

        // Verificación de conflictos con otras citas, considerando duración de 35 minutos
        // Se busca en un rango un poco más amplio para asegurar que se capturan todas las citas que podrían solaparse.
        // Por ejemplo, una cita existente que empieza 30 mins antes podría solaparse.
        LocalDateTime rangoInicioBusqueda = dataHora.minusMinutes(34);
        LocalDateTime rangoFinBusqueda = dataHora.plusMinutes(34);

        List<Cita> citasPotencialmenteConflitantes = citaRepository.findByProfesorIdAndDataHoraBetween(
                profesor.getId(), rangoInicioBusqueda, rangoFinBusqueda);

        LocalDateTime inicioNuevaCita = dataHora;
        LocalDateTime finNuevaCita = dataHora.plusMinutes(35);

        for (Cita citaExistente : citasPotencialmenteConflitantes) {
            LocalDateTime inicioCitaExistente = citaExistente.getDataHora();
            LocalDateTime finCitaExistente = citaExistente.getDataHora().plusMinutes(35);

            // Lógica de solapamiento: si la nueva cita empieza antes de que termine la existente Y la nueva cita termina después de que empiece la existente
            if (inicioNuevaCita.isBefore(finCitaExistente) && finNuevaCita.isAfter(inicioCitaExistente)) {
                throw new IllegalArgumentException("Profesor ya posee una cita programada que interfiere con el intervalo de 35 minutos para este horario.");
            }
        }

        Cita cita = new Cita(); // Entidad actualizada
        cita.setApoderado(apoderado); // Campo actualizado
        cita.setProfesor(profesor); // Campo actualizado
        cita.setDataHora(dataHora);
        cita.setStatus(ESTADO_PROGRAMADA); // Estado actualizado
        cita.setObservacoes(citaDTO.getObservacoes());

        return citaMapper.toDTO(citaRepository.save(cita));
    }

    /**
     * Lista todas las citas con filtros opcionales.
     * Puede ser utilizada para obtener la vista de citas de un profesor específico
     * pasando solo el profesorId y dejando los otros parámetros de filtro como nulos o según se necesite.
     * Por ejemplo, para obtener todas las citas de un profesor: listar(profesorId, null, null, null, null).
     * Para obtener citas programadas futuras de un profesor: listar(profesorId, null, ESTADO_PROGRAMADA, LocalDateTime.now(), null).
     */
    public List<CitaDTO> listar(Long profesorId, Long apoderadoId, String status, LocalDateTime dataInicio, // Parámetros actualizados
            LocalDateTime dataFim) {
        List<String> statusList = List.of(ESTADO_PROGRAMADA, ESTADO_CANCELADA, ESTADO_COMPLETADA); // Estados actualizados
        if (status != null && (!statusList.contains(status) || status.isEmpty())) {
            throw new IllegalArgumentException("Estado inválido. Use: Programada, Cancelada o Completada");
        }

        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Fecha de inicio no puede ser posterior a la fecha de fin");
        }

        List<Cita> citas = citaRepository.findAllUsing(profesorId, apoderadoId, status, dataInicio, dataFim); // Lógica actualizada

        return citaMapper.toDTOList(citas);
    }

    /**
     * Busca una cita por el ID
     */
    public Optional<CitaDTO> buscarPorId(Long id) { // DTO actualizado
        return citaRepository.findById(id).map(citaMapper::toDTO);
    }

    /**
     * Actualiza el estado de una cita
     */
    @Transactional
    public CitaDTO atualizarStatus(Long id, String nuevoEstado) { // DTO y parámetro actualizados
        if (!ESTADO_PROGRAMADA.equals(nuevoEstado) && !ESTADO_CANCELADA.equals(nuevoEstado) // Estados actualizados
                && !ESTADO_COMPLETADA.equals(nuevoEstado)) {
            throw new IllegalArgumentException("Estado inválido. Use: Programada, Cancelada o Completada");
        }

        Cita cita = citaRepository.findById(id) // Entidad actualizada
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        cita.setStatus(nuevoEstado);

        return citaMapper.toDTO(citaRepository.save(cita));
    }

    /**
     * Cancela una cita (sólo permitido con 24h de antecedência)
     */
    @Transactional
    public void cancelar(Long id) {
        Cita cita = citaRepository.findById(id) // Entidad actualizada
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        if (ESTADO_CANCELADA.equals(cita.getStatus())) { // Estado actualizado
            throw new IllegalArgumentException("Cita ya está cancelada");
        }

        if (cita.getDataHora().minusHours(24).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cancelación sólo es permitida con no mínimo 24 horas de antecedencia");
        }

        cita.setStatus(ESTADO_CANCELADA); // Estado actualizado
        citaRepository.save(cita);
    }
}
