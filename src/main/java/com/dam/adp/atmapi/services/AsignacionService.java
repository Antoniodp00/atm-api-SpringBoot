package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.models.AsignacionTecnico;
import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import com.dam.adp.atmapi.models.enums.Turno;
import com.dam.adp.atmapi.repositories.AsignacionTecnicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AsignacionService {
    private final AsignacionTecnicoRepository asignacionTecnicoRepository;
    private final UsuarioService usuarioService;
    private final CajeroService cajeroService;


    public AsignacionService(AsignacionTecnicoRepository asignacionTecnicoRepository, UsuarioService usuarioService, CajeroService cajeroService) {
        this.asignacionTecnicoRepository = asignacionTecnicoRepository;
        this.usuarioService = usuarioService;
        this.cajeroService = cajeroService;
    }

    public AsignacionTecnico crearAsignacion(Long usuarioId, String cajeroId, Turno turno, LocalDate fecha){
        Usuario tecnico = usuarioService.obtenerPorId(usuarioId);
        if (tecnico.getRol() != Rol.TECNICO){
            throw new IllegalArgumentException("El usuario no es un técnico");

        }

        Cajero cajero = cajeroService.obtenerDetalle(cajeroId);
        if(asignacionTecnicoRepository.existsByFechaAsignacionAndTurno(tecnico, fecha, turno)){
            throw  new IllegalArgumentException("El técnico ya tiene una asignación para ese día y turno");
        }
        AsignacionTecnico asignacion = new AsignacionTecnico();
        asignacion.setUsuario(tecnico);
        asignacion.setCajero(cajero);
        asignacion.setTurno(turno);
        asignacion.setFechaAsignacion(fecha);

        return asignacionTecnicoRepository.save(asignacion);
    }
}
