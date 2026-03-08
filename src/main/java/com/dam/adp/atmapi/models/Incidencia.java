package com.dam.adp.atmapi.models;

import com.dam.adp.atmapi.models.enums.EstadoIncidencia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entidad que representa una incidencia reportada en un cajero.
 * Contiene información sobre el estado, prioridad y técnico asignado.
 */
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
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 30)
    private EstadoIncidencia estado;

    @Min(1)
    @Max(10)
    @Column(name = "prioridad")
    private Integer prioridad;

    @JsonIgnore
    @OneToMany(mappedBy = "incidencia")
    private Set<ConsumoRepuesto> consumoRepuestos = new LinkedHashSet<>();

}
