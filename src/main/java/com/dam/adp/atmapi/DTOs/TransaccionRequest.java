package com.dam.adp.atmapi.DTOs;

import com.dam.adp.atmapi.models.enums.TipoTransaccion;

/**
 * DTO para la solicitud de registro de una nueva transacción.
 * @param cajeroId Identificador del cajero donde se realiza la operación.
 * @param tipo Tipo de transacción (retiro, depósito, etc.).
 * @param monto Cantidad monetaria involucrada en la operación.
 */
public record TransaccionRequest(
        String cajeroId,
        TipoTransaccion tipo,
        Double monto
) {}
