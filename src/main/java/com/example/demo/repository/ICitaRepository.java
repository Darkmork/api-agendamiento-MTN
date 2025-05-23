package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Cita; // Importar la entidad Cita

// Interfaz de repositorio para la entidad Cita.
@Repository
public interface ICitaRepository extends JpaRepository<Cita, Long> { // Extender JpaRepository con Cita
        // Query para buscar citas con filtros dinámicos
        @Query("""
                        SELECT c FROM Cita c
                        WHERE (:profesorId IS NULL OR c.profesor.id = :profesorId)
                          AND (:apoderadoId IS NULL OR c.apoderado.id = :apoderadoId)
                          AND (:status IS NULL OR c.status = :status)
                          AND (:dataInicio IS NULL OR c.dataHora >= :dataInicio)
                          AND (:dataFim IS NULL OR c.dataHora <= :dataFim)
                        """)
        List<Cita> findAllUsing(@Param("profesorId") Long profesorId, @Param("apoderadoId") Long apoderadoId,
                        @Param("status") String status, @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim);

        // Query para buscar citas de un profesor específico en un rango de fechas/horas
        @Query("SELECT c FROM Cita c WHERE c.profesor.id = :profesorId AND c.dataHora BETWEEN :inicio AND :fim")
        List<Cita> findByProfesorIdAndDataHoraBetween(@Param("profesorId") Long profesorId,
                        @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
