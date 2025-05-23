package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Materia; // Importar la entidad Materia
import java.util.Optional;

// Interfaz de repositorio para la entidad Materia.
@Repository
public interface IMateriaRepository extends JpaRepository<Materia, Long> { // Extender JpaRepository con Materia
    Optional<Materia> findByNome(String nome); // Método para buscar una materia por su nombre
}
