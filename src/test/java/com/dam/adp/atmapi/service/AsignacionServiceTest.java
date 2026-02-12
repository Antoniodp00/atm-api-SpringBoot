package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import com.dam.adp.atmapi.models.enums.Turno;
import com.dam.adp.atmapi.repositories.AsignacionTecnicoRepository;
import com.dam.adp.atmapi.services.AsignacionService;
import com.dam.adp.atmapi.services.CajeroService;
import com.dam.adp.atmapi.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsignacionServiceTest {

    @Mock
    private AsignacionTecnicoRepository asignacionRepository;
    @Mock private UsuarioService usuarioService;
    @Mock private CajeroService cajeroService;
    @InjectMocks
    private AsignacionService asignacionService;

    @Test
    void crearAsignacion_siYaExisteTurno_deberiaLanzarExcepcion() {
        // GIVEN
        Long tecnicoId = 1L;
        String cajeroId = "ATM-01";
        LocalDate fecha = LocalDate.now();
        Turno turno = Turno.MANANA;

        Usuario tecnico = new Usuario();
        tecnico.setRol(Rol.TECNICO);

        when(usuarioService.obtenerPorId(tecnicoId)).thenReturn(tecnico);
        when(cajeroService.obtenerDetalle(cajeroId)).thenReturn(new Cajero());
        // Simulamos que ya existe la asignación
        when(asignacionRepository.existsByFechaAsignacionAndTurno(any(), any(), any())).thenReturn(true);

        // WHEN & THEN
        assertThrows(IllegalStateException.class, () -> {
            asignacionService.crearAsignacion(tecnicoId, cajeroId, turno, fecha);
        });
    }
}