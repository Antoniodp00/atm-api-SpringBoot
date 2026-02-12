package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.ConsumoRepuesto;
import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.models.enums.EstadoIncidencia;
import com.dam.adp.atmapi.repositories.CajeroRepository;
import com.dam.adp.atmapi.repositories.ConsumoRepuestoRepository;
import com.dam.adp.atmapi.repositories.IncidenciaRepository;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import com.dam.adp.atmapi.services.CajeroService;
import com.dam.adp.atmapi.services.IncidenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

    @Mock
    private RepuestoRepository repuestoRepository;
    @Mock
    private IncidenciaRepository incidenciaRepository;
    @Mock
    private ConsumoRepuestoRepository consumoRepuestoRepository;

    @Mock
    private CajeroService cajeroService;


    @InjectMocks
    private IncidenciaService incidenciaService;

    @Test
    void crearIncidencia_deberiaAsignarCajeroYFecha() {
        // 1. GIVEN
        String idCajero = "ATM-BCN-001";
        Cajero cajeroMock = new Cajero();
        cajeroMock.setId(idCajero);
        cajeroMock.setActivo(true);


        Incidencia incidenciaNueva = new Incidencia();
        incidenciaNueva.setDescripcion("Pantalla rota");

        when(cajeroService.obtenerDetalle(idCajero)).thenReturn(cajeroMock);
        // Simulamos el guardado
        when(incidenciaRepository.save(any(Incidencia.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. WHEN
        // Nota: Tendrás que crear este método en el servicio
        Incidencia resultado = incidenciaService.crearIncidencia(idCajero, "Pantalla rota", 5);

        // 3. THEN
        assertNotNull(resultado.getFechaApertura()); // La fecha se pone sola
        assertEquals(EstadoIncidencia.ABIERTA, resultado.getEstado()); // Estado inicial
        assertEquals(cajeroMock, resultado.getAtm()); // Relación establecida
    }

    @Test
    void procesarConsumo_deberiaBajarStockYGuardarConsumo() {
        // GIVEN
        Long idIncidencia = 100L;
        Long idRepuesto = 1L;
        Integer cantidadAUsar = 3;

        // Mock de la incidencia
        Incidencia incidenciaMock = new Incidencia();
        incidenciaMock.setId(idIncidencia);
        when(incidenciaRepository.findById(idIncidencia)).thenReturn(Optional.of(incidenciaMock));

        // Mock del repuesto (Stock inicial 10)
        Repuesto repuestoMock = new Repuesto();
        repuestoMock.setId(idRepuesto);
        repuestoMock.setStockActual(10);
        when(repuestoRepository.findById(idRepuesto)).thenReturn(Optional.of(repuestoMock));

        // WHEN
        incidenciaService.procesarConsumo(idIncidencia, idRepuesto, cantidadAUsar);

        // THEN
        // 1. Verificamos que el stock bajó a 7
        assertEquals(7, repuestoMock.getStockActual());

        // 2. Verificamos que se guardó el cambio de stock
        verify(repuestoRepository).save(repuestoMock);

        // 3. Verificamos que se guardó la relación en la tabla intermedia
        verify(consumoRepuestoRepository).save(any(ConsumoRepuesto.class));
    }

    @Test
    void procesarConsumo_siIncidenciaEstaResuelta_deberiaLanzarExcepcion() {
        // 1. GIVEN
        Long idIncidencia = 50L;
        Incidencia incidenciaCerrada = new Incidencia();
        incidenciaCerrada.setId(idIncidencia);
        // Usamos tu nuevo Enum
        incidenciaCerrada.setEstado(EstadoIncidencia.RESUELTA);

        when(incidenciaRepository.findById(idIncidencia)).thenReturn(Optional.of(incidenciaCerrada));

        // 2. WHEN & THEN
        // Esperamos una excepcion porque está cerrada
        assertThrows(IllegalStateException.class, () -> {
            incidenciaService.procesarConsumo(idIncidencia, 1L, 1);
        });

        // Aseguramos que NO se llamó a guardar nada
        verify(consumoRepuestoRepository, never()).save(any());
        verify(repuestoRepository, never()).save(any());
    }

    @Test
    void buscarPorCiudad_deberiaLlamarAlRepositorio() {
        // 1. GIVEN
        String ciudad = "Sevilla";
        // Simulamos que el repo devuelve una lista vacía (nos da igual el contenido, queremos ver si llama)
        when(incidenciaRepository.findByCiudad(ciudad)).thenReturn(List.of());

        // 2. WHEN
        // Este método aún no existe en tu servicio
        List<Incidencia> resultado = incidenciaService.obtenerIncidenciasPorCiudad(ciudad);

        // 3. THEN
        // Verificamos que el servicio llamó a la query personalizada del repositorio
        verify(incidenciaRepository).findByCiudad(ciudad);
    }
}