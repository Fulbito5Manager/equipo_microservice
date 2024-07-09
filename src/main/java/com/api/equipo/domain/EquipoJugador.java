package com.api.equipo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "equipo_jugador")
public class EquipoJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long equipoId;
    private Long jugadorId;
}
