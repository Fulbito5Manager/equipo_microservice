package com.api.equipo.service;

import com.api.equipo.domain.Jugador;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class JugadorServiceImpl implements JugadorService{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Jugador getJugadorById(Long jugadorId) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/api/jugadores/" + jugadorId,
                    Jugador.class
            );
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                log.warn("El API de jugador retorno 404 para el ID: " + jugadorId);
                return null;
            }
            throw e;
        }
    }
}
