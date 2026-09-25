package com.techcorp.securedocs.controller;

import com.techcorp.securedocs.dto.DocumentoDTO;
import com.techcorp.securedocs.model.Documento;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.security.AuthenticatedUserProvider;
import com.techcorp.securedocs.security.authorization.AuthorizationService;
import com.techcorp.securedocs.service.DocumentoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;
    private final AuthorizationService authorizationService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public DocumentoController(DocumentoService documentoService, AuthorizationService authorizationService,
                               AuthenticatedUserProvider authenticatedUserProvider) {
        this.documentoService = documentoService;
        this.authorizationService = authorizationService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @PostMapping
    public Documento crear(@RequestBody DocumentoDTO dto,
                           @RequestHeader(value = "X-Ip", defaultValue = "0.0.0.0") String ip,
                           @RequestHeader(value = "X-Ubicacion", defaultValue = "PERU") String ubicacion,
                           @RequestHeader(value = "X-Dispositivo", defaultValue = "PERSONAL") String dispositivo) {

        Usuario usuario = authenticatedUserProvider.obtenerUsuarioActual();

        authorizationService.autorizar(usuario, null, "CREAR_DOCUMENTO", "CREAR", ip, ubicacion, dispositivo);

        return documentoService.crear(dto, usuario);
    }

    @GetMapping("/{id}")
    public Documento consultar(@PathVariable Long id,
                               @RequestHeader(value = "X-Ip", defaultValue = "0.0.0.0") String ip,
                               @RequestHeader(value = "X-Ubicacion", defaultValue = "PERU") String ubicacion,
                               @RequestHeader(value = "X-Dispositivo", defaultValue = "PERSONAL") String dispositivo) {

        Usuario usuario = authenticatedUserProvider.obtenerUsuarioActual();
        Documento documento = documentoService.buscarPorId(id);

        authorizationService.autorizar(usuario, documento, "CONSULTAR_DOCUMENTO", "CONSULTAR", ip, ubicacion, dispositivo);

        return documento;
    }

    @PutMapping("/{id}")
    public Documento modificar(@PathVariable Long id, @RequestBody DocumentoDTO dto,
                               @RequestHeader(value = "X-Ip", defaultValue = "0.0.0.0") String ip,
                               @RequestHeader(value = "X-Ubicacion", defaultValue = "PERU") String ubicacion,
                               @RequestHeader(value = "X-Dispositivo", defaultValue = "PERSONAL") String dispositivo) {

        Usuario usuario = authenticatedUserProvider.obtenerUsuarioActual();
        Documento documento = documentoService.buscarPorId(id);

        authorizationService.autorizar(usuario, documento, "MODIFICAR_DOCUMENTO", "MODIFICAR", ip, ubicacion, dispositivo);

        return documentoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id,
                         @RequestHeader(value = "X-Ip", defaultValue = "0.0.0.0") String ip,
                         @RequestHeader(value = "X-Ubicacion", defaultValue = "PERU") String ubicacion,
                         @RequestHeader(value = "X-Dispositivo", defaultValue = "PERSONAL") String dispositivo) {

        Usuario usuario = authenticatedUserProvider.obtenerUsuarioActual();
        Documento documento = documentoService.buscarPorId(id);

        authorizationService.autorizar(usuario, documento, "ELIMINAR_DOCUMENTO", "ELIMINAR", ip, ubicacion, dispositivo);

        documentoService.eliminar(id);
    }

    @PostMapping("/{id}/aprobar")
    public Documento aprobar(@PathVariable Long id,
                             @RequestHeader(value = "X-Ip", defaultValue = "0.0.0.0") String ip,
                             @RequestHeader(value = "X-Ubicacion", defaultValue = "PERU") String ubicacion,
                             @RequestHeader(value = "X-Dispositivo", defaultValue = "PERSONAL") String dispositivo) {

        Usuario usuario = authenticatedUserProvider.obtenerUsuarioActual();
        Documento documento = documentoService.buscarPorId(id);

        authorizationService.autorizar(usuario, documento, "APROBAR_DOCUMENTO", "APROBAR", ip, ubicacion, dispositivo);

        return documentoService.aprobar(id);
    }

    @GetMapping
    public List<Documento> listar() {
        return documentoService.listarTodos();
    }
}