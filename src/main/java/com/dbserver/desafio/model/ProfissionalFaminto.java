package com.dbserver.desafio.model;

import java.time.LocalDate;

public class ProfissionalFaminto extends Usuario{

    private LocalDate diaVotado;

    public ProfissionalFaminto(String email, String senha) {
        super(email, senha);
    }

    public ProfissionalFaminto(String email, String senha, LocalDate diaVotado) {
        super(email, senha);
        this.diaVotado = diaVotado;
    }

    public LocalDate getDiaVotado() {
        return diaVotado;
    }

    public void setDiaVotado(LocalDate diaVotado) {
        this.diaVotado = diaVotado;
    }
}
