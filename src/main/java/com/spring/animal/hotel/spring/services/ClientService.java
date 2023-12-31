package com.spring.animal.hotel.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.animal.hotel.spring.models.BookingModel;
import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.repositories.BookingRepository;
import com.spring.animal.hotel.spring.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BookingRepository bookingRepository;

    public ClientModel saveClient(ClientDto clientDto) {
        ClientModel clientModel = new ClientModel(clientDto, -1);

        return clientRepository.save(clientModel);
    }

    public List<ClientModel> getAllClients() {
        return clientRepository.findAll();
    }

    public Object getOneClient(int id) {
        Optional<ClientModel> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }
        
        return "O registro não foi encontrado.";
    }

    public Integer getTotalHostingsCost(int id) {
        List<BookingModel> bookings = bookingRepository.findByClientModelId(id);
        int bookingsCostSum = 0;
        
        for (BookingModel boks: bookings) {
            bookingsCostSum += boks.getCost_bo();
        }

        return bookingsCostSum;
    }

    public Object updateClient(int id, ClientDto clientDto) {
        if (clientRepository.existsById(id)) {
            ClientModel clientModel = new ClientModel(clientDto, id);
            
            return clientRepository.save(clientModel);
        }

        return "O registro escolhido não existe.";
    }

    public boolean deleteClient(int id) {
        Optional<ClientModel> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            clientRepository.delete(optionalClient.get());

            return true;
        }

        return false;
    }
}
