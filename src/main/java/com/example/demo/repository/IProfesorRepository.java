package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entities.Profesor;

// Este fragmento de código es el repositorio de Profesor, que extiende la interfaz JpaRepository
// Posee métodos para buscar profesores por id, nombre, identificadorProfesor y materiaId
@Repository
public interface IProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNome(String nome); // Busca profesores por nombre

    Optional<Profesor> findByIdentificadorProfesor(String identificadorProfesor); // Busca un profesor por su identificador único

    List<Profesor> findByMateriaId(Long materiaId); // Busca profesores por el ID de la materia que imparten
}