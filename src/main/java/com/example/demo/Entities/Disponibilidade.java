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

// Entidad que representa la disponibilidad horaria de un profesor
@Data
@Entity
@Table(name = "Disponibilidade") // Corregido el nombre de la tabla
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disponibilidade {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; // Identificador único de la disponibilidad

   @Column(name = "profesor_id", nullable = false) // Columna renombrada para reflejar la asociación con Profesor
   private Long profesorId; // ID del profesor asociado a esta disponibilidad

   @Column(nullable = false)
   private String diaDaSemana; // Día de la semana (e.g., LUNES, MARTES)

   @Column(nullable = false)
   private LocalTime horarioInicio; // Hora de inicio de la disponibilidad

   @Column(nullable = false)
   private LocalTime horarioFim; // Hora de fin de la disponibilidad

}
