package com.techcorp.securedocs.seeder;

import com.techcorp.securedocs.model.*;
import com.techcorp.securedocs.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DocumentoRepository documentoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RolRepository rolRepository, PermisoRepository permisoRepository,
                      DepartamentoRepository departamentoRepository, UsuarioRepository usuarioRepository,
                      DocumentoRepository documentoRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
        this.departamentoRepository = departamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.documentoRepository = documentoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (rolRepository.count() > 0) {
            return; // ya hay datos, no vuelve a insertar
        }

        // 1. Permisos
        Map<String, Permiso> permisos = new HashMap<>();
        for (String nombre : List.of(
                "CREAR_DOCUMENTO", "CONSULTAR_DOCUMENTO", "MODIFICAR_DOCUMENTO",
                "ELIMINAR_DOCUMENTO", "APROBAR_DOCUMENTO", "VER_AUDITORIA",
                "GESTIONAR_USUARIOS", "ASIGNAR_ROLES")) {
            Permiso p = new Permiso();
            p.setNombre(nombre);
            permisos.put(nombre, permisoRepository.save(p));
        }

        // 2. Roles + su matriz de permisos (según el documento del lab)
        Rol administrador = crearRol("ADMINISTRADOR", permisos,
                "CREAR_DOCUMENTO", "CONSULTAR_DOCUMENTO", "MODIFICAR_DOCUMENTO",
                "ELIMINAR_DOCUMENTO", "APROBAR_DOCUMENTO", "VER_AUDITORIA",
                "GESTIONAR_USUARIOS", "ASIGNAR_ROLES");

        Rol gerente = crearRol("GERENTE", permisos,
                "CREAR_DOCUMENTO", "CONSULTAR_DOCUMENTO", "MODIFICAR_DOCUMENTO",
                "ELIMINAR_DOCUMENTO", "APROBAR_DOCUMENTO", "VER_AUDITORIA");

        Rol supervisor = crearRol("SUPERVISOR", permisos,
                "CREAR_DOCUMENTO", "CONSULTAR_DOCUMENTO", "MODIFICAR_DOCUMENTO", "APROBAR_DOCUMENTO");

        Rol empleado = crearRol("EMPLEADO", permisos,
                "CREAR_DOCUMENTO", "CONSULTAR_DOCUMENTO", "MODIFICAR_DOCUMENTO");

        Rol auditor = crearRol("AUDITOR", permisos,
                "CONSULTAR_DOCUMENTO", "VER_AUDITORIA");

        Rol invitado = crearRol("INVITADO", permisos,
                "CONSULTAR_DOCUMENTO");

        // 3. Departamentos
        Departamento finanzas = crearDepartamento("FINANZAS");
        Departamento rrhh = crearDepartamento("RRHH");

        // 4. Usuarios de prueba (variados a propósito, para poder probar ABAC)
        Usuario carlos = crearUsuario("Carlos Ruiz", "carlos.ruiz@techcorp.com",
                supervisor, finanzas, 3, "PERU", "INTERNO", "ACTIVO");

        Usuario ana = crearUsuario("Ana Torres", "ana.torres@techcorp.com",
                empleado, rrhh, 2, "PERU", "INTERNO", "ACTIVO");

        Usuario luis = crearUsuario("Luis Gomez", "luis.gomez@techcorp.com",
                gerente, finanzas, 4, "PERU", "INTERNO", "ACTIVO");

        Usuario maria = crearUsuario("Maria Paz", "maria.paz@techcorp.com",
                auditor, finanzas, 5, "PERU", "INTERNO", "ACTIVO");

        Usuario rosa = crearUsuario("Rosa Diaz", "rosa.diaz@techcorp.com",
                empleado, rrhh, 2, "PERU", "INTERNO", "SUSPENDIDO");

        Usuario invitadoExterno = crearUsuario("Pedro Externo", "pedro.ext@cliente.com",
                invitado, null, 1, "PERU", "EXTERNO", "ACTIVO");

        // 5. Documentos de prueba
        crearDocumento("Presupuesto 2027", finanzas, 3, "PENDIENTE", "PERU", carlos);
        crearDocumento("Plan de RRHH confidencial", rrhh, 4, "PENDIENTE", "PERU", ana);
        crearDocumento("Comunicado público", finanzas, 1, "PUBLICADO", "PERU", luis);

        // Usuario Administrador (faltaba en el seed original)
        Usuario admin = crearUsuario("Admin General", "admin@techcorp.com",
                administrador, null, 5, "PERU", "INTERNO", "ACTIVO");

// Documentos adicionales para cubrir todos los casos de prueba
        crearDocumento("Reporte de personal", rrhh, 2, "PENDIENTE", "PERU", ana);            // id 4
        crearDocumento("Documento para prueba de eliminación", finanzas, 2, "PENDIENTE", "PERU", luis); // id 5
        crearDocumento("Plan estratégico confidencial", finanzas, 5, "PENDIENTE", "PERU", luis);        // id 6
        crearDocumento("Borrador nivel bajo", finanzas, 1, "PENDIENTE", "PERU", luis);                   // id 7
        crearDocumento("Documento de otro empleado", rrhh, 2, "PENDIENTE", "PERU", rosa);                // id 8


    }

    private Rol crearRol(String nombre, Map<String, Permiso> permisos, String... nombresPermisos) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        Set<Permiso> set = new HashSet<>();
        for (String n : nombresPermisos) set.add(permisos.get(n));
        rol.setPermisos(set);
        return rolRepository.save(rol);
    }

    private Departamento crearDepartamento(String nombre) {
        Departamento d = new Departamento();
        d.setNombre(nombre);
        return departamentoRepository.save(d);
    }

    private Usuario crearUsuario(String nombre, String correo, Rol rol, Departamento depto,
                                 int nivelSeguridad, String pais, String tipoContrato, String estado) {
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setPassword(passwordEncoder.encode("123456")); // clave de prueba para todos
        u.setRol(rol);
        u.setDepartamento(depto);
        u.setNivelSeguridad(nivelSeguridad);
        u.setPais(pais);
        u.setTipoContrato(tipoContrato);
        u.setEstado(estado);
        return usuarioRepository.save(u);
    }

    private void crearDocumento(String titulo, Departamento depto, int nivelConfidencialidad,
                                String estado, String pais, Usuario propietario) {
        Documento d = new Documento();
        d.setTitulo(titulo);
        d.setDepartamento(depto);
        d.setNivelConfidencialidad(nivelConfidencialidad);
        d.setEstado(estado);
        d.setPais(pais);
        d.setPropietario(propietario);
        d.setFechaCreacion(LocalDateTime.now());
        documentoRepository.save(d);
    }
}