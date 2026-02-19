package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Transaccion;
import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import com.dam.adp.atmapi.repositories.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final CajeroService cajeroService;


    public TransaccionService(TransaccionRepository transaccionRepository, CajeroService cajeroService) {
        this.transaccionRepository = transaccionRepository;
        this.cajeroService = cajeroService;
    }

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

    public List<Transaccion> obtenerHistorialCajero(String idCajero) {
        if (idCajero == null) {
            throw new IllegalArgumentException("El ID del cajero no puede estar vacío");
        }
        if (cajeroService.obtenerDetalle(idCajero) == null) {
            throw new IllegalArgumentException("El cajero no existe");
        }
        return transaccionRepository.findByAtmIdOrderByFechaHoraDesc(idCajero);
    }

    public long contarOperaciones(String cajeroId){
        cajeroService.obtenerDetalle(cajeroId);
        return  transaccionRepository.countByAtmId(cajeroId);
    }

    public double sumarIngresos(String cajeroId){
        return transaccionRepository.sumarMontoPorCajeroYTipo(cajeroId, TipoTransaccion.INGRESO);
    }

    public double sumarRetiros(String cajeroId){
        return transaccionRepository.sumarMontoPorCajeroYTipo(cajeroId, TipoTransaccion.RETIRO);
    }

    public double sumarTransferencias(String cajeroId){
        return transaccionRepository.sumarMontoPorCajeroYTipo(cajeroId, TipoTransaccion.TRANSFERENCIA);
    }
}
