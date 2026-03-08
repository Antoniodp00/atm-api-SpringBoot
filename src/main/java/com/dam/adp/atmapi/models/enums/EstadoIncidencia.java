package com.dam.adp.atmapi.models.enums;

/**
 * Enumeración que define los estados posibles de una incidencia.
 * ABIERTA: Incidencia reportada pero no atendida.
 * EN_PROCESO: Técnico asignado y trabajando.
 * RESUELTA: Problema solucionado.
 * CANCELADA: Incidencia desestimada.
 */
public enum EstadoIncidencia {
    ABIERTA,
    EN_PROCESO,
    RESUELTA,
    CANCELADA
}
