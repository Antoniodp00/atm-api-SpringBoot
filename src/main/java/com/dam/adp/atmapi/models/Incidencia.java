package com.dam.adp.atmapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "incidencia", schema = "sistema_cajeros")
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id")
    private Cajero atm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @NotNull
    @Column(name = "fecha_apertura", nullable = false)
    private Instant fechaApertura;

    @Column(name = "fecha_cierre")
    private Instant fechaCierre;

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 30)
    @Column(name = "estado", length = 30)
    private String estado;

    @Column(name = "prioridad")
    private Integer prioridad;

    @OneToMany(mappedBy = "incidencia")
    private Set<ConsumoRepuesto> consumoRepuestos = new LinkedHashSet<>();

}