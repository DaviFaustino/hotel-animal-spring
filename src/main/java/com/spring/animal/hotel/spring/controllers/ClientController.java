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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientModel> saveClient(@RequestBody @Valid ClientDto clientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(clientDto));
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClient(@PathVariable(value = "id") int id) {
        Object serviceResponse = clientService.getOneClient(id);

        if (serviceResponse.getClass() == ClientModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @GetMapping("/total-hostings-cost/{id}")
    public ResponseEntity<Integer> getTotalHostingsCost(@PathVariable(value = "id") int id) {
        Integer serviceResponse = clientService.getTotalHostingsCost(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value = "id") int id, @RequestBody @Valid ClientDto clientDto) {
        Object serviceResponse = clientService.updateClient(id, clientDto);

        if (serviceResponse.getClass() == ClientModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") int id) {
        boolean serviceResponse = clientService.deleteClient(id);

        if (serviceResponse) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleção bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }
}
