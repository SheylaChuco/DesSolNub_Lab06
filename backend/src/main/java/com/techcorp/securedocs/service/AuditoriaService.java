package com.techcorp.securedocs.service;

import com.techcorp.securedocs.model.Auditoria;
import com.techcorp.securedocs.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public void registrar(String usuario, String recurso, String accion, String resultado, String motivo) {
        Auditoria log = new Auditoria();
        log.setUsuario(usuario);
        log.setRecurso(recurso);
        log.setAccion(accion);
        log.setFecha(LocalDateTime.now());
        log.setResultado(resultado);
        log.setMotivo(motivo);
        auditoriaRepository.save(log);
    }

    public List<Auditoria> listarTodo() {
        return auditoriaRepository.findAll();
    }
}