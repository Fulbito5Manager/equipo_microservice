package com.api.equipo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Jugador {
    private Long id;
    private String nombre;
    private int media;
    private String email;
}