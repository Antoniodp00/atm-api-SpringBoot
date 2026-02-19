package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import com.dam.adp.atmapi.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe", id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario obtenerPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe",id));

    }

    public Usuario obtenerPorNombreUsuario(String username){
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe",username));
    }

    public List<Usuario> obtenerTecnicosActivos(){
        return usuarioRepository.findByActivoTrueAndRol(Rol.TECNICO);
    }

    public List<Usuario> obtenerAdminsActivos(){
        return usuarioRepository.findByActivoTrueAndRol(Rol.ADMIN);
    }


}
