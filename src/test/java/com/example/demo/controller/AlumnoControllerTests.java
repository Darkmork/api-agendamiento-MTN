package com.example.demo.controller;

import com.example.demo.dto.AlumnoDTO;
import com.example.demo.service.AlumnoService;
import com.example.demo.service.Utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never; 
import static org.mockito.Mockito.verify; // Added static import for verify()
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Pruebas unitarias para AlumnoController
@WebMvcTest(AlumnoController.class)
class AlumnoControllerTests {

    @Autowired
    private MockMvc mockMvc; // Para simular peticiones HTTP

    @MockBean
    private AlumnoService alumnoService; // Mock del servicio de alumnos

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON y viceversa

    private AlumnoDTO alumnoDTO1;
    private AlumnoDTO alumnoDTO2;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada prueba
        alumnoDTO1 = new AlumnoDTO();
        alumnoDTO1.setId(1L);
        alumnoDTO1.setNombre("Ana Torres"); // Ensuring correct setter for 'nombre'
        alumnoDTO1.setNumeroEstudiante("B001");
        alumnoDTO1.setEmail("ana.torres@example.com");
        alumnoDTO1.setTelefone("987654321");
        alumnoDTO1.setAtivo(true);

        alumnoDTO2 = new AlumnoDTO();
        alumnoDTO2.setId(2L);
        alumnoDTO2.setNombre("Luis Mora"); // Ensuring correct setter for 'nombre'
        alumnoDTO2.setNumeroEstudiante("B002");
        // ... inicializar otros campos para alumnoDTO2
    }

    // Prueba para el endpoint de listar todos los alumnos
    @Test
    void debeListarTodosLosAlumnosEndpoint() throws Exception {
        List<AlumnoDTO> listaAlumnosDTO = Arrays.asList(alumnoDTO1, alumnoDTO2);
        when(alumnoService.listarTodos()).thenReturn(listaAlumnosDTO); // Simula el servicio

        mockMvc.perform(get("/api/alumnos"))
                .andExpect(status().isOk()) // Verifica que el estado HTTP sea 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(listaAlumnosDTO.size())) // Verifica el tamaño de la lista
                .andExpect(jsonPath("$[0].nombre").value(alumnoDTO1.getNombre())) // Ensuring correct getter for 'nombre'
                .andExpect(jsonPath("$[1].nombre").value(alumnoDTO2.getNombre())); // Ensuring correct getter for 'nombre'
    }

    // Prueba para el endpoint de crear un nuevo alumno
    @Test
    void debeCrearNuevoAlumnoEndpoint() throws Exception {
        // Preparamos el DTO que se enviará en el cuerpo de la solicitud
        AlumnoDTO nuevoAlumnoDTO = new AlumnoDTO();
        nuevoAlumnoDTO.setNombre("Carlos Solis"); // Ensuring correct setter for 'nombre'
        nuevoAlumnoDTO.setNumeroEstudiante("B003");
        nuevoAlumnoDTO.setEmail("carlos.solis@example.com");
        nuevoAlumnoDTO.setTelefone("112233445");
        nuevoAlumnoDTO.setAtivo(true);
    
        // Preparamos el DTO que esperamos que el servicio devuelva (con ID)
        AlumnoDTO alumnoGuardadoDTO = new AlumnoDTO();
        alumnoGuardadoDTO.setId(3L); // ID asignado por el sistema
        alumnoGuardadoDTO.setNombre(nuevoAlumnoDTO.getNombre()); // Ensuring correct getter for 'nombre'
        alumnoGuardadoDTO.setNumeroEstudiante(nuevoAlumnoDTO.getNumeroEstudiante());
        alumnoGuardadoDTO.setEmail(nuevoAlumnoDTO.getEmail());
        alumnoGuardadoDTO.setTelefone(nuevoAlumnoDTO.getTelefone());
        alumnoGuardadoDTO.setAtivo(nuevoAlumnoDTO.isAtivo());

        when(alumnoService.salvar(any(AlumnoDTO.class))).thenReturn(alumnoGuardadoDTO); // Simula el servicio

        mockMvc.perform(post("/api/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoAlumnoDTO))) // Envía el DTO como JSON
                .andExpect(status().isCreated()) // Verifica que el estado HTTP sea 201 Created
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(alumnoGuardadoDTO.getId()))
                .andExpect(jsonPath("$.data.nombre").value(alumnoGuardadoDTO.getNombre())) // Ensuring correct getter for 'nombre'
                .andExpect(jsonPath("$.data.numeroEstudiante").value(alumnoGuardadoDTO.getNumeroEstudiante()));
    }
    
    // Prueba para el endpoint de buscar un alumno por ID existente
    @Test
    void debeBuscarAlumnoPorIdEndpointExistente() throws Exception {
        Long idExistente = 1L;
        when(alumnoService.buscarPorId(idExistente)).thenReturn(Optional.of(alumnoDTO1)); // Simula el servicio

        mockMvc.perform(get("/api/alumnos/{id}", idExistente))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(alumnoDTO1.getId()))
                .andExpect(jsonPath("$.nombre").value(alumnoDTO1.getNombre())); // Ensuring correct getter for 'nombre'
    }

    // Prueba para el endpoint de buscar un alumno por ID no existente
    @Test
    void debeRetornarNotFoundParaIdEndpointNoExistente() throws Exception {
        Long idNoExistente = 99L;
        when(alumnoService.buscarPorId(idNoExistente)).thenReturn(Optional.empty()); // Simula el servicio

        mockMvc.perform(get("/api/alumnos/{id}", idNoExistente))
                .andExpect(status().isNotFound()); // Verifica que el estado HTTP sea 404 Not Found
    }

    // Prueba para el endpoint de eliminar un alumno
    @Test
    void debeEliminarAlumnoEndpoint() throws Exception {
        Long idAEliminar = 1L;
        // Mockito.doNothing() es útil para métodos void, pero para deleteById
        // en el controlador, el servicio es el que se encarga.
        // No necesitamos simular nada específico para deleteById si el servicio lo maneja
        // y el controlador simplemente llama al servicio y devuelve noContent.
        // La verificación de la llamada al servicio se haría en AlumnoServiceTests.

        mockMvc.perform(delete("/api/alumnos/{id}", idAEliminar))
                .andExpect(status().isNoContent()); // Verifica que el estado HTTP sea 204 No Content
    }
    
    // Prueba para el endpoint de crear un nuevo alumno con datos inválidos (ej. nombre vacío)
    @Test
    void debeRetornarBadRequestAlCrearAlumnoConNombreVacio() throws Exception {
        AlumnoDTO alumnoInvalidoDTO = new AlumnoDTO();
        alumnoInvalidoDTO.setNombre(""); // Ensuring correct setter for 'nombre'
        // numeroEstudiante, email, and telefone are also @NotBlank, let's set them to ensure only 'nombre' is the issue.
        alumnoInvalidoDTO.setNumeroEstudiante("B004"); 
        alumnoInvalidoDTO.setEmail("test@example.com");
        alumnoInvalidoDTO.setTelefone("1234567");

        // No necesitamos mockear el servicio aquí porque la validación de @Valid
        // debe ocurrir antes de que se llame al método del servicio.
        // Spring maneja la validación y devuelve 400 Bad Request automáticamente.
        // Sin embargo, para asegurar que el servicio NO es llamado, podemos verificarlo.

        mockMvc.perform(post("/api/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumnoInvalidoDTO)))
                .andExpect(status().isBadRequest()); // Esperamos un 400 Bad Request
        
        // Verificar que el método salvar del servicio NUNCA fue llamado.
        verify(alumnoService, never()).salvar(any(AlumnoDTO.class));
    }
}
