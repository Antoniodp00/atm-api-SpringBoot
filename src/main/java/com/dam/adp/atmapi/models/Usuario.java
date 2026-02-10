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
@Table(name = "usuario", schema = "sistema_cajeros")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 150)
    @Column(name = "nombre_completo", length = 150)
    private String nombreCompleto;

    @Size(max = 20)
    @NotNull
    @Column(name = "rol", nullable = false, length = 20)
    private String rol;

    @ColumnDefault("1")
    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "usuario")
    private Set<AsignacionTecnico> asignacionTecnicos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tecnico")
    private Set<Incidencia> incidencias = new LinkedHashSet<>();

}