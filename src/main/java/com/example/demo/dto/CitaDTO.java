package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase DTO para la validación de los atributos de la Cita
@Data
@NoArgsConstructor
public class CitaDTO {

    private Long id; // Identificador único de la cita

    @NotNull(message = "El ID del apoderado es obligatorio")
    private Long apoderadoId; // ID del apoderado asociado a la cita

    private String nombreApoderado; // Nombre del apoderado (opcional, para visualización)

    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId; // ID del profesor asociado a la cita

    private String nombreProfesor; // Nombre del profesor (opcional, para visualización)

    @NotNull(message = "La fecha y hora de la cita son obligatorias")
    @Future(message = "La fecha y hora de la cita deben ser futuras")
    private LocalDateTime dataHora; // Fecha y hora de la cita

    private String status; // Estado de la cita (e.g., programada, cancelada, completada)

    private String observacoes; // Observaciones adicionales sobre la cita
}
