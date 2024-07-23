package com.api.equipo.service;

import com.api.equipo.domain.Equipo;
import com.api.equipo.domain.Jugador;
import com.api.equipo.domain.Partido;
import com.api.equipo.exception.*;
import com.api.equipo.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private PartidoService partidoService;
    @Autowired
    private JugadorService jugadorService;

    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> getEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    @Override
    public List<Equipo> getEquiposByPartidoId(Long partidoId) {
        if (partidoExiste(partidoId)) {
            return equipoRepository.findAll().stream()
                    .filter(equipo -> equipo.getPartidoId().equals(partidoId))
                    .toList();
        } else {
            throw new RuntimeException("Partido no encontrado");
        }
    }

    public Equipo saveEquipo(Equipo equipo) {
        Long partidoId = equipo.getPartidoId();
        if (partidoExiste(partidoId)) {
            return equipoRepository.save(equipo);
        } else {
            throw new PartidoNotFoundException("No se encontro un partido con Id: " + partidoId);
        }
    }

    @Override
    public void deleteEquipoById(Long id) {
        equipoRepository.deleteById(id);
    }

    @Override
    public void addJugador(Long jugadorId, Long equipoId) {
        /*si el jugador está en el equipo no se puede volver a agregar
         * -------------------------------------------------------------------------------
         * NO LISTO
         * -Hay que verificar que cuando se añade un jugador con addJugador,
         *  ese jugador no esté en ninguno de los 2 equipos del partido para poder
         *  agregarlo.
         *  Si ya está en uno de los dos, solo debe poder agregarse al otro.
         *  No debería poder agregar un jugador a los dos equipos del partido.
         *
         * ----------------------------------------------------------------------
         * LISTO
         * -Los equipos ahora deben tener un máximo de jugadores.
         *  Si se intenta agregar un jugador a un equipo que ya está completo
         *  debe lanzar un error
         * --------------------------------------------------------------------
         * */


        Long partidoIdDelEquipo = getPartidoIdByEquipoId(equipoId);
        List<Equipo> equipos = getEquiposByPartidoId(partidoIdDelEquipo);
        List<Equipo> otrosEquipos = getOtrosEquipos(equipos, equipoId);
        List<Equipo> otrosEquiposSinJugador = eliminarJugadorDeOtrosEquiposSiExiste(otrosEquipos, jugadorId);


        Jugador jugador = getJugadorByIdOrElseThrow(jugadorId);
        Equipo equipo = getEquipoByIdOrElseThrow(equipoId);
        //equipo.getJugadores().contains(jugadorId) && equipo.getTipo().equals()
        //!equipo.getId().equals(jugadorRequest.getEquipoId()) && equipo.getJugadores().contains(jugadorRequest.getJugadorId()))


        if (equipo.getJugadores().size() == 5)
            throw new ListaJugadoresIsFullException("La lista a la que quieres añadir el jugador esta llena");

        if (equipo.getJugadores().contains(jugadorId))
            throw new JugadorAlreadyExistException("El jugador ya se encuentra en el equipo");
        else {
            equipo.getJugadores().add(jugador.getId());
            equipoRepository.save(equipo);
        }

    }

    private List<Equipo> eliminarJugadorDeOtrosEquiposSiExiste(List<Equipo> otrosEquipos, Long jugadorId) {
        return otrosEquipos.stream()
                .map(equipo -> {
                    List<Long> jugadoresFiltrados = equipo.getJugadores().stream()
                            .filter(jugador -> !jugador.equals(jugadorId))
                            .collect(Collectors.toList());
                    equipo.setJugadores(jugadoresFiltrados);
                    return equipo;
                })
                .collect(Collectors.toList());
    }


    private List<Equipo> getOtrosEquipos(List<Equipo> equipos,Long equipoId) {
        return  equipos.stream()
                .filter(equipo -> !equipo.getId().equals(equipoId))
                .collect(Collectors.toList());

    }
    private Long getPartidoIdByEquipoId(Long equipoId){
        //SELECT partido_id FROM equipo where equipo_id = 'equipo_id'
        return equipoRepository.findPartidoIdById(equipoId);
    }

    private Jugador getJugadorByIdOrElseThrow(Long jugadorId) {
        /*busco el jugador por id y lo guardo en una variable
        * en caso de que el jugador sea null lanzo un jugador not found exception
            retornar la variable
        * */
        Jugador jugador = jugadorService.getJugadorById(jugadorId);
        if (jugador == null) {
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
