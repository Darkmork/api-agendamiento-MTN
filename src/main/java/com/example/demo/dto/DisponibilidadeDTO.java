package com.example.demo.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Future; // Manteniendo por si se usa en otro contexto, aunque parece incorrecto para diaDaSemana
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Añadido para el ID del profesor
import jakarta.validation.constraints.Pattern; // Para validar el formato de hora si es necesario
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la validación y transferencia de datos de Disponibilidad
@Data
@NoArgsConstructor
public class DisponibilidadeDTO {

    private Long id; // Identificador único de la disponibilidad

    @NotNull(message = "El ID del profesor es obligatorio.") // Validación añadida para profesorId
    private Long profesorId; // ID del profesor asociado a esta disponibilidad

    @NotBlank(message = "Es obligatorio informar el día de la semana.")
    // @Size(min = 5, max = 10, message = "El día de la semana debe tener entre 5 y 10 caracteres, ej: LUNES, MARTES") // Ajustar según el formato esperado
    // La anotación @Future no es apropiada para un campo como 'diaDaSemana' (String). Se mantiene si la lógica original dependía de ello de alguna forma inesperada.
    // Si 'diaDaSemana' fuera una fecha completa, @Future sería relevante.
    private String diaDaSemana; // Día de la semana (e.g., LUNES, MARTES)

    @NotNull(message = "Es obligatorio informar el horario de inicio.")
    // @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "El formato de hora debe ser HH:mm") // Validación de formato de hora
    private LocalTime horarioInicio; // Hora de inicio de la disponibilidad

    @NotNull(message = "Es obligatorio informar el horario de fin.")
    // @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "El formato de hora debe ser HH:mm") // Validación de formato de hora
    private LocalTime horarioFim; // Hora de fin de la disponibilidad

}
