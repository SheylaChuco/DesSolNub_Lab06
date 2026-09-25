package com.techcorp.securedocs.service;

import com.techcorp.securedocs.dto.UsuarioDTO;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.repository.DepartamentoRepository;
import com.techcorp.securedocs.repository.RolRepository;
import com.techcorp.securedocs.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final DepartamentoRepository departamentoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                          DepartamentoRepository departamentoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.departamentoRepository = departamentoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario crear(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        aplicarDatosComunes(usuario, dto);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        usuario.setNombre(dto.getNombre());
        // el correo y password no se tocan aquí; si se quiere cambiar password, se haría en un endpoint aparte
        aplicarDatosComunes(usuario, dto);
        return usuarioRepository.save(usuario);
    }

    private void aplicarDatosComunes(Usuario usuario, UsuarioDTO dto) {
        usuario.setRol(rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no existe")));

        if (dto.getDepartamentoId() != null) {
            usuario.setDepartamento(departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento no existe")));
        } else {
            usuario.setDepartamento(null);
        }

        usuario.setNivelSeguridad(dto.getNivelSeguridad());
        usuario.setPais(dto.getPais());
        usuario.setTipoContrato(dto.getTipoContrato());
        usuario.setEstado(dto.getEstado());
    }
}