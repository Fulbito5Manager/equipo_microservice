package com.api.equipo.service;

import com.api.equipo.domain.Equipo;
import com.api.equipo.domain.Jugador;

import java.util.List;
import java.util.Optional;

public interface EquipoService {
    List<Equipo> getAllEquipos();
    Optional<Equipo> getEquipoById(Long id);
    Equipo saveEquipo(Equipo equipo);
    void addJugador(Long jugador, Long equipo);
}
