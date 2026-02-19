package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Transaccion;
import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import com.dam.adp.atmapi.repositories.TransaccionRepository;
import com.dam.adp.atmapi.services.CajeroService;
import com.dam.adp.atmapi.services.TransaccionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private CajeroService cajeroService; // ¡Ojo! Mockeamos el servicio, no el repo del cajero

    @InjectMocks
    private TransaccionService transaccionService;

    @Test
    void registrarTransaccion_siCajeroActivo_deberiaGuardar() {
        // GIVEN
        String idCajero = "ATM-001";
        Cajero cajeroOk = new Cajero();
        cajeroOk.setId(idCajero);
        cajeroOk.setActivo(true);

        // Simulamos que el cajero existe y está activo
        when(cajeroService.obtenerDetalle(idCajero)).thenReturn(cajeroOk);
        when(transaccionRepository.save(any(Transaccion.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Transaccion resultado = transaccionService.registrarTransaccion(idCajero, TipoTransaccion.RETIRO, 50.0);

        // THEN
        assertNotNull(resultado);
        assertNotNull(resultado.getFechaHora()); // Se puso la fecha sola
        assertEquals(50.0, resultado.getMonto());
        verify(transaccionRepository).save(any(Transaccion.class));
    }

    @Test
    void registrarTransaccion_siCajeroInactivo_deberiaLanzarExcepcion() {
        // GIVEN
        String idCajero = "ATM-OFF";
        Cajero cajeroApagado = new Cajero();
        cajeroApagado.setId(idCajero);
        cajeroApagado.setActivo(false); // APAGADO

        when(cajeroService.obtenerDetalle(idCajero)).thenReturn(cajeroApagado);

        // WHEN & THEN
        assertThrows(IllegalStateException.class, () -> {
            transaccionService.registrarTransaccion(idCajero, TipoTransaccion.INGRESO, 100.0);
        });

        verify(transaccionRepository, never()).save(any());
    }

    @Test
    void obtenerHistorialCajero_deberiaLlamarAlRepositorio() {
        // 1. GIVEN
        String idCajero = "ATM-BCN";

        // Mockeamos el cajero para que la validación del SERVICE pase
        Cajero cajeroMock = new Cajero();
        cajeroMock.setId(idCajero);
        when(cajeroService.obtenerDetalle(idCajero)).thenReturn(cajeroMock);

        // Mockeamos el repositorio
        when(transaccionRepository.findByAtmIdOrderByFechaHoraDesc(idCajero)).thenReturn(List.of());

        // 2. WHEN
        transaccionService.obtenerHistorialCajero(idCajero);

        // 3. THEN
        verify(transaccionRepository).findByAtmIdOrderByFechaHoraDesc(idCajero);
    }
}