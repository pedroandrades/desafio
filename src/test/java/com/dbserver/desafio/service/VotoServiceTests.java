package com.dbserver.desafio.service;


import com.dbserver.desafio.dto.VotoDTO;
import com.dbserver.desafio.exception.MaisDeUmVotoPorDiaException;
import com.dbserver.desafio.exception.RestauranteGanhadorDaSemanaAtualException;
import com.dbserver.desafio.model.ProfissionalFaminto;
import com.dbserver.desafio.model.Restaurante;
import com.dbserver.desafio.model.Voto;
import com.dbserver.desafio.repository.ProfissionalFamintoRepository;
import com.dbserver.desafio.repository.RestauranteRepository;
import com.dbserver.desafio.repository.VotoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class VotoServiceTests {

    @MockBean
    private RestauranteRepository restauranteRepository;

    @MockBean
    private ProfissionalFamintoRepository profissionalFamintoRepository;

    @MockBean
    private VotoRepository votoRepository;

    private VotoService votoService;

    private ProfissionalFaminto profissionalFaminto;

    private Restaurante restaurante;

    @Before
    public void setUp() {
        restaurante = new Restaurante("restaurante");
        profissionalFaminto = new ProfissionalFaminto("teste@teste.com", "teste");
        votoService = new VotoService(restauranteRepository, profissionalFamintoRepository, votoRepository);
    }

    @Test
    public void votarTest(){
        VotoDTO votoDTO = new VotoDTO("restaurante");
        Principal principal = () -> "teste@teste.com";
        given(restauranteRepository.findByName(restaurante.getNome())).willReturn(Optional.ofNullable(restaurante));
        given(profissionalFamintoRepository.findByEmail(profissionalFaminto.getEmail())).willReturn(Optional.ofNullable(profissionalFaminto));
        votoService.votar(votoDTO, principal);
    }

    @Test
    public void atualizarProfissionalFamintoTest(){
        votoService.atualizarProfissionalFaminto(profissionalFaminto);
        assertThat(profissionalFaminto.getDiaVotado()).isNotNull();
    }

    @Test(expected = RestauranteGanhadorDaSemanaAtualException.class)
    public void checarRestauranteGanhadorDaUltimaSemanaTest(){
        Restaurante restaurante = new Restaurante(LocalDate.now());
        votoService.checarRestauranteGanhadorDaUltimaSemana(restaurante);
    }

    @Test(expected = MaisDeUmVotoPorDiaException.class)
    public void checarVotoProfissionalFamintoTest(){
        ProfissionalFaminto profissionalFaminto = new ProfissionalFaminto("teste@teste.com", "teste", LocalDate.now());
        votoService.checarVotoProfissionalFaminto(profissionalFaminto);
    }

    @Test
    public void checarRestauranteVencedorTest(){
        Voto voto = new Voto(LocalDate.now(), restaurante, profissionalFaminto);
        List<Voto> votoList = new ArrayList<>();
        votoList.add(voto);
        given(votoRepository.findAll()).willReturn(votoList);
        given(restauranteRepository.findByName(restaurante.getNome())).willReturn(Optional.ofNullable(restaurante));
        assertThat(votoService.checarRestauranteVencedor()).isEqualTo(restaurante.getNome());
    }

}
