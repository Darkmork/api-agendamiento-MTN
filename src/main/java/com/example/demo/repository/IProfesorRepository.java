package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entities.Profesor;

@Repository
public interface IProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNome(String nome);

    Optional<Profesor> findByIdentificacionProfesor(String identificacionProfesor);

    List<Profesor> findByMateriaId(Long materiaId);
}

// Repositorio para la entidad Profesor, extiende JpaRepository.
// Proporciona métodos para buscar profesores por id, nombre, identificacionProfesor y materiaId.