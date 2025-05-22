package com.example.demo.Entities;

//import java.time.LocalDate;

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

// Este fragmento de código es la entidad Profesor, que representa la tabla profesor en la base de datos
// Posee los atributos id, nombre, identificadorProfesor y materiaId, que se mapean a las columnas de la tabla

@Data
@Entity
@Table(name = "profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    /*
     * @Column(nullable = false) private LocalDate dataNascimento;
     */

    @Column(nullable = false, unique = true)
    private String identificadorProfesor;

    @Column(name = "materia_id", nullable = false)
    private Long materiaId;
}
