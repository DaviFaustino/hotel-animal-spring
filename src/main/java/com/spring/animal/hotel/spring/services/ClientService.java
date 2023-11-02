package com.spring.animal.hotel.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;

    public ClientModel saveClient(ClientDto clientDto) {
        ClientModel clientModel = new ClientModel(clientDto, -1);

        return repository.save(clientModel);
    }

    public List<ClientModel> getAllClients() {
        return repository.findAll();
    }

    public Object getOneClient(int id) {
        Optional<ClientModel> optionalClient = repository.findById(id);

        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }
        
        return "O registro não foi encontrado.";
    }

    public Object updateClient(int id, ClientDto clientDto) {
        if (repository.existsById(id)) {
            ClientModel clientModel = new ClientModel(clientDto, id);
            
            return repository.save(clientModel);
        }

        return "O registro escolhido não existe.";
    }

    public boolean deleteClient(int id) {
        Optional<ClientModel> optionalClient = repository.findById(id);

        if (optionalClient.isPresent()) {
            repository.delete(optionalClient.get());

            return true;
        }

        return false;
    }
}
