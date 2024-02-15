package com.spring.animal.hotel.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
@Tag(name = "Hotel API")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Operation(summary = "Salva um novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente salvo com sucesso")
    })
    @PostMapping
    public ResponseEntity<ClientModel> saveClient(@RequestBody @Valid ClientDto clientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(clientDto));
    }

    @Operation(summary = "Retorna todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todos os clientes cadastrados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }

    @Operation(summary = "Retorna um cliente pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClient(@PathVariable(value = "id") int id) {
        Object serviceResponse = clientService.getOneClient(id);

        if (serviceResponse.getClass() == ClientModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @Operation(summary = "Retorna o custo total das reservas do cliente pelo id dele")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Custo total obtido com sucesso")
    })
    @GetMapping("/total-hostings-cost/{id}")
    public ResponseEntity<Integer> getTotalHostingsCost(@PathVariable(value = "id") int id) {
        Integer serviceResponse = clientService.getTotalHostingsCost(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

    @Operation(summary = "Atualiza todos os dados de um cliente pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados do cliente atualizados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value = "id") int id, @RequestBody @Valid ClientDto clientDto) {
        Object serviceResponse = clientService.updateClient(id, clientDto);

        if (serviceResponse.getClass() == ClientModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @Operation(summary = "Apaga cadastro do cliente pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleção bem sucedida Sw"),
        @ApiResponse(responseCode = "404", description = "O registro escolhido não existe Sw")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") int id) {
        boolean serviceResponse = clientService.deleteClient(id);

        if (serviceResponse) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleção bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }
}
