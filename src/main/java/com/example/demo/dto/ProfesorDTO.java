package com.example.demo.dto;

//import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase para la validación de los atributos de la clase Profesor

@Data
@NoArgsConstructor
public class ProfesorDTO {

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

    @NotBlank(message = "El identificador del profesor es obligatorio")
    @Size(min = 5, max = 10, message = "El identificador del profesor debe tener entre 5 y 10 caracteres")
    private String identificadorProfesor;

    @NotBlank(message = "La materia es obligatoria")
    @Size(min = 3, max = 50, message = "La materia debe tener entre 3 y 50 caracteres")
    private String materiaId;
}
