package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.models.ConsumoRepuesto;
import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.repositories.ConsumoRepuestoRepository;
import com.dam.adp.atmapi.repositories.IncidenciaRepository;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import com.dam.adp.atmapi.services.IncidenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @InjectMocks
    private IncidenciaService incidenciaService;

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
}