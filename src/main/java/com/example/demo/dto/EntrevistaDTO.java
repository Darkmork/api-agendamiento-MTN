package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la creación y visualización de Entrevistas
@Data
@NoArgsConstructor
public class EntrevistaDTO { // Class name changed

    private Long id;

    @NotNull(message = "El ID del alumno es obligatorio") // Validation message translated
    private Long alumnoId; // Field name changed

    private String nombreAlumno; // Field name changed

    @NotNull(message = "El ID del profesor es obligatorio") // Validation message translated
    private Long profesorId; // Field name changed

    private String nombreProfesor; // Field name changed

    @NotNull(message = "La fecha y hora de la entrevista son obligatorias") // Validation message translated
    @Future(message = "La fecha y hora de la entrevista deben ser futuras") // Validation message translated
    private LocalDateTime dataHora;

    private String status;

    private String observacoes;
}
