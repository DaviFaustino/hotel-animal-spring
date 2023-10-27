package com.spring.animal.hotel.spring.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.repositories.ClientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientRepository repository;

    @PostMapping
    public ResponseEntity<ClientModel> saveClient(@RequestBody @Valid ClientDto clientDto) {
        ClientModel clientModel = new ClientModel();
        BeanUtils.copyProperties(clientDto, clientModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(clientModel));
    }
}
