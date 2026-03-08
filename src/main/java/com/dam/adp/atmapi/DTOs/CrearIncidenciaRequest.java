package com.dam.adp.atmapi.DTOs;

/**
 * DTO para la solicitud de creación de una nueva incidencia.
 * @param cajeroId Identificador del cajero que presenta el problema.
 * @param descripcion Detalle del problema reportado.
 * @param prioridad Nivel de urgencia de la incidencia.
 */
public record CrearIncidenciaRequest(
        String cajeroId,
        String descripcion,
        Integer prioridad
){}
