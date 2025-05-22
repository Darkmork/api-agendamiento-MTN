package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Alumno; // Updated import

import java.util.Optional;

/**
 * Interface de repositorio para la entidad Alumno.
 */
@Repository
public interface IAlumnoRepository extends JpaRepository<Alumno, Long> { // Updated interface name and generic type
    Optional<Alumno> findById(Long id); // Corrected method name to match JpaRepository standard

    Optional<Alumno> findByNome(String nombre); // Parameter name changed to Spanish and lowercase

    Optional<Alumno> findByNumeroEstudiante(String numeroEstudiante); // Method and parameter name changed

    Optional<Alumno> findByEmail(String email); // Parameter name changed to lowercase

    Optional<Alumno> findByTelefone(String telefone); // Parameter name changed to lowercase

}