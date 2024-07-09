package com.api.equipo.service;

import com.api.equipo.domain.Equipo;
import com.api.equipo.domain.Jugador;
import com.api.equipo.domain.Partido;
import com.api.equipo.exception.EquipoNotFoundException;
import com.api.equipo.exception.JugadorNotFoundException;
import com.api.equipo.exception.PartidoNotFoundException;
import com.api.equipo.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private PartidoService partidoService;
    @Autowired
    private JugadorService jugadorService;

    public List<Equipo> getAllEquipos(){
        return equipoRepository.findAll();
    }

    public Optional<Equipo> getEquipoById(Long id){
        return equipoRepository.findById(id);
    }

    public Equipo saveEquipo(Equipo equipo){
        Long partidoId = equipo.getPartidoId();
        if(partidoExiste(partidoId)){
            return equipoRepository.save(equipo);
        } else {
             throw new PartidoNotFoundException("No se encontro un partido con Id: " + partidoId) ;
        }
    }

    @Override
    public void addJugador(Long jugadorId, Long equipoId) {
        Jugador jugador = getJugadorByIdOrElseThrow(jugadorId);
        Equipo equipo = getEquipoByIdOrElseThrow(equipoId);
        equipo.getJugadores().add(jugador.getId());
        equipoRepository.save(equipo);
    }

    private Jugador getJugadorByIdOrElseThrow(Long jugadorId) {
        /*busco el jugador por id y lo guardo en una variable
        * en caso de que el jugador sea null lanzo un jugador not found exception
            retornar la variable
        * */
        Jugador jugador =  jugadorService.getJugadorById(jugadorId);
        if(jugador == null){
            throw new JugadorNotFoundException("El jugador no fue encontrado");
        } else {
            return jugador;
        }
    }

    private Equipo getEquipoByIdOrElseThrow(Long equipoId) {
        return equipoRepository.findById(equipoId)
                .orElseThrow(() -> new EquipoNotFoundException("El equipo no fue encontrado"));
    }

    private boolean partidoExiste(Long partidoId) {
        Partido partido = partidoService.getPartidoById(partidoId);
        return partido != null;
    }

}
