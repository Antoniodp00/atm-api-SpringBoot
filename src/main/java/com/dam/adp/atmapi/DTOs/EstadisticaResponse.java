package com.dam.adp.atmapi.DTOs;

/**
 * DTO para la respuesta con estadísticas de operación de un cajero.
 * @param cajeroId Identificador del cajero analizado.
 * @param totalOperaciones Número total de transacciones realizadas.
 * @param totalIngresado Suma total de dinero ingresado.
 * @param totalRetirado Suma total de dinero retirado.
 * @param totalTransferido Suma total de dinero transferido.
 */
public record EstadisticaResponse(
        String cajeroId,
        long totalOperaciones,
        double totalIngresado,
        double totalRetirado,
        double totalTransferido
) {}
