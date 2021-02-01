package com.dbserver.desafio.dto;

import javax.validation.constraints.NotNull;

public class VotoDTO {

    @NotNull
    private String nomeDoRestaurante;

    public String getNomeDoRestaurante() {
        return nomeDoRestaurante;
    }

    public void setNomeDoRestaurante(String nomeDoRestaurante) {
        this.nomeDoRestaurante = nomeDoRestaurante;
    }

    public VotoDTO(String nomeDoRestaurante) {
        this.nomeDoRestaurante = nomeDoRestaurante;
    }

    public VotoDTO() {
    }
}
