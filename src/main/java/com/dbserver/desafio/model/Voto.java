package com.dbserver.desafio.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Voto {

    @NotNull
    private LocalDate diaDoVoto;

    @NotNull
    private Restaurante restaurante;

    @NotNull
    private ProfissionalFaminto profissionalFaminto;

    public Voto(@NotNull LocalDate diaDoVoto, @NotNull Restaurante restaurante, @NotNull ProfissionalFaminto profissionalFaminto) {
        this.diaDoVoto = diaDoVoto;
        this.restaurante = restaurante;
        this.profissionalFaminto = profissionalFaminto;
    }

    public LocalDate getDiaDoVoto() {
        return diaDoVoto;
    }

    public void setDiaDoVoto(LocalDate diaDoVoto) {
        this.diaDoVoto = diaDoVoto;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public ProfissionalFaminto getProfissionalFaminto() {
        return profissionalFaminto;
    }

    public void setProfissionalFaminto(ProfissionalFaminto profissionalFaminto) {
        this.profissionalFaminto = profissionalFaminto;
    }
}
