package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 * Permite realizar operaciones CRUD y consultas personalizadas sobre usuarios.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUsername(String username);

    List<Usuario> findByActivoTrueAndRol(Rol rol);

    Optional<Usuario> findByUsername(String username);


    @Query("SELECT u FROM Usuario u JOIN u.incidencias i " +
            "WHERE u.rol = com.dam.adp.atmapi.models.enums.Rol.TECNICO " +
            "AND i.estado != com.dam.adp.atmapi.models.enums.EstadoIncidencia.RESUELTA " +
            "GROUP BY u.id " +
            "HAVING COUNT(i) > :cantidad")
    List<Usuario> findTecnicosSobrecargados(@Param("cantidad") Long cantidad);
}
