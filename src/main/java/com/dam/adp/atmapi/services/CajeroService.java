package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.repositories.CajeroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CajeroService {
    private final CajeroRepository cajeroRepository;

    private static final String PATRON_ID_CAJERO = "^ATM-[A-Z]{3}-\\d{3}$";

    public CajeroService(CajeroRepository cajeroRepository) {
        this.cajeroRepository = cajeroRepository;
    }

    public List<Cajero>obtenerTodos(){
        return cajeroRepository.findAll();
    }

    public List<Cajero> obtenerTodosActivos(){
        return cajeroRepository.findByActivoTrue();
    }

    public List<Cajero> obtenerTodosInactivos(){
        return cajeroRepository.findByActivoFalse();
    }

    public Cajero obtenerDetalle(String id){
        return cajeroRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Cajero no encontrado",id));

    }

    public Cajero crearCajero(Cajero cajero){

        if (!cajero.getId().matches(PATRON_ID_CAJERO)){
            throw new IllegalArgumentException("El ID debe tener el formato ATM-XXX-000 (Ej: ATM-COR-001)");
        }


        if (cajeroRepository.existsById(cajero.getId())){
            throw new IllegalArgumentException("El cajero ya existe con el ID: " + cajero.getId());

        }
        cajero.setActivo(true);

        return cajeroRepository.save(cajero);
    }

    public Cajero desactivarCajero(String id){
        Cajero cajero = obtenerDetalle(id);
        cajero.setActivo(false);
        return cajeroRepository.save(cajero);
    }



}
