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

@Data
@Entity
@Table(name = "citas") // Nombre de la tabla en la base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cita { // Nombre de la clase de la entidad

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la cita

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apoderado_id", nullable = false) // Clave foránea para Apoderado
    private Apoderado apoderado; // Apoderado asociado a la cita

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = false) // Clave foránea para Profesor
    private Profesor profesor; // Profesor asociado a la cita

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora; // Fecha y hora de la cita

    @Column(nullable = false)
    private String status; // Estado de la cita (e.g., programada, cancelada, completada)

    @Column(columnDefinition = "TEXT")
    private String observacoes; // Observaciones adicionales sobre la cita
}
