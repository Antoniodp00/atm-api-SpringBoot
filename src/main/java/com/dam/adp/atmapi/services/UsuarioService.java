package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import com.dam.adp.atmapi.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de usuarios del sistema.
 * Maneja el registro, autenticación y consulta de usuarios y técnicos.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida la disponibilidad del nombre de usuario y encripta la contraseña.
     * @param usuario Datos del usuario a registrar.
     * @return El usuario registrado.
     */
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

    /**
     * Desactiva un usuario existente.
     * @param id Identificador del usuario a desactivar.
     * @return El usuario actualizado con estado inactivo.
     */
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe", id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        return usuario;
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id Identificador del usuario.
     * @return El usuario encontrado.
     */
    public Usuario obtenerPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe",id));

    }

    /**
     * Obtiene un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return El usuario encontrado.
     */
    public Usuario obtenerPorNombreUsuario(String username){
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("El usuario no existe",username));
    }

    /**
     * Obtiene la lista de técnicos activos.
     * @return Lista de usuarios con rol TECNICO y estado activo.
     */
    public List<Usuario> obtenerTecnicosActivos(){
        return usuarioRepository.findByActivoTrueAndRol(Rol.TECNICO);
    }

    /**
     * Obtiene la lista de administradores activos.
     * @return Lista de usuarios con rol ADMIN y estado activo.
     */
    public List<Usuario> obtenerAdminsActivos(){
        return usuarioRepository.findByActivoTrueAndRol(Rol.ADMIN);
    }

    /**
     * Identifica técnicos con una carga de trabajo elevada.
     * @param cantidadIncidencias Umbral de incidencias para considerar sobrecarga.
     * @return Lista de técnicos que superan el umbral de incidencias asignadas.
     */
    public List<Usuario> obtenerTecnicosSobrecargados(Long cantidadIncidencias){
        return usuarioRepository.findTecnicosSobrecargados(cantidadIncidencias);
    }

}
