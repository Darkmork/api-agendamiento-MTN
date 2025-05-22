package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// DTO para la transferencia de datos de Usuario.
// Contiene validaciones para los campos.
@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre; // Nombre del usuario

    @NotBlank(message = "El CPF es obligatorio")
    @Size(min = 11, max = 11, message = "El CPF debe tener 11 caracteres")
    private String cpf; // CPF del usuario

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email; // Email del usuario

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena; // Contraseña del usuario

    private LocalDate fechaNacimiento; // Fecha de nacimiento del usuario

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefone; // Teléfono del usuario
}
