package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import com.dam.adp.atmapi.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {

       validarDisponibilidadNombreUsuario(usuario.getUsername());
       prepararNuevoUsuario(usuario);

       return usuarioRepository.save(usuario);
    }

    private void validarDisponibilidadNombreUsuario(String nombreUsuario) {
        if (usuarioRepository.existsByUsername(nombreUsuario)) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso" + nombreUsuario);
        }
    }

    private void prepararNuevoUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        if (usuario.getRol() == null){
            usuario.setRol(Rol.TECNICO);
        }
    }
    public Usuario obtenerPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe",id));

    }

}
