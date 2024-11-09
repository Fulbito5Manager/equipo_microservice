package com.api.equipo.domain;


import jakarta.persistence.Column;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo", nullable = false)
    private String tipo;
    @Column(name = "partido_id",nullable = false)
    private Long partidoId;

    @ElementCollection
    @CollectionTable(name = "equipo_jugadores", joinColumns = @JoinColumn(name = "equipo_id"))
    @Column(name = "jugador_id")
    private List<Long> jugadores;

    public List<Long> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Long> jugadores) {
        this.jugadores = jugadores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }
}
