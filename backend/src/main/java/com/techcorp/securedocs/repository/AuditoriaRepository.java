package com.techcorp.securedocs.repository;

import com.techcorp.securedocs.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
}
