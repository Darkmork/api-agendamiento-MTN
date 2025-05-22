package com.example.demo.Entities;

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

// Define la entidad Materia, que representa la tabla Materia en la base de datos.
// Contiene los atributos Id y nome.
@Data
@Entity
@Table(name = "Materia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; // Identificador único de la materia

    @Column(nullable = false, unique = true)
    private String nome; // Nombre de la materia
}
