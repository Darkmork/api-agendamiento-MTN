package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

// Validación de los atributos de la clase ApoderadoDTO
@Data
@NoArgsConstructor
public class ApoderadoDTO {

    private Long id;
    private boolean activo = true; // Manteniendo el campo activo como en Apoderado.java

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nome;

    @NotBlank(message = "El CPF es obligatorio") // CPF del apoderado
    @Size(min = 11, max = 11, message = "El CPF debe tener 11 caracteres")
    private String cpf;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "El e-mail es obligatorio")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 10, max = 15, message = "El teléfono debe tener entre 10 y 15 caracteres")
    private String telefone;
}