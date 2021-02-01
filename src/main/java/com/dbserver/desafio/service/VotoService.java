package com.dbserver.desafio.service;

import com.dbserver.desafio.dto.VotoDTO;
import com.dbserver.desafio.exception.InexistenteException;
import com.dbserver.desafio.exception.MaisDeUmVotoPorDiaException;
import com.dbserver.desafio.exception.RestauranteGanhadorDaSemanaAtualException;
import com.dbserver.desafio.model.ProfissionalFaminto;
import com.dbserver.desafio.model.Restaurante;
import com.dbserver.desafio.model.Voto;
import com.dbserver.desafio.repository.ProfissionalFamintoRepository;
import com.dbserver.desafio.repository.RestauranteRepository;
import com.dbserver.desafio.repository.VotoRepository;
import org.jooq.lambda.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotoService {

    private final RestauranteRepository restauranteRepository;

    private final ProfissionalFamintoRepository profissionalFamintoRepository;

    private final VotoRepository votoRepository;

    @Autowired
    public VotoService(RestauranteRepository restauranteRepository, ProfissionalFamintoRepository profissionalFamintoRepository, VotoRepository votoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.profissionalFamintoRepository = profissionalFamintoRepository;
        this.votoRepository = votoRepository;
    }

    public void votar(VotoDTO votoDTO, Principal principal) {
        Restaurante restaurante = restauranteRepository.findByName(votoDTO.getNomeDoRestaurante()).orElseThrow(() -> new InexistenteException("O restaurante não existe."));
        ProfissionalFaminto profissionalFaminto = profissionalFamintoRepository.findByEmail(principal.getName()).orElseThrow(() -> new InexistenteException("O profissional faminto não existe"));
        checarVotoProfissionalFaminto(profissionalFaminto);
        checarRestauranteGanhadorDaUltimaSemana(restaurante);
        atualizarProfissionalFaminto(profissionalFaminto);
        votoRepository.votar(new Voto(LocalDate.now(), restaurante, profissionalFaminto));
    }

    public String checarRestauranteVencedor() {
        List<Voto> votoList = votoRepository.findAll();
        return computarVotos(votoList);
    }

    public void atualizarProfissionalFaminto(ProfissionalFaminto profissionalFaminto) {
        profissionalFaminto.setDiaVotado(LocalDate.now());
        profissionalFamintoRepository.update(profissionalFaminto);
    }

    public void checarRestauranteGanhadorDaUltimaSemana(Restaurante restaurante) {
        if ((restaurante.getDataEscolhida() != null) && ((restaurante.getDataEscolhida().getDayOfYear() - LocalDate.now().getDayOfYear()) < 7)) {
            throw new RestauranteGanhadorDaSemanaAtualException("O restaurante escolhido já ganhou essa semana.");
        }
    }

    public void checarVotoProfissionalFaminto(ProfissionalFaminto profissionalFaminto) {
        if (profissionalFaminto.getDiaVotado() != null && profissionalFaminto.getDiaVotado().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
            throw new MaisDeUmVotoPorDiaException("Você já votou hoje.");
        }
    }

    public String computarVotos(List<Voto> votoList) {
        List<Voto> votosAceitos = verificarDiaDoVoto(votoList);
        List<String> restaurantesVotados = votosAceitos.stream()
                                                .map(Voto::getRestaurante)
                                                .map(Restaurante::getNome)
                                                .collect(Collectors.toList());
        Optional<List<String>> restauranteVencedorList = Seq.of(restaurantesVotados).mode();
        if(!restauranteVencedorList.isPresent()) {
           throw new InexistenteException("O restaurante não existe.");
        }
        String restauranteVencedor = restauranteVencedorList.get().get(0);
        validarRestauranteVencedor(restauranteVencedor);
        votoRepository.deletarTudo();
        return restauranteVencedor;
    }

    public void validarRestauranteVencedor(String restauranteVencedor) {
        Restaurante restaurante = restauranteRepository.findByName(restauranteVencedor).orElseThrow(() -> new InexistenteException("O restaurante não existe."));
        restaurante.setDataEscolhida(LocalDate.now());
        restauranteRepository.update(restaurante);
    }

    public List<Voto> verificarDiaDoVoto(List<Voto> votoList) {
        return votoList.stream()
                .filter(p -> p.getDiaDoVoto().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());
    }
}
