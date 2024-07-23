package com.api.equipo.repository;

import com.api.equipo.domain.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo,Long> {
    Long findPartidoIdById(Long id);

}
