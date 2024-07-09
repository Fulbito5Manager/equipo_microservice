package com.api.equipo.service;

import com.api.equipo.domain.Partido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PartidoServiceImpl implements PartidoService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Partido getPartidoById(Long partidoId) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8081/api/partidos/" + partidoId,
                    Partido.class
            );
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                log.warn("El API de partido retorno 404 para el ID: " + partidoId);
                return null;
            }
            throw e;
        }
    }


}
