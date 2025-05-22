package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ProfesorDTO;
import com.example.demo.service.ProfesorService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Profesores", description = "Endpoints para la gestión de Profesores")
@RestController
@RequestMapping("api/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @Operation(summary = "Listar todos los profesores", description = "Retorna una lista con todos los profesores registrados")
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> listarProfesores() {
        List<ProfesorDTO> profesores = profesorService.listarTodos();
        return ResponseEntity.ok(profesores);
    }

    @Operation(summary = "Buscar un profesor por ID", description = "Retorna los detalles de un profesor específico")
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> buscarProfesorPorId(@PathVariable Long id) {
        Optional<ProfesorDTO> profesorDTO = profesorService.buscarPorId(id);
        return profesorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo Profesor", description = "Registra un nuevo profesor en el sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<ProfesorDTO>> crearProfesor(@Valid @RequestBody ProfesorDTO profesorDTO) {
        try {
            ProfesorDTO savedProfesor = profesorService.salvar(profesorDTO);
            ApiResponse<ProfesorDTO> response = new ApiResponse<>(savedProfesor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<ProfesorDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", e.getMessage());
            ApiResponse<ProfesorDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar un profesor", description = "Elimina un profesor del sistema por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        profesorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
