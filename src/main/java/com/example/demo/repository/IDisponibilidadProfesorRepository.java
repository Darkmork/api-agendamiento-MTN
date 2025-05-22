package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.DisponibilidadProfesor; // Updated import
import java.util.List;

// Repositorio para la entidad DisponibilidadProfesor, extiende JpaRepository.
// Proporciona métodos para buscar la disponibilidad de un profesor por su ID.
@Repository
public interface IDisponibilidadProfesorRepository extends JpaRepository<DisponibilidadProfesor, Long> { // Updated interface name and generic type
    List<DisponibilidadProfesor> findByProfesorId(Long profesorId); // Method signature updated
}
