package com.api.equipo.controller;

import com.api.equipo.domain.Equipo;
import com.api.equipo.domain.Jugador;
import com.api.equipo.dto.AddJugadorRequestDto;
import com.api.equipo.dto.EquipoUpdateRequestDto;
import com.api.equipo.exception.EquipoIsNotExistException;
import com.api.equipo.service.EquipoService;
import com.api.equipo.service.EquipoServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipos")
@Tag(name = "Equipo Management System", description = "Operations pertaining to team in Equipo Management System")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;
    @Operation(summary = "Devuelve una lista de equipos con todos los equipos")
    @GetMapping
    public List<Equipo> getAllEquipos(){
      return equipoService.getAllEquipos();
    }
    @Operation(summary = "Devuelve un equipo con un Id de equipo particular")
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(
            @Parameter(description = "ID del equipo a devolver", required = true)
            @PathVariable Long id){
        Optional<Equipo> equipo = equipoService.getEquipoById(id);
        return equipo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(summary = "Devuelve una lista de equipos con un Id de partido particular")
    @GetMapping("/partido/{partidoId}")
    public List<Equipo> getEquiposByPartidoId(
            @Parameter(description = "ID del partido con equipos a devolver", required = true)
            @PathVariable Long partidoId){
        return equipoService.getEquiposByPartidoId(partidoId);
    }
    @Operation(summary = "Devuelve una lista de jugadores con un Id de equipo particular")
    @GetMapping("/{equipoId}/jugadores")
    public List<Long> getJugadoresByEquipoId(
            @Parameter(description = "ID del equipo con jugadores a devolver", required = true)
            @PathVariable Long equipoId){
        return equipoService.getEquipoById(equipoId)
                .map(Equipo::getJugadores)
                .orElseThrow();
    }

    @Operation(summary = "Crea un nuevo equipo")
    @PostMapping
    public Equipo createEquipo(
            @Parameter(description = "Objeto equipo a crear", required = true)
            @RequestBody Equipo equipo){
        return equipoService.saveEquipo(equipo);
    }

    @Operation(summary = "Agrega un nuevo jugador a un equipo")
    @PostMapping("/{id}/jugadores")
    public ResponseEntity addJugador(
            @Parameter(description = "Objeto request para agregar a un jugador", required = true)
            @RequestBody AddJugadorRequestDto addJugadorRequestDto,
            @Parameter(description = "Id del equipo para agregar al jugador", required = true)
            @PathVariable Long id) {
        Long jugadorId = addJugadorRequestDto.getJugadorId();
        equipoService.addJugador(addJugadorRequestDto.getJugadorId(), id);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Devuelve un Equipo con un atributo actualizado")
    @PatchMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipoById(
            @Parameter(description = "ID del equipo a parchear", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto Request para parchear un equipo", required = true)
            @RequestBody EquipoUpdateRequestDto equipoUpdateRequestDto) {
        Optional<Equipo> equipo = equipoService.getEquipoById(id);
        if (equipo.isPresent()) {
            Equipo equipoEncontrado = equipo.get();
            if (equipoUpdateRequestDto.getTipo() != null) {
                equipoEncontrado.setTipo(equipoUpdateRequestDto.getTipo());
            }
            if (equipoUpdateRequestDto.getPartidoId() != null) {
                equipoEncontrado.setPartidoId(equipoUpdateRequestDto.getPartidoId());
            }
            if (equipoUpdateRequestDto.getJugadores() != null) {
                equipoEncontrado.setJugadores(equipoUpdateRequestDto.getJugadores());
            }
            Equipo updatedEquipo = equipoService.saveEquipo(equipoEncontrado);
            return ResponseEntity.ok(updatedEquipo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualiza un equipo existente")
    @PutMapping("/{id}")
    public  ResponseEntity<Equipo> updateEquipo(
            @Parameter(description = "Id del equipo a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto de equipo actualizado", required = true)
            @RequestBody Equipo equipoDetails){
        Optional<Equipo> equipo = equipoService.getEquipoById(id);
        if (equipo.isPresent()){
            Equipo existingEquipo = equipo.get();
            existingEquipo.setTipo(equipoDetails.getTipo());
            existingEquipo.setPartidoId(equipoDetails.getPartidoId());
            Equipo updateEquipo = equipoService.saveEquipo(existingEquipo);
            return  ResponseEntity.ok(updateEquipo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Borra un equipo con un id en particular")
    @DeleteMapping("/{id}")
    public void deleteEquipoById(
            @Parameter(description = "Id del equipo a borrar", required = true)
            @PathVariable Long id){
        Optional<Equipo> equipo = equipoService.getEquipoById(id);
        if(equipo.isPresent())
            equipoService.deleteEquipoById(id);
        throw new EquipoIsNotExistException("El equipo que quiere eliminar no existe");
    }
}
