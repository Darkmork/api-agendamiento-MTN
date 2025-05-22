package com.example.demo.dto;

//import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Added import for @NotNull
//import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la validación de los atributos de la entidad Profesor
@Data
@NoArgsConstructor
public class ProfesorDTO { // Class name changed

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nome;

    /*
     * @NotNull(message = "A data de nascimento é obrigatória")
     * 
     * @Size(min = 10, max = 10, message =
     * "A data de nascimento deve ter o formato dd/MM/yyyy")
     * 
     * @Past(message = "A data de nascimento deve ser uma data passada") private
     * LocalDate dataNascimento;
     */

    @NotBlank(message = "La identificación del profesor es obligatoria") // Validation message translated
    @Size(min = 5, max = 20, message = "La identificación del profesor debe tener entre 5 y 20 caracteres") // Size constraint updated
    private String identificacionProfesor; // Field name changed

    @NotNull(message = "El ID de la materia es obligatorio") // Validation changed to @NotNull and message translated
    private Long materiaId; // Field name changed and type changed to Long
}
