package com.dbserver.desafio.repository;

import com.dbserver.desafio.model.Voto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VotoRepository {

    private List<Voto> votoList;

    public VotoRepository() {
        this.votoList = new ArrayList<>();
    }

    public void votar(Voto voto){
        votoList.add(voto);
    }

    public List<Voto> findAll(){
        return votoList;
    }

    public void deletarTudo() {
        votoList = new ArrayList<>();
    }
}
