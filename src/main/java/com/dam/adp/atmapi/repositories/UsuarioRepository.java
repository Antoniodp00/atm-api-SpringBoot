package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUsername(String username);

    List<Usuario> findByActivoTrueAndRol(Rol rol);


    Optional<Usuario> findByUsername(String username);
}
