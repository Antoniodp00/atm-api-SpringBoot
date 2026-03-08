package com.dam.adp.atmapi.DTOs;

import com.dam.adp.atmapi.models.enums.Turno;

/**
 * DTO para la solicitud de asignación de un técnico a una incidencia.
 * @param id Identificador del técnico a asignar.
 * @param turno Turno de trabajo en el que se realizará la intervención.
 */
public record AsignarTecnicoRequest(
        Long id,
        Turno turno
) {}
