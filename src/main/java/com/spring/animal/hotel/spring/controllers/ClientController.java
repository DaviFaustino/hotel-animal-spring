package com.spring.animal.hotel.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
import com.spring.animal.hotel.spring.repositories.ClientRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients() {
        List<ClientModel> clientList = repository.findAll();
        if (!clientList.isEmpty()) {
            for (ClientModel client: clientList) {
                int id = client.getId_cl();
                client.add(linkTo(methodOn(ClientController.class).getOneClient(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClient(@PathVariable(value = "id") int id) {
        Optional<ClientModel> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            optionalClient.get().add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("Lista de clientes"));

            return ResponseEntity.status(HttpStatus.OK).body(optionalClient.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro não foi encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value = "id") int id, @RequestBody @Valid ClientDto clientDto) {
        Optional<ClientModel> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            ClientModel clientModel = optionalClient.get();
            BeanUtils.copyProperties(clientDto, clientModel);
            
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(clientModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") int id) {
        Optional<ClientModel> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            repository.delete(optionalClient.get());

            return ResponseEntity.status(HttpStatus.OK).body("Deleção bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }
}
