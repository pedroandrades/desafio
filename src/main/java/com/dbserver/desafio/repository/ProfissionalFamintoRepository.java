package com.dbserver.desafio.repository;

import com.dbserver.desafio.exception.InexistenteException;
import com.dbserver.desafio.model.ProfissionalFaminto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfissionalFamintoRepository {

    private List<ProfissionalFaminto> profissionalFamintoList;

    public ProfissionalFamintoRepository() {
        this.profissionalFamintoList = new ArrayList<>();
        this.profissionalFamintoList.add(new ProfissionalFaminto("admin@dbserver.com", "$2a$10$N6Tw6jzY0L0tvmMg6XUc3uX42WqmndcP72Cp6Xslu8J3oZiunnzVC"));
        this.profissionalFamintoList.add(new ProfissionalFaminto("dev@dbserver.com", "$2a$10$N6Tw6jzY0L0tvmMg6XUc3uX42WqmndcP72Cp6Xslu8J3oZiunnzVC"));
        this.profissionalFamintoList.add(new ProfissionalFaminto("tester@dbserver.com", "$2a$10$N6Tw6jzY0L0tvmMg6XUc3uX42WqmndcP72Cp6Xslu8J3oZiunnzVC"));
    }

    public Optional<ProfissionalFaminto> findByEmail(String email) {
        return profissionalFamintoList.stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }

    public void update(ProfissionalFaminto profissionalFaminto) {
        ProfissionalFaminto profissionalFamintoSalvado = findByEmail(profissionalFaminto.getEmail()).orElseThrow(() -> new InexistenteException("Não foi encontrado nenhum profissional faminto para realizar a atualização"));
        profissionalFamintoList.remove(profissionalFamintoSalvado);
        profissionalFamintoSalvado.setDiaVotado(profissionalFaminto.getDiaVotado());
        profissionalFamintoList.add(profissionalFamintoSalvado);


    }

}
