package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuarios", description = "Endpoints para la gestión de Usuarios") // @Tag updated
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista con todos los usuarios registrados") // @Operation updated
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar un usuario por ID", description = "Retorna los detalles de un usuario específico") // @Operation updated
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id) { // Method name updated for clarity
        Optional<UsuarioDTO> usuarioDTO = usuarioService.buscarPorId(id);
        return usuarioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema") // @Operation updated
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Intenta guardar el usuario
            UsuarioDTO savedUsuario = usuarioService.salvar(usuarioDTO);

            // Retorna éxito con el UsuarioDTO guardado
            ApiResponse<UsuarioDTO> response = new ApiResponse<>(savedUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Crea un error con el mensaje específico
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage()); // Error message translated
            ApiResponse<UsuarioDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Crea un error genérico
            ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", e.getMessage()); // Error message translated
            ApiResponse<UsuarioDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema por su ID") // @Operation updated
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) { // Method name updated for clarity
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
