package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.models.Usuario;
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

        if (usuarioRepository.existsByUsername(usuario.getUsername())){
            throw new IllegalArgumentException("El usuario ya existe");

        }

        String passwordHasheada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordHasheada);

        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }


}
