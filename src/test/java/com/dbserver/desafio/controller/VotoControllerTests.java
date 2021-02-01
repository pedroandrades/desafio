package com.dbserver.desafio.controller;

import com.dbserver.desafio.dto.VotoDTO;
import com.dbserver.desafio.exception.InexistenteException;
import com.dbserver.desafio.exception.MaisDeUmVotoPorDiaException;
import com.dbserver.desafio.exception.RestauranteGanhadorDaSemanaAtualException;
import com.dbserver.desafio.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class VotoControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VotoService votoService;

    private final String url = "/votos/";

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void votarTest() throws Exception {
        VotoDTO votoDTO = new VotoDTO("Coco bambu");

        JSONObject esperado = new JSONObject();
        esperado.put("mensagem", "Seu voto foi computado.");

        mvc.perform(post(url)
                .content(mapper.writeValueAsString(votoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(esperado)));
    }

    @Test
    public void checarRestauranteVencedorTest() throws Exception {
        String restauranteVencedor = "restaurante";

        given(votoService.checarRestauranteVencedor()).willReturn(restauranteVencedor);

        JSONObject esperado = new JSONObject();
        esperado.put("mensagem", "O restaurante vencedor foi: restaurante");

        mvc.perform(get(url+"/vencedor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(esperado)));
    }

    @Test
    public void handleMaisDeUmVotoPorDiaExceptionTest() throws Exception {
        given(votoService.checarRestauranteVencedor()).willThrow(new MaisDeUmVotoPorDiaException("Teste de exceção"));

        JSONObject esperado = new JSONObject();
        esperado.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        esperado.put("message", "Teste de exceção");

        mvc.perform(get(url+"/vencedor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().json(String.valueOf(esperado)));
    }

    @Test
    public void handleRestauranteGanhadorDaSemanaAtualExceptionTest() throws Exception {
        given(votoService.checarRestauranteVencedor()).willThrow(new RestauranteGanhadorDaSemanaAtualException("Teste de exceção"));

        JSONObject esperado = new JSONObject();
        esperado.put("status", HttpStatus.EXPECTATION_FAILED.value());
        esperado.put("message", "Teste de exceção");

        mvc.perform(get(url+"/vencedor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content().json(String.valueOf(esperado)));
    }

    @Test
    public void handleInexistenteExceptionTest() throws Exception {
        given(votoService.checarRestauranteVencedor()).willThrow(new InexistenteException("Teste de exceção"));

        JSONObject esperado = new JSONObject();
        esperado.put("status", HttpStatus.NOT_FOUND.value());
        esperado.put("message", "Teste de exceção");

        mvc.perform(get(url+"/vencedor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(String.valueOf(esperado)));
    }
}
