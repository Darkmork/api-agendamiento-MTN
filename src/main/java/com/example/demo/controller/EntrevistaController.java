package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EntrevistaDTO; // Updated import
import com.example.demo.service.EntrevistaService; // Updated import
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Entrevistas", description = "Endpoints para la gestión de Entrevistas") // @Tag updated
@RestController
@RequestMapping("/api/entrevistas") // @RequestMapping updated
public class EntrevistaController { // Class name updated

    @Autowired
    private EntrevistaService entrevistaService; // Service updated

    @Operation(summary = "Programar entrevista", description = "Programa una nueva entrevista entre un alumno y un profesor.") // @Operation updated
    @PostMapping
    public ResponseEntity<ApiResponse<EntrevistaDTO>> programarEntrevista(@Valid @RequestBody EntrevistaDTO entrevistaDTO) { // Method and DTO updated
        try {
            EntrevistaDTO entrevistaSalva = entrevistaService.programar(entrevistaDTO); // Service call and DTO updated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(entrevistaSalva);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Error message translated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // CONFLICT for business rule violations
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Error message translated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar entrevistas", description = "Lista todas las entrevistas con filtros opcionales.") // @Operation updated
    @GetMapping
    public ResponseEntity<ApiResponse<List<EntrevistaDTO>>> listarEntrevistas( // Method name and DTO updated
            @RequestParam(required = false) Long profesorId, // Parameter name updated
            @RequestParam(required = false) Long alumnoId,   // Parameter name updated
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        try {
            List<EntrevistaDTO> entrevistas = entrevistaService.listar(profesorId, alumnoId, status, dataInicio, dataFim); // Service call and parameters updated
            ApiResponse<List<EntrevistaDTO>> response = new ApiResponse<>(entrevistas);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Error message translated
            ApiResponse<List<EntrevistaDTO>> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Error message translated
            ApiResponse<List<EntrevistaDTO>> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Buscar entrevista por ID", description = "Retorna los detalles de una entrevista específica.") // @Operation updated
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EntrevistaDTO>> buscarEntrevistaPorId(@PathVariable Long id) { // Method name and DTO updated
        Optional<EntrevistaDTO> entrevistaOpt = entrevistaService.buscarPorId(id); // Service call and DTO updated

        if (entrevistaOpt.isPresent()) {
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(entrevistaOpt.get());
            return ResponseEntity.ok(response);
        } else {
            ErrorResponse error = new ErrorResponse("No encontrada", "Entrevista no encontrada"); // Error message translated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Actualizar estado de la entrevista", description = "Actualiza el estado de una entrevista específica (ej. programada, completada, cancelada).") // @Operation updated
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<EntrevistaDTO>> actualizarEstadoEntrevista(@PathVariable Long id, // Method name updated
            @RequestParam String status) {

        try {
            EntrevistaDTO entrevistaActualizada = entrevistaService.actualizarStatus(id, status); // Service call and DTO updated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(entrevistaActualizada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Error message translated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(error);

            HttpStatus httpStatus = e.getMessage().toLowerCase().contains("no encontrada") ? HttpStatus.NOT_FOUND // Logic to check message content
                    : HttpStatus.BAD_REQUEST;

            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Error message translated
            ApiResponse<EntrevistaDTO> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Cancelar entrevista", description = "Cancela una entrevista (permitido hasta 24 horas antes).") // @Operation updated
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelarEntrevista(@PathVariable Long id) { // Method name updated
        try {
            entrevistaService.cancelar(id); // Service call updated
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Error message translated
            ApiResponse<Void> response = new ApiResponse<>(error);

            HttpStatus httpStatus;
            String lowerCaseMessage = e.getMessage().toLowerCase();
            if (lowerCaseMessage.contains("no encontrada")) {
                httpStatus = HttpStatus.NOT_FOUND;
            } else if (lowerCaseMessage.contains("24 horas") || lowerCaseMessage.contains("anticipación")) { // Check for cancellation rule violation
                httpStatus = HttpStatus.CONFLICT; 
            } else {
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Error message translated
            ApiResponse<Void> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
