package com.dbserver.desafio.controller;

import com.dbserver.desafio.dto.VotoDTO;
import com.dbserver.desafio.exception.InexistenteException;
import com.dbserver.desafio.exception.MaisDeUmVotoPorDiaException;
import com.dbserver.desafio.exception.RestauranteGanhadorDaSemanaAtualException;
import com.dbserver.desafio.resposta.Resposta;
import com.dbserver.desafio.service.VotoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;

    @Autowired
    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @ApiOperation(value = "Verifica qual foi o ganhador do dia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "O restaurante vencedor foi: ..."),
            @ApiResponse(code = 404, message = "O restaurante não existe."),
            @ApiResponse(code = 417, message = "Não foi possível computar os votos."),
    })
    @GetMapping("/vencedor")
    public ResponseEntity<Resposta> checarRestauranteVencedor(){
        String restauranteVencedor = votoService.checarRestauranteVencedor();
        return ResponseEntity.ok(new Resposta("O restaurante vencedor foi: " + restauranteVencedor));
    }

    @ApiOperation(value = "Vota em um determinado restaurante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Seu voto foi computado."),
            @ApiResponse(code = 404, message = "O restaurante/profissional faminto não existe."),
            @ApiResponse(code = 406, message = "Você já votou hoje."),
            @ApiResponse(code = 417, message = "O restaurante escolhido já ganhou essa semana."),
    })
    @PostMapping
    public ResponseEntity<Resposta> votar(@Valid @RequestBody VotoDTO votoDTO, Principal principal){
        votoService.votar(votoDTO, principal);
        return ResponseEntity.ok(new Resposta("Seu voto foi computado."));
    }

    @ExceptionHandler(MaisDeUmVotoPorDiaException.class)
    public ResponseEntity<JsonNode> handleMaisDeUmVotoPorDiaException(MaisDeUmVotoPorDiaException e){
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("status", notAcceptable.value());
        jsonNode.put("message", e.getMessage());
        return ResponseEntity.status(notAcceptable).body(jsonNode);
    }

    @ExceptionHandler(RestauranteGanhadorDaSemanaAtualException.class)
    public ResponseEntity<JsonNode> handleRestauranteGanhadorDaSemanaAtualException(RestauranteGanhadorDaSemanaAtualException e){
        HttpStatus expectationFailed = HttpStatus.EXPECTATION_FAILED;
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("status", expectationFailed.value());
        jsonNode.put("message", e.getMessage());
        return ResponseEntity.status(expectationFailed).body(jsonNode);
    }

    @ExceptionHandler(InexistenteException.class)
    public ResponseEntity<JsonNode> handleInexistenteException(InexistenteException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("status", notFound.value());
        jsonNode.put("message", e.getMessage());
        return ResponseEntity.status(notFound).body(jsonNode);
    }

}
