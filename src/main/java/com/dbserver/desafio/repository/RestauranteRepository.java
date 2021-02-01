package com.dbserver.desafio.repository;

import com.dbserver.desafio.exception.InexistenteException;
import com.dbserver.desafio.model.Restaurante;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRepository {

    private List<Restaurante> restauranteList;

    public RestauranteRepository() {
        this.restauranteList = new ArrayList<>();
        restauranteList.add(new Restaurante("Tudo pelo social"));
        restauranteList.add(new Restaurante("Coco bambu"));
    }

    public Optional<Restaurante> findByName(String name){
        return restauranteList.stream()
                .filter(p -> p.getNome().equals(name))
                .findFirst();
    }

    public void update(Restaurante restaurante) {
        Restaurante restauranteSalvado = findByName(restaurante.getNome()).orElseThrow(() -> new InexistenteException("Não foi encontrado nenhum restaurante para realizar a atualização"));
        restauranteList.remove(restauranteSalvado);
        restauranteSalvado.setDataEscolhida(restaurante.getDataEscolhida());
        restauranteList.add(restauranteSalvado);
    }
}
