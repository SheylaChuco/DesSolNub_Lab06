package com.techcorp.securedocs.repository;

import com.techcorp.securedocs.model.Permiso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Optional<Permiso> findByNombre(String nombre);
}
