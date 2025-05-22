package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la validación de los atributos de la entidad Materia
@Data
@NoArgsConstructor
public class MateriaDTO { // Class name changed
    private Long id;

    @NotBlank(message = "El nombre es obligatorio") // Validation message translated
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") // Validation message translated
    private String nome;
}
