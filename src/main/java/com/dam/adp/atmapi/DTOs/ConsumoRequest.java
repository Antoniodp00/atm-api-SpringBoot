package com.dam.adp.atmapi.DTOs;

/**
 * DTO para la solicitud de consumo de repuestos en una incidencia.
 * @param repuestoId Identificador del repuesto a consumir.
 * @param cantidad Cantidad de unidades a descontar del inventario.
 */
public record ConsumoRequest(
        Long repuestoId,
        Integer cantidad
) {}
