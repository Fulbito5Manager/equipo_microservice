package com.api.equipo.domain;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Partido {


    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;
    private Long canchaId;
    private String estado;

    public Partido() {
    }

    public Partido(Long id, LocalDateTime date, Long canchaId, String estado) {
        this.id = id;
        this.date = date;
        this.canchaId = canchaId;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(Long canchaId) {
        this.canchaId = canchaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}