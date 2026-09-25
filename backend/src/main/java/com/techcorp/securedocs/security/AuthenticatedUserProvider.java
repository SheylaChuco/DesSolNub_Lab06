package com.techcorp.securedocs.security;

import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    private final UsuarioRepository usuarioRepository;

    public AuthenticatedUserProvider(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obtenerUsuarioActual() {
        String correo = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
