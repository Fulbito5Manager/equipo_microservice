package com.api.equipo.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EquipoUpdateRequestDto {

    private String tipo;
    private Long partidoId;
    private List<Long> jugadores;
}
