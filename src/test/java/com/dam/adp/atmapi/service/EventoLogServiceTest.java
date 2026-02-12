package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.models.enums.Nivel;
import com.dam.adp.atmapi.repositories.EventoLogRepository;
import com.dam.adp.atmapi.services.CajeroService;
import com.dam.adp.atmapi.services.EventoLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventoLogServiceTest {

    @Mock
    private EventoLogRepository eventoLogRepository;
    @Mock private CajeroService cajeroService;
    @InjectMocks
    private EventoLogService eventoLogService;

    @Test
    void registrarEvento_deberiaGuardarConFecha() {
        // GIVEN
        String mensaje = "Error de red";
        Nivel nivel = Nivel.ERROR;

        // WHEN
        eventoLogService.registrarEvento(null, nivel, mensaje, "NET-001");

        // THEN
        // Verificamos que se guardó un objeto EventoLog y que los datos coinciden
        verify(eventoLogRepository).save(argThat(log ->
                log.getMensajeTecnico().equals(mensaje) &&
                        log.getNivel() == nivel &&
                        log.getFechaHora() != null
        ));
    }
}