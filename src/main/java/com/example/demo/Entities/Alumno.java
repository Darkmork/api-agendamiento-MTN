package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Definiendo los atributos de la clase Alumno
@Data
@Entity
@Table(name = "Alumno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // Nombre del alumno - corrected field name

    @Column(name = "numero_estudiante", nullable = false, unique = true)
    private String numeroEstudiante; // Número de identificación único del estudiante

    @Column(nullable = false, unique = true)
    private String email; // Correo electrónico del alumno

    @Column(nullable = false)
    private String telefone; // Número de teléfono del alumno

    @Column(nullable = false)
    private boolean ativo = true; // Estado de actividad del alumno (activo/inactivo)

}
