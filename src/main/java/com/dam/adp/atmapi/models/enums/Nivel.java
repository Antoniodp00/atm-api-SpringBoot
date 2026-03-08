package com.dam.adp.atmapi.models.enums;

/**
 * Enumeración que define los niveles de severidad de los logs.
 * INFO: Información general.
 * WARNING: Advertencia sobre posibles problemas.
 * ERROR: Error en una operación.
 * CRITICAL: Fallo crítico del sistema.
 */
public enum Nivel {
    INFO,       // Operación normal (ej: "Transacción realizada")
    WARNING,    // Algo raro pero no rompe nada (ej: "Stock bajo")
    ERROR,      // Fallo de operación (ej: "Tarjeta rechazada")
    CRITICAL    // Fallo del sistema (ej: "Cajero sin conexión a BBDD")
}
