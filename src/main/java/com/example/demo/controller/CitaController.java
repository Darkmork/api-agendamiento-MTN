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

import com.example.demo.dto.CitaDTO; // Actualizado a CitaDTO
import com.example.demo.service.CitaService; // Actualizado a CitaService
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Citas", description = "Endpoints para gestión de citas (reuniones)") // Tag actualizado
@RestController
@RequestMapping("/api/citas") // RequestMapping actualizado
public class CitaController { // Nombre de clase actualizado

    @Autowired
    private CitaService citaService; // Servicio actualizado

    @Operation(summary = "Programar cita", description = "Programa una nueva cita (reunión)")
    @PostMapping
    public ResponseEntity<ApiResponse<CitaDTO>> agendar(@Valid @RequestBody CitaDTO citaDTO) { // DTO actualizado
        try {
            CitaDTO citaGuardada = citaService.agendar(citaDTO); // Llamada al servicio actualizada
            ApiResponse<CitaDTO> response = new ApiResponse<>(citaGuardada); // DTO actualizado
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Error de validación o conflicto de negocio
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<CitaDTO> response = new ApiResponse<>(error); // DTO actualizado
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            // Error interno
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<CitaDTO> response = new ApiResponse<>(error); // DTO actualizado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar citas", description = "Lista todas las citas con filtros opcionales. Permite ver citas por profesor.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaDTO>>> listar(
            @RequestParam(required = false) Long profesorId, // Parámetro actualizado
            @RequestParam(required = false) Long apoderadoId, // Parámetro actualizado
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {

        try {
            List<CitaDTO> citas = citaService.listar(profesorId, apoderadoId, status, dataInicio, dataFim); // Llamada al servicio actualizada
            ApiResponse<List<CitaDTO>> response = new ApiResponse<>(citas); // DTO actualizado
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<List<CitaDTO>> response = new ApiResponse<>(error); // DTO actualizado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<List<CitaDTO>> response = new ApiResponse<>(error); // DTO actualizado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Buscar cita por ID", description = "Retorna los detalles de una cita específica")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaDTO>> buscarPorId(@PathVariable Long id) { // DTO actualizado
        Optional<CitaDTO> citaOpt = citaService.buscarPorId(id); // Llamada al servicio actualizada

        if (citaOpt.isPresent()) {
            ApiResponse<CitaDTO> response = new ApiResponse<>(citaOpt.get()); // DTO actualizado
            return ResponseEntity.ok(response);
        } else {
            ErrorResponse error = new ErrorResponse("No encontrada", "Cita no encontrada"); // Mensajes de error en español
            ApiResponse<CitaDTO> response = new ApiResponse<>(error); // DTO actualizado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Actualizar estado de la cita", description = "Actualiza el estado de una cita específica")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CitaDTO>> atualizarStatus(@PathVariable Long id, // DTO actualizado
            @RequestParam String status) {

        try {
            CitaDTO citaActualizada = citaService.atualizarStatus(id, status); // Llamada al servicio actualizada
            ApiResponse<CitaDTO> response = new ApiResponse<>(citaActualizada); // DTO actualizado
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<CitaDTO> response = new ApiResponse<>(error); // DTO actualizado

            // Si la cita no fue encontrada, retorna 404, sino retorna 400 para estado inválido
            HttpStatus httpStatus = e.getMessage().contains("no encontrada") ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;

            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<CitaDTO> response = new ApiResponse<>(error); // DTO actualizado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Cancelar cita", description = "Cancela una cita (permitido hasta 24h antes)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelar(@PathVariable Long id) {
        try {
            citaService.cancelar(id); // Llamada al servicio actualizada
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Error de validación", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<Void> response = new ApiResponse<>(error);

            // Determinar el estado HTTP apropiado basado en el mensaje de error
            HttpStatus httpStatus;
            if (e.getMessage().contains("no encontrada")) { // "no encontrada" es parte del mensaje de CitaService
                httpStatus = HttpStatus.NOT_FOUND;
            } else if (e.getMessage().contains("24 horas") || e.getMessage().contains("antecedencia")) { // "24 horas" o "antecedencia" es parte del mensaje de CitaService
                httpStatus = HttpStatus.CONFLICT; // Regla de negocio violada
            } else {
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno del servidor", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<Void> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
