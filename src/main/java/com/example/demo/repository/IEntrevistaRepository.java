package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Entrevista; // Updated import

// Repositorio para la entidad Entrevista, extiende JpaRepository.
// Proporciona métodos para buscar y filtrar entrevistas.
@Repository
public interface IEntrevistaRepository extends JpaRepository<Entrevista, Long> { // Updated interface name and generic type
        @Query("""
                        SELECT e FROM Entrevista e
                        WHERE (:profesorId IS NULL OR e.profesor.id = :profesorId)
                          AND (:alumnoId IS NULL OR e.alumno.id = :alumnoId)
                          AND (:status IS NULL OR e.status = :status)
                          AND (:dataInicio IS NULL OR e.dataHora >= :dataInicio)
                          AND (:dataFim IS NULL OR e.dataHora <= :dataFim)
                        """)
        List<Entrevista> findAllUsing(@Param("profesorId") Long profesorId, @Param("alumnoId") Long alumnoId, // Parameters updated
                        @Param("status") String status, @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim); // Return type updated

        @Query("SELECT e FROM Entrevista e WHERE e.profesor.id = :profesorId AND e.dataHora BETWEEN :inicio AND :fim")
        List<Entrevista> findByProfesorIdAndDataHoraBetween(@Param("profesorId") Long profesorId, // Parameter updated
                        @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim); // Return type updated
}
