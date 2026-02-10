package com.dam.adp.atmapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cajero", schema = "sistema_cajeros")
public class Cajero {
    @Id
    @Size(max = 20)
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Size(max = 100)
    @NotNull
    @Column(name = "ubicacion", nullable = false, length = 100)
    private String ubicacion;

    @Size(max = 50)
    @NotNull
    @Column(name = "ciudad", nullable = false, length = 50)
    private String ciudad;

    @Size(max = 50)
    @Column(name = "modelo", length = 50)
    private String modelo;

    @ColumnDefault("1")
    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "cajero")
    private Set<AsignacionTecnico> asignacionTecnicos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "atm")
    private Set<EstadisticaDiaria> estadisticaDiarias = new LinkedHashSet<>();

    @OneToMany(mappedBy = "atm")
    private Set<EventoLog> eventoLogs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "atm")
    private Set<Incidencia> incidencias = new LinkedHashSet<>();

    @OneToMany(mappedBy = "atm")
    private Set<Transaccion> transaccions = new LinkedHashSet<>();

}