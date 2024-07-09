package com.api.equipo.controller;

import com.api.equipo.domain.Equipo;
import com.api.equipo.service.EquipoService;
import com.api.equipo.service.EquipoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public List<Equipo> getAllEquipos(){
      return equipoService.getAllEquipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id){
        Optional<Equipo> equipo = equipoService.getEquipoById(id);
        return equipo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/partido/{partidoId}")
    public List<Equipo> getEquiposById(@PathVariable Long partidoId){
        Partido partido = partidoService.getPartidoById(id);
        if (partido != null) {
            return equipoService.
        } else {
            throw new RuntimeException("Partido no encontrado");
        }
        return null;
    }

    @PostMapping
    public Equipo createEquipo(@RequestBody Equipo equipo){

        return equipoService.saveEquipo(equipo);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Equipo> updateEquipo(@PathVariable Long id, @RequestBody Equipo equipoDetails){
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
}
