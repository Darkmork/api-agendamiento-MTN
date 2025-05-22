package com.example.demo.Entities;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Define la entidad DisponibilidadProfesor, que representa la tabla DisponibilidadProfesor en la base de datos.
// Almacena los horarios de disponibilidad de los profesores.
@Data
@Entity
@Table(name = "DisponibilidadProfesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadProfesor {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; // Identificador único del registro de disponibilidad

   @Column(name = "profesor_id", nullable = false)
   private Long profesorId; // ID del profesor asociado a esta disponibilidad

   @Column(nullable = false)
   private String diaDaSemana; // Día de la semana para la disponibilidad (e.g., LUNES, MARTES)

   @Column(nullable = false)
   private LocalTime horarioInicio; // Hora de inicio de la disponibilidad

   @Column(nullable = false)
   private LocalTime horarioFim; // Hora de fin de la disponibilidad

}
