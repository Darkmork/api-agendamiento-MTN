package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Materia; // Updated import
import java.util.Optional;

// Repositorio para la entidad Materia, extiende JpaRepository.
// Proporciona métodos para buscar materias por nombre.
@Repository
public interface IMateriaRepository extends JpaRepository<Materia, Long> { // Updated interface name and generic type
    Optional<Materia> findByNome(String nome); // Updated return type
}
