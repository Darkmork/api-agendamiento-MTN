package com.example.demo.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Added for LocalTime and Long
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la gestión de la disponibilidad de profesores
@Data
@NoArgsConstructor
public class DisponibilidadProfesorDTO { // Class name changed

    private Long id;

    @NotNull(message = "El ID del profesor es obligatorio") // Changed to @NotNull and translated
    private Long profesorId; // Field name changed

    @NotBlank(message = "El día de la semana es obligatorio") // Validation updated and translated
    private String diaDaSemana;

    @NotNull(message = "La hora de inicio es obligatoria") // Changed to @NotNull and translated
    private LocalTime horarioInicio;

    @NotNull(message = "La hora de fin es obligatoria") // Changed to @NotNull and translated
    private LocalTime horarioFim;

}
