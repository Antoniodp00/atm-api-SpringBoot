package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para registrar, consultar y desactivar usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id Identificador del usuario.
     * @return Respuesta con el usuario encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioById (@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return Respuesta con el usuario encontrado.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioByNombreUsuario (@PathVariable String username) {
        Usuario usuario = usuarioService.obtenerPorNombreUsuario(username);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene la lista de técnicos activos.
     * @return Respuesta con la lista de técnicos.
     */
    @GetMapping("/tecnicos")
    public ResponseEntity<List<Usuario>> obtenerTecnicosActivos() {
        return ResponseEntity.ok(usuarioService.obtenerTecnicosActivos());
        }

    /**
     * Obtiene la lista de administradores activos.
     * @return Respuesta con la lista de administradores.
     */
    @GetMapping("/admins")
    public ResponseEntity<List<Usuario>> obtenerAdminsActivos() {
        return ResponseEntity.ok(usuarioService.obtenerAdminsActivos());
    }

    /**
     * Obtiene la lista de técnicos con sobrecarga de trabajo.
     * @return Respuesta con la lista de técnicos sobrecargados.
     */
    @GetMapping("/tecnicos/sobrecargados")
    public ResponseEntity<List<Usuario>> obtenerTecnicosSobrecargados() {
        return ResponseEntity.ok(usuarioService.obtenerTecnicosSobrecargados(2L));
    }
    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario Datos del nuevo usuario.
     * @return Respuesta con el usuario registrado.
     */
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    /**
     * Desactiva un usuario existente.
     * @param id Identificador del usuario a desactivar.
     * @return Respuesta con el usuario desactivado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario>desactivarUsuario(@PathVariable Long id){
        Usuario usuarioDesactivado = usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok(usuarioDesactivado);
    }


}
