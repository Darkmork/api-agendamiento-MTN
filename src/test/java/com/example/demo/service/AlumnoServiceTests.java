package com.example.demo.service;

import com.example.demo.Entities.Alumno;
import com.example.demo.dto.AlumnoDTO;
import com.example.demo.mapper.AlumnoMapper;
import com.example.demo.repository.IAlumnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Pruebas unitarias para AlumnoService
@ExtendWith(MockitoExtension.class)
class AlumnoServiceTests {

    @Mock
    private IAlumnoRepository alumnoRepository; // Mock del repositorio de alumnos

    @Mock
    private AlumnoMapper alumnoMapper; // Mock del mapper de alumnos

    @InjectMocks
    private AlumnoService alumnoService; // Instancia del servicio a probar con mocks inyectados

    private Alumno alumno1;
    private AlumnoDTO alumnoDTO1;
    private Alumno alumno2;
    private AlumnoDTO alumnoDTO2;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada prueba
        alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNombre("Juan Perez"); // Ensuring correct setter for 'nombre'
        alumno1.setNumeroEstudiante("A001");
        alumno1.setEmail("juan.perez@example.com");
        alumno1.setTelefone("123456789");
        alumno1.setAtivo(true);

        alumnoDTO1 = new AlumnoDTO();
        alumnoDTO1.setId(1L);
        alumnoDTO1.setNombre("Juan Perez"); // Ensuring correct setter for 'nombre'
        alumnoDTO1.setNumeroEstudiante("A001");
        alumnoDTO1.setEmail("juan.perez@example.com");
        alumnoDTO1.setTelefone("123456789");
        alumnoDTO1.setAtivo(true);

        alumno2 = new Alumno();
        alumno2.setId(2L);
        // ... inicializar otros campos para alumno2
        alumnoDTO2 = new AlumnoDTO();
        alumnoDTO2.setId(2L);
        // ... inicializar otros campos para alumnoDTO2
    }

    // Prueba para verificar la obtención de todos los alumnos
    @Test
    void debeListarTodosLosAlumnos() {
        List<Alumno> alumnos = Arrays.asList(alumno1, alumno2);
        List<AlumnoDTO> alumnosDTO = Arrays.asList(alumnoDTO1, alumnoDTO2);

        when(alumnoRepository.findAll()).thenReturn(alumnos); // Simula findAll del repositorio
        when(alumnoMapper.toDTOList(alumnos)).thenReturn(alumnosDTO); // Simula la conversión a DTO

        List<AlumnoDTO> resultado = alumnoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(alumnosDTO, resultado);
        verify(alumnoRepository).findAll(); // Verifica que findAll fue llamado
        verify(alumnoMapper).toDTOList(alumnos); // Verifica que toDTOList fue llamado
    }

    // Prueba para buscar un alumno por un ID existente
    @Test
    void debeBuscarAlumnoPorIdExistente() {
        Long idExistente = 1L;
        when(alumnoRepository.findById(idExistente)).thenReturn(Optional.of(alumno1)); // Simula encontrar el alumno
        when(alumnoMapper.toDTO(alumno1)).thenReturn(alumnoDTO1); // Simula la conversión a DTO

        Optional<AlumnoDTO> resultado = alumnoService.buscarPorId(idExistente);

        assertTrue(resultado.isPresent());
        assertEquals(alumnoDTO1, resultado.get());
        verify(alumnoRepository).findById(idExistente);
        verify(alumnoMapper).toDTO(alumno1);
    }

    // Prueba para buscar un alumno por un ID que no existe
    @Test
    void debeDevolverOptionalVacioParaIdNoExistente() {
        Long idNoExistente = 3L;
        when(alumnoRepository.findById(idNoExistente)).thenReturn(Optional.empty()); // Simula no encontrar el alumno

        Optional<AlumnoDTO> resultado = alumnoService.buscarPorId(idNoExistente);

        assertFalse(resultado.isPresent());
        verify(alumnoRepository).findById(idNoExistente);
        verify(alumnoMapper, never()).toDTO(any(Alumno.class)); // Verifica que toDTO no fue llamado
    }

    // Prueba para guardar un nuevo alumno
    @Test
    void debeSalvarUnNuevoAlumno() {
        AlumnoDTO alumnoDTONuevo = new AlumnoDTO(); // DTO sin ID, ya que es nuevo
        alumnoDTONuevo.setNombre("Carlos Gomez"); // Ensuring correct setter for 'nombre'
        alumnoDTONuevo.setNumeroEstudiante("A003");
        // ... otros campos

        Alumno alumnoNuevoEntidad = new Alumno(); // Entidad correspondiente al DTO nuevo
        // ... mapear campos de alumnoDTONuevo a alumnoNuevoEntidad

        Alumno alumnoGuardadoEntidad = new Alumno(); // Entidad como sería devuelta por save()
        alumnoGuardadoEntidad.setId(3L); // ID asignado después de guardar
        // ... otros campos mapeados desde alumnoNuevoEntidad

        AlumnoDTO alumnoGuardadoDTO = new AlumnoDTO(); // DTO correspondiente a la entidad guardada
        alumnoGuardadoDTO.setId(3L);
        // ... otros campos mapeados

        when(alumnoMapper.toEntity(alumnoDTONuevo)).thenReturn(alumnoNuevoEntidad); // DTO -> Entidad (antes de guardar)
        when(alumnoRepository.save(alumnoNuevoEntidad)).thenReturn(alumnoGuardadoEntidad); // Guardar entidad
        when(alumnoMapper.toDTO(alumnoGuardadoEntidad)).thenReturn(alumnoGuardadoDTO); // Entidad -> DTO (después de guardar)

        AlumnoDTO resultado = alumnoService.salvar(alumnoDTONuevo);

        assertNotNull(resultado);
        assertEquals(alumnoGuardadoDTO.getId(), resultado.getId());
        // Aquí podrías añadir más aserciones para otros campos si es necesario
        verify(alumnoMapper).toEntity(alumnoDTONuevo);
        verify(alumnoRepository).save(alumnoNuevoEntidad);
        verify(alumnoMapper).toDTO(alumnoGuardadoEntidad);
    }

    // Prueba para eliminar un alumno por ID
    @Test
    void debeEliminarAlumnoPorId() {
        Long idAEliminar = 1L;
        // No es necesario simular deleteById, ya que es void.
        // Solo necesitamos verificar que se llamó.
        // Opcionalmente, podrías simular findById antes para asegurarte de que existe,
        // pero para la lógica de deleteById, la verificación de la llamada es lo principal.

        alumnoService.deletar(idAEliminar);

        verify(alumnoRepository, times(1)).deleteById(idAEliminar); // Verifica que deleteById fue llamado una vez con el ID correcto
    }
}
