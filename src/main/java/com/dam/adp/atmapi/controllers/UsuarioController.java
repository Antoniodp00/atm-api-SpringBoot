package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioById (@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioByNombreUsuario (@PathVariable String username) {
        Usuario usuario = usuarioService.obtenerPorNombreUsuario(username);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/tecnicos")
    public ResponseEntity<List<Usuario>> obtenerTecnicosActivos() {
        return ResponseEntity.ok(usuarioService.obtenerTecnicosActivos());
        }

    @GetMapping("/admins")
    public ResponseEntity<List<Usuario>> obtenerAdminsActivos() {
        return ResponseEntity.ok(usuarioService.obtenerAdminsActivos());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario>desactivarUsuario(@PathVariable Long id){
        Usuario usuarioDesactivado = usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok(usuarioDesactivado);
    }


}

