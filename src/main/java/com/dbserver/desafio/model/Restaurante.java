package com.dbserver.desafio.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Restaurante {

    @NotNull
    private String nome;

    private LocalDate dataEscolhida;

    public Restaurante(String nome) {
        this.nome = nome;
    }

    public Restaurante(LocalDate dataEscolhida) {
        this.dataEscolhida = dataEscolhida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataEscolhida() {
        return dataEscolhida;
    }

    public void setDataEscolhida(LocalDate dataEscolhida) {
        this.dataEscolhida = dataEscolhida;
    }
}
