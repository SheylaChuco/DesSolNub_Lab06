package com.techcorp.securedocs.service;

import com.techcorp.securedocs.dto.LoginRequestDTO;
import com.techcorp.securedocs.dto.LoginResponseDTO;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.repository.UsuarioRepository;
import com.techcorp.securedocs.security.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new RuntimeException("Usuario inactivo o suspendido");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol().getNombre());
        claims.put("nombre", usuario.getNombre());

        String token = jwtUtil.generarToken(usuario.getCorreo(), claims);

        Long departamentoId = usuario.getDepartamento() != null ? usuario.getDepartamento().getId() : null;
        String departamentoNombre = usuario.getDepartamento() != null ? usuario.getDepartamento().getNombre() : null;

        return new LoginResponseDTO(token, usuario.getNombre(), usuario.getRol().getNombre(), departamentoId, departamentoNombre);
    }
}