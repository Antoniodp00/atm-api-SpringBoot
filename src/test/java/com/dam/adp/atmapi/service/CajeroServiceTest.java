package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.repositories.CajeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CajeroServiceTest {

    @Mock
    private CajeroRepository cajeroRepository;

    @InjectMocks
    private CajeroService cajeroService;

    // --- TEST PARA: crearCajero ---

    @Test
    void crearCajero_conDatosValidos_deberiaGuardarYRetornarCajero() {
        // 1. GIVEN (Dado un cajero con formato correcto y que NO existe en BBDD)
        Cajero cajeroNuevo = new Cajero();
        cajeroNuevo.setId("ATM-COR-001"); // Cumple el Regex
        cajeroNuevo.setModelo("NCR SelfServ");

        when(cajeroRepository.existsById("ATM-COR-001")).thenReturn(false);
        when(cajeroRepository.save(any(Cajero.class))).thenReturn(cajeroNuevo);

        // 2. WHEN (Cuando llamamos al servicio)
        Cajero resultado = cajeroService.crearCajero(cajeroNuevo);

        // 3. THEN (Entonces...)
        assertNotNull(resultado);
        assertTrue(resultado.getActivo()); // Verificamos que lo puso activo
        verify(cajeroRepository).save(cajeroNuevo); // Verificamos que llamó a guardar
    }

    @Test
    void crearCajero_conIdFormatoInvalido_deberiaLanzarExcepcion() {
        // 1. GIVEN (Cajero con ID mal formado)
        Cajero cajeroMal = new Cajero();
        cajeroMal.setId("ID-FEO-123"); // No empieza por ATM, ni cumple el patrón

        // 2. WHEN & THEN (Esperamos que explote con IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cajeroService.crearCajero(cajeroMal);
        });

        // Verificamos el mensaje de error
        assertTrue(exception.getMessage().contains("El ID debe tener el formato"));

        // ¡IMPORTANTE!: Verificamos que NUNCA se llamó al repositorio para guardar
        verify(cajeroRepository, never()).save(any());
    }

    @Test
    void crearCajero_siYaExiste_deberiaLanzarExcepcion() {
        // 1. GIVEN (Cajero válido pero que YA existe en BBDD)
        Cajero cajeroDuplicado = new Cajero();
        cajeroDuplicado.setId("ATM-COR-001");

        // Simulamos que el repositorio dice "Sí, ya existe"
        when(cajeroRepository.existsById("ATM-COR-001")).thenReturn(true);

        // 2. WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> {
            cajeroService.crearCajero(cajeroDuplicado);
        });

        verify(cajeroRepository, never()).save(any());
    }

    // --- TEST PARA: obtenerDetalle ---

    @Test
    void obtenerDetalle_siExiste_deberiaRetornarCajero() {
        // 1. GIVEN
        String id = "ATM-MAD-055";
        Cajero cajeroMock = new Cajero();
        cajeroMock.setId(id);

        when(cajeroRepository.findById(id)).thenReturn(Optional.of(cajeroMock));

        // 2. WHEN
        Cajero resultado = cajeroService.obtenerDetalle(id);

        // 3. THEN
        assertEquals(id, resultado.getId());
    }

    @Test
    void obtenerDetalle_siNoExiste_deberiaLanzarRecordNotFound() {
        // 1. GIVEN
        String id = "ATM-FANTASMA";
        when(cajeroRepository.findById(id)).thenReturn(Optional.empty());

        // 2. WHEN & THEN
        assertThrows(RecordNotFoundException.class, () -> {
            cajeroService.obtenerDetalle(id);
        });
    }

    // --- TEST PARA: desactivarCajero (Borrado Lógico) ---

    @Test
    void desactivarCajero_deberiaCambiarEstadoAFalse() {
        // 1. GIVEN
        String id = "ATM-SEV-999";
        Cajero cajeroExistente = new Cajero();
        cajeroExistente.setId(id);
        cajeroExistente.setActivo(true); // Está encendido

        // Simulamos que lo encuentra
        when(cajeroRepository.findById(id)).thenReturn(Optional.of(cajeroExistente));
        // Simulamos el guardado (devolvemos el mismo objeto)
        when(cajeroRepository.save(cajeroExistente)).thenReturn(cajeroExistente);

        // 2. WHEN
        cajeroService.desactivarCajero(id);

        // 3. THEN
        assertFalse(cajeroExistente.getActivo()); // Verificamos que ahora es false
        verify(cajeroRepository).save(cajeroExistente); // Verificamos que se guardó el cambio
    }
}