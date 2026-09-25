package com.techcorp.securedocs.repository;

import com.techcorp.securedocs.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}