package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DisponibilidadProfesorDTO; // Updated import
import com.example.demo.service.DisponibilidadProfesorService; // Updated import
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "DisponibilidadProfesores", description = "Endpoints para la gestión de la disponibilidad de profesores") // @Tag updated
@RestController
@RequestMapping("/api/profesores/{profesorId}/disponibilidades") // @RequestMapping updated
public class DisponibilidadProfesorController { // Class name updated

    @Autowired
    private DisponibilidadProfesorService disponibilidadProfesorService; // Service updated

    @Operation(summary = "Registrar disponibilidad de profesor", description = "Crea un nuevo registro de disponibilidad para un profesor específico.")
    @PostMapping
    public ResponseEntity<ApiResponse<DisponibilidadProfesorDTO>> registrarDisponibilidad(
            @PathVariable Long profesorId,
            @RequestBody @Valid DisponibilidadProfesorDTO disponibilidadDTO) {
        try {
            // Asegurando que el profesorId del path se usa para la lógica del DTO
            // y que el DTO es el único argumento para el servicio.
            disponibilidadDTO.setProfesorId(profesorId); 
            DisponibilidadProfesorDTO disponibilidadSalva = disponibilidadProfesorService.registrarDisponibilidad(disponibilidadDTO);
            ApiResponse<DisponibilidadProfesorDTO> response = new ApiResponse<>(disponibilidadSalva);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Error del usuario", e.getMessage());
            ApiResponse<DisponibilidadProfesorDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Fallo del sistema", e.getMessage());
            ApiResponse<DisponibilidadProfesorDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar disponibilidades de profesor", description = "Lista todos los registros de disponibilidad para un profesor específico.") // @Operation updated
    @GetMapping
    public ResponseEntity<ApiResponse<List<DisponibilidadProfesorDTO>>> listarDisponibilidades(
            @PathVariable Long profesorId) { // Path variable and DTO type updated
        try {
            List<DisponibilidadProfesorDTO> disponibilidades = disponibilidadProfesorService.listarDisponibilidadPorProfesor(profesorId); // Service call updated
            ApiResponse<List<DisponibilidadProfesorDTO>> response = new ApiResponse<>(disponibilidades);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Error del usuario", e.getMessage()); // Error message translated
            ApiResponse<List<DisponibilidadProfesorDTO>> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Fallo del sistema", e.getMessage()); // Error message translated
            ApiResponse<List<DisponibilidadProfesorDTO>> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar disponibilidad de profesor", description = "Elimina un registro de disponibilidad específico por su ID.") // @Operation updated
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removerDisponibilidade(@PathVariable Long id) {
        try {
            disponibilidadProfesorService.removerDisponibilidade(id);
            ApiResponse<String> response = new ApiResponse<>("Disponibilidad eliminada!"); // Success message translated
            return ResponseEntity.ok().body(response); // Changed to ResponseEntity.ok() for successful deletion message

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Error del usuario", e.getMessage()); // Error message translated
            ApiResponse<String> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Fallo del sistema", e.getMessage()); // Error message translated
            ApiResponse<String> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}