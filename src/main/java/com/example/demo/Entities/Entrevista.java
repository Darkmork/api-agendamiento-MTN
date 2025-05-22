package com.example.demo.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Define la entidad Entrevista, que representa la tabla Entrevistas en la base de datos.
// Almacena información sobre las entrevistas programadas entre alumnos y profesores.
@Data
@Entity
@Table(name = "Entrevistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entrevista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la entrevista

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno; // Alumno participante en la entrevista

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor; // Profesor participante en la entrevista

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora; // Fecha y hora de la entrevista

    @Column(nullable = false)
    private String status; // Estado actual de la entrevista (e.g., programada, completada, cancelada)

    @Column(columnDefinition = "TEXT")
    private String observacoes; // Observaciones o notas adicionales sobre la entrevista
}
