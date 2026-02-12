package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import com.dam.adp.atmapi.services.RepuestoService;
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
class RepuestoServiceTest {

    @Mock
    private RepuestoRepository repuestoRepository;

    @InjectMocks
    private RepuestoService repuestoService;

    // --- TEST: CREAR REPUESTO ---

    @Test
    void crearRepuesto_conDatosValidos_deberiaGuardar() {
        // 1. GIVEN
        Repuesto nuevo = new Repuesto();
        nuevo.setNombre("Pantalla LCD");
        nuevo.setStockActual(10);
        // Simulamos que NO existe otro con ese nombre
        when(repuestoRepository.existsByNombre("Pantalla LCD")).thenReturn(false);
        // Simulamos el guardado
        when(repuestoRepository.save(any(Repuesto.class))).thenReturn(nuevo);

        // 2. WHEN
        Repuesto resultado = repuestoService.crearRepuesto(nuevo);

        // 3. THEN
        assertNotNull(resultado);
        verify(repuestoRepository).save(nuevo);
    }

    @Test
    void crearRepuesto_conNombreDuplicado_deberiaLanzarExcepcion() {
        // 1. GIVEN
        Repuesto duplicado = new Repuesto();
        duplicado.setNombre("Pantalla LCD");

        // Simulamos que YA existe
        when(repuestoRepository.existsByNombre("Pantalla LCD")).thenReturn(true);

        // 2. WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> {
            repuestoService.crearRepuesto(duplicado);
        });

        // ¡Vital! Verificamos que NUNCA se llamó a guardar
        verify(repuestoRepository, never()).save(any());
    }

    @Test
    void crearRepuesto_conStockNegativo_deberiaLanzarExcepcion() {
        // 1. GIVEN
        Repuesto invalido = new Repuesto();
        invalido.setNombre("Teclado");
        invalido.setStockActual(-5); // Stock negativo

        // 2. WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> {
            repuestoService.crearRepuesto(invalido);
        });

        verify(repuestoRepository, never()).save(any());
    }

    // --- TEST: ACTUALIZAR STOCK ---

    @Test
    void actualizarStock_siExiste_deberiaModificarYGuardar() {
        // 1. GIVEN
        Long id = 1L;
        Repuesto existente = new Repuesto();
        existente.setId(id);
        existente.setStockActual(10);

        when(repuestoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(repuestoRepository.save(existente)).thenReturn(existente);

        // 2. WHEN
        Repuesto resultado = repuestoService.actualizarStock(id, 50);

        // 3. THEN
        assertEquals(50, resultado.getStockActual()); // Verificamos que cambió
        verify(repuestoRepository).save(existente);
    }

    @Test
    void actualizarStock_siNoExiste_deberiaLanzarRecordNotFound() {
        // 1. GIVEN
        Long id = 99L;
        when(repuestoRepository.findById(id)).thenReturn(Optional.empty());

        // 2. WHEN & THEN
        assertThrows(RecordNotFoundException.class, () -> {
            repuestoService.actualizarStock(id, 20);
        });

        verify(repuestoRepository, never()).save(any());
    }
}