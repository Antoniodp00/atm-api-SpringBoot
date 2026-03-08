package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.models.EventoLog;
import com.dam.adp.atmapi.models.enums.Nivel;
import com.dam.adp.atmapi.repositories.CajeroRepository;
import com.dam.adp.atmapi.repositories.EventoLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio para el registro y gestión de logs de eventos.
 * Permite almacenar información sobre errores y actividades del sistema.
 */
@Service
public class EventoLogService {
    private final EventoLogRepository eventoLogRepository;
    private final CajeroService cajeroService;

    public EventoLogService(EventoLogRepository eventoLogRepository, CajeroService cajeroService) {
        this.eventoLogRepository = eventoLogRepository;
        this.cajeroService = cajeroService;
    }

    /**
     * Registra un nuevo evento en el log del sistema.
     * @param cajeroId Identificador del cajero asociado (opcional).
     * @param nivel Nivel de severidad del evento.
     * @param mensaje Descripción técnica del evento.
     * @param codigo Código de error asociado.
     */
    public void registrarEvento(String cajeroId, Nivel nivel, String mensaje, String codigo ){
        EventoLog log = new EventoLog();

        if (cajeroId != null && !cajeroId.trim().isEmpty() ){
            try {
                log.setAtm(cajeroService.obtenerDetalle(cajeroId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        log.setNivel(nivel != null ? nivel : Nivel.INFO);
        log.setMensajeTecnico(mensaje);
        log.setCodigoError(codigo != null ? codigo : "GENERICO");
        log.setFechaHora(LocalDateTime.now());

        eventoLogRepository.save(log);


    }
}
