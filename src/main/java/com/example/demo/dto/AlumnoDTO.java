package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la validación de los atributos de la entidad Alumno
@Data
@NoArgsConstructor
public class AlumnoDTO { // Class name changed

    private Long id;
    private boolean ativo = true;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre; // Field name corrected to 'nombre'

    @NotBlank(message = "El número de estudiante es obligatorio") // Validation message translated
    @Size(min = 5, max = 20, message = "El número de estudiante debe tener entre 5 y 20 caracteres") // Constraints updated
    private String numeroEstudiante; // Field name changed

    @Email(message = "E-mail inválido")
    @NotBlank(message = "El e-mail es obligatorio") // Validation message translated
    private String email;

    @NotBlank(message = "El teléfono es obligatorio") // Validation message translated
    @Size(min = 8, max = 15, message = "El teléfono debe tener entre 8 y 15 caracteres") // Adjusted min size for phone
    private String telefone;
}