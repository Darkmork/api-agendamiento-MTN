package com.example.demo.Entities;

import java.time.LocalDate;

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

@Data
@Entity
@Table(name = "usuarios") // Nombre de la tabla para usuarios
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del usuario

    @Column(nullable = false)
    private String nombre; // Nombre completo del usuario

    @Column(nullable = false, unique = true)
    private String cpf; // Documento de identidad personal (CPF)

    @Column(nullable = false, unique = true)
    private String email; // Dirección de correo electrónico del usuario

    @Column(nullable = false)
    private String contrasena; // Contraseña para el acceso al sistema

    @Column(nullable = false)
    private LocalDate fechaNacimiento; // Fecha de nacimiento del usuario

    @Column(nullable = false)
    private String telefone; // Número de teléfono del usuario

    public Usuario(String nombre, String cpf, String email, String contrasena, LocalDate fechaNacimiento, String telefone) {
        this.nombre = nombre;
        this.cpf = cpf;
        this.email = email;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
        this.telefone = telefone;
    }
}
