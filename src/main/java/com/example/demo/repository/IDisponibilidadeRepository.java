package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Disponibilidade;
import java.util.List;

// Interfaz de Repositorio para la entidad Disponibilidade.
// Proporciona métodos para interactuar con la base de datos respecto a las disponibilidades de los profesores.
@Repository
public interface IDisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
    // Busca una lista de disponibilidades asociadas a un ID de profesor específico.
    List<Disponibilidade> findByProfesorId(Long profesorId); // Método renombrado para usar profesorId
}
