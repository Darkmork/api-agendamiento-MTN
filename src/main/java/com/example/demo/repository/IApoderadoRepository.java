package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Apoderado; // Importar la entidad Apoderado

import java.util.Optional;

/**
 * Interfaz de repositorio para la entidad Apoderado.
 */
@Repository
public interface IApoderadoRepository extends JpaRepository<Apoderado, Long> { // Extender JpaRepository con Apoderado
    Optional<Apoderado> findByid(Long id); // Método para buscar por ID

    Optional<Apoderado> findByNome(String Nome); // Método para buscar por nombre

    Optional<Apoderado> findByCpf(String Cpf); // Método para buscar por CPF

    Optional<Apoderado> findByEmail(String Email); // Método para buscar por email

    Optional<Apoderado> findByTelefone(String Telefone); // Método para buscar por teléfono

}