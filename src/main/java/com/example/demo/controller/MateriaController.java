package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MateriaDTO;
import com.example.demo.service.MateriaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Materias", description = "Endpoints para la gestión de Materias")
@RestController
@RequestMapping("api/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @Operation(summary = "Listar todas las materias", description = "Retorna una lista con todas las materias registradas")
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> listarMaterias() {
        List<MateriaDTO> materias = materiaService.listarTodos();
        return ResponseEntity.ok(materias);
    }

    @Operation(summary = "Buscar una materia por ID", description = "Retorna los detalles de una materia específica")
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> buscarMateriaPorId(@PathVariable Long id) {
        Optional<MateriaDTO> materiaDTO = materiaService.buscarPorId(id);
        return materiaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva Materia", description = "Registra una nueva materia en el sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<MateriaDTO>> crearMateria(@Valid @RequestBody MateriaDTO materiaDTO) {
        try {
            MateriaDTO savedMateria = materiaService.salvar(materiaDTO);
            ApiResponse<MateriaDTO> response = new ApiResponse<>(savedMateria);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<MateriaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", e.getMessage());
            ApiResponse<MateriaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar una materia", description = "Elimina una materia del sistema por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
