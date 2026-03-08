package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Transaccion;
import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import com.dam.adp.atmapi.repositories.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * Servicio para la gestión de transacciones financieras.
 * Permite registrar operaciones, consultar historiales y calcular totales.
 */
@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final CajeroService cajeroService;


    public TransaccionService(TransaccionRepository transaccionRepository, CajeroService cajeroService) {
        this.transaccionRepository = transaccionRepository;
        this.cajeroService = cajeroService;
    }

    /**
     * Registra una nueva transacción en un cajero.
     * Valida que el cajero esté activo y genera un hash único para la transacción.
     * @param idCajero Identificador del cajero.
     * @param tipoTransaccion Tipo de operación (retiro, depósito, etc.).
     * @param cantidad Monto de la transacción.
     * @return La transacción registrada.
     */
    public Transaccion registrarTransaccion(String idCajero, TipoTransaccion tipoTransaccion, Double cantidad) {
        Cajero cajero = cajeroService.obtenerDetalle(idCajero);

        if (!cajero.getActivo()){
            throw new IllegalArgumentException("No se pueden realizar transacciones en un cajero inactivo");
        }
        Transaccion transaccion = new Transaccion();
        transaccion.setAtm(cajero);
        transaccion.setTipo(tipoTransaccion);
        transaccion.setMonto(cantidad);
        transaccion.setFechaHora(LocalDateTime.now());
        transaccion.setTarjetaHash(randomUUID().toString());
        transaccion.setResultado("EXITOSA");

        return transaccionRepository.save(transaccion);
    }

    /**
     * Obtiene el historial de transacciones de un cajero.
     * @param idCajero Identificador del cajero.
     * @return Lista de transacciones ordenadas por fecha.
     */
    public List<Transaccion> obtenerHistorialCajero(String idCajero) {
        if (idCajero == null) {
            throw new IllegalArgumentException("El ID del cajero no puede estar vacío");
        }
        if (cajeroService.obtenerDetalle(idCajero) == null) {
            throw new IllegalArgumentException("El cajero no existe");
        }
        return transaccionRepository.findByAtmIdOrderByFechaHoraDesc(idCajero);
    }

    /**
     * Cuenta el número total de operaciones realizadas en un cajero.
     * @param cajeroId Identificador del cajero.
     * @return Cantidad total de transacciones.
     */
    public long contarOperaciones(String cajeroId){
        cajeroService.obtenerDetalle(cajeroId);
        return  transaccionRepository.countByAtmId(cajeroId);
    }

    /**
     * Calcula la suma total de ingresos en un cajero.
     * @param cajeroId Identificador del cajero.
     * @return Monto total de ingresos.
     */
    public double sumarIngresos(String cajeroId){
        return transaccionRepository.sumarMontoPorCajeroYTipo(cajeroId, TipoTransaccion.INGRESO);
    }

    /**
     * Calcula la suma total de retiros en un cajero.
     * @param cajeroId Identificador del cajero.
     * @return Monto total de retiros.
     */
    public double sumarRetiros(String cajeroId){
        return transaccionRepository.sumarMontoPorCajeroYTipo(cajeroId, TipoTransaccion.RETIRO);
    }

    /**
     * Calcula la suma total de transferencias en un cajero.
     * @param cajeroId Identificador del cajero.
     * @return Monto total de transferencias.
     */
    public double sumarTransferencias(String cajeroId){
        return transaccionRepository.sumarMontoPorCajeroYTipo(cajeroId, TipoTransaccion.TRANSFERENCIA);
    }
}
