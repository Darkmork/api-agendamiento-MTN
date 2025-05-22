package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AlumnoDTO; // Updated import
import com.example.demo.service.AlumnoService; // Updated import
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Alumnos", description = "Endpoints para la gestión de Alumnos") // @Tag updated
@RestController
@RequestMapping("api/alumnos") // @RequestMapping updated
public class AlumnoController { // Class name updated

    @Autowired
    private AlumnoService alumnoService; // Service updated

    @Operation(summary = "Listar todos los alumnos", description = "Retorna una lista con todos los alumnos registrados") // @Operation updated
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> listarAlumnos() { // Method name and return type updated
        List<AlumnoDTO> alumnos = alumnoService.listarTodos(); // Variable name and service call updated
        return ResponseEntity.ok(alumnos);
    }

    @Operation(summary = "Buscar un alumno por ID", description = "Retorna los detalles de un alumno específico") // @Operation updated
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> buscarAlumnoPorId(@PathVariable Long id) { // Method name and return type updated
        Optional<AlumnoDTO> alumnoDTO = alumnoService.buscarPorId(id); // Variable name and service call updated
        return alumnoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo Alumno", description = "Registra un nuevo alumno en el sistema") // @Operation updated
    @PostMapping
    public ResponseEntity<ApiResponse<AlumnoDTO>> crearAlumno(@Valid @RequestBody AlumnoDTO alumnoDTO) { // Method name and parameter type updated
        try {
            // Intenta guardar el alumno
            AlumnoDTO savedAlumno = alumnoService.salvar(alumnoDTO); // Variable name and service call updated

            // Retorna éxito con el AlumnoDTO guardado
            ApiResponse<AlumnoDTO> response = new ApiResponse<>(savedAlumno); // Type updated
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Crea un error con el mensaje específico
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage()); // Error message translated
            ApiResponse<AlumnoDTO> response = new ApiResponse<>(errorResponse); // Type updated
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Crea un error genérico
            ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", e.getMessage()); // Error message translated
            ApiResponse<AlumnoDTO> response = new ApiResponse<>(errorResponse); // Type updated
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar un alumno", description = "Elimina un alumno del sistema por su ID") // @Operation updated
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) { // Method name updated
        alumnoService.deletar(id); // Service call updated
        return ResponseEntity.noContent().build();
    }
}
