package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApoderadoDTO; // Actualizado a ApoderadoDTO
import com.example.demo.service.ApoderadoService; // Actualizado a ApoderadoService
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Apoderado", description = "Endpoints para gestión de Apoderados") // Tag actualizado
@RestController
@RequestMapping("api/apoderados") // RequestMapping actualizado
public class ApoderadoController { // Nombre de clase actualizado

    @Autowired
    private ApoderadoService apoderadoService; // Servicio actualizado

    @Operation(summary = "Lista todos los apoderados", description = "Retorna una lista con todos los apoderados registrados")
    @GetMapping
    public ResponseEntity<List<ApoderadoDTO>> listarApoderados() { // Nombre de método y DTO actualizados
        List<ApoderadoDTO> apoderados = apoderadoService.listarTodos(); // Llamada al servicio actualizada
        return ResponseEntity.ok(apoderados);
    }

    @Operation(summary = "Busca un apoderado por ID", description = "Retorna los detalles de un apoderado específico")
    @GetMapping("/{id}")
    public ResponseEntity<ApoderadoDTO> buscarPorId(@PathVariable Long id) { // DTO actualizado
        Optional<ApoderadoDTO> apoderadoDTO = apoderadoService.buscarPorId(id); // Llamada al servicio actualizada
        return apoderadoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crea un nuevo Apoderado", description = "Registra un nuevo apoderado en el sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<ApoderadoDTO>> crearApoderado(@Valid @RequestBody ApoderadoDTO apoderadoDTO) { // Nombre de método y DTO actualizados
        try {
            // Intenta guardar el apoderado
            ApoderadoDTO savedApoderado = apoderadoService.salvar(apoderadoDTO); // Llamada al servicio actualizada

            // Retorna éxito con el ApoderadoDTO guardado
            ApiResponse<ApoderadoDTO> response = new ApiResponse<>(savedApoderado); // DTO actualizado
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Crea un error con el mensaje específico
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<ApoderadoDTO> response = new ApiResponse<>(errorResponse); // DTO actualizado
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Crea un error genérico
            ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", e.getMessage()); // Mensaje de error principal en español
            ApiResponse<ApoderadoDTO> response = new ApiResponse<>(errorResponse); // DTO actualizado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Elimina un apoderado", description = "Elimina un apoderado del sistema por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarApoderado(@PathVariable Long id) { // Nombre de método actualizado
        apoderadoService.deletar(id); // Llamada al servicio actualizada
        return ResponseEntity.noContent().build();
    }
}
