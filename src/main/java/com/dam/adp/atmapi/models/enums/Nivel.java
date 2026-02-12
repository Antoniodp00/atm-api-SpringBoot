package com.dam.adp.atmapi.models.enums;

public enum Nivel {
    INFO,       // Operación normal (ej: "Transacción realizada")
    WARNING,    // Algo raro pero no rompe nada (ej: "Stock bajo")
    ERROR,      // Fallo de operación (ej: "Tarjeta rechazada")
    CRITICAL    // Fallo del sistema (ej: "Cajero sin conexión a BBDD")
}