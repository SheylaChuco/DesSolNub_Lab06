package com.techcorp.securedocs.service;

import com.techcorp.securedocs.dto.DocumentoDTO;
import com.techcorp.securedocs.model.Documento;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.repository.DepartamentoRepository;
import com.techcorp.securedocs.repository.DocumentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDateTime;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DepartamentoRepository departamentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository, DepartamentoRepository departamentoRepository) {
        this.documentoRepository = documentoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public Documento crear(DocumentoDTO dto, Usuario propietario) {
        Documento doc = new Documento();
        doc.setTitulo(dto.getTitulo());
        doc.setDescripcion(dto.getDescripcion());
        doc.setDepartamento(departamentoRepository.findById(dto.getDepartamentoId())
                .orElseThrow(() -> new RuntimeException("Departamento no existe")));
        doc.setNivelConfidencialidad(dto.getNivelConfidencialidad());
        doc.setPais(dto.getPais());
        doc.setPropietario(propietario);
        doc.setEstado("PENDIENTE");
        doc.setFechaCreacion(LocalDateTime.now());
        return documentoRepository.save(doc);
    }

    public Documento buscarPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no existe"));
    }

    public Documento aprobar(Long id) {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no existe"));
        doc.setEstado("APROBADO");
        return documentoRepository.save(doc);
    }



    public void eliminar(Long id) {
        documentoRepository.deleteById(id);
    }
    public Documento actualizar(Long id, DocumentoDTO dto) {
        Documento doc = buscarPorId(id);
        doc.setTitulo(dto.getTitulo());
        doc.setDescripcion(dto.getDescripcion());
        return documentoRepository.save(doc);
    }

    public List<Documento> listarTodos() {
        return documentoRepository.findAll();
    }
}