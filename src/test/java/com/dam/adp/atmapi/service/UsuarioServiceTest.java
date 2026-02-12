package com.dam.adp.atmapi.service;

import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Rol;
import com.dam.adp.atmapi.repositories.UsuarioRepository;
import com.dam.adp.atmapi.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService; // Dará error porque no existe aún

    @Test
    void registrarUsuario_deberiaHashearPasswordYGuardar() {
        // GIVEN
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setUsername("tecnico1");
        usuarioNuevo.setPassword("1234"); // Contraseña plana
        usuarioNuevo.setRol(Rol.TECNICO);

        // Simulamos que el encoder devuelve un hash falso
        when(passwordEncoder.encode("1234")).thenReturn("$2a$10$FakeHashString...");

        // Simulamos el guardado
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioNuevo);

        // WHEN
        usuarioService.registrarUsuario(usuarioNuevo);

        // THEN
        // Usamos ArgumentCaptor para "robar" el objeto que se le pasó al repositorio
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioGuardado = usuarioCaptor.getValue();

        // Verificamos que NO sea "1234"
        assertNotEquals("1234", usuarioGuardado.getPassword());
        // Verificamos que SEA el hash
        assertEquals("$2a$10$FakeHashString...", usuarioGuardado.getPassword());
    }
}