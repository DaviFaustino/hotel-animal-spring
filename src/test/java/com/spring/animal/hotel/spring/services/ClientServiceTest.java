package com.spring.animal.hotel.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.repositories.BookingRepository;
import com.spring.animal.hotel.spring.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    ClientRepository clientRepository;

    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    @DisplayName("Deve salvar um Client no banco de dados corretamente.")
    void testSaveClient() {
        ClientDto clientDto = new ClientDto("Agata Borralheira", "(00) 99999-9999");
        ClientModel savedClientInRepository = new ClientModel(clientDto, 1);

        when(clientRepository.save(any())).thenReturn(savedClientInRepository);

        ClientModel savedClient = clientService.saveClient(clientDto);

        assertEquals("Agata Borralheira", savedClient.getName_cl());
        assertEquals("(00) 99999-9999", savedClient.getPhone_cl());

        verify(clientRepository).save(any(ClientModel.class));
    }

    @Test
    void testGetAllClients() {
        ClientModel client = new ClientModel(1, "Agata Borralheira", "(00) 99999-9999");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));

        List<ClientModel> clientList = clientService.getAllClients();

        assertEquals(Arrays.asList(client), clientList);

        verify(clientRepository).findAll();
    }

    @Test
    void testGetOneClient() {
        ClientModel client = new ClientModel(1, "Agata Borralheira", "(00) 99999-9999");
        
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Object result = clientService.getOneClient(1);

        assertEquals(client.getClass(), result.getClass());

        verify(clientRepository).findById(1);
    }

    @Test
    void testUpdateClient() {

    }

    @Test
    void testDeleteClient() {

    }

    @Test
    void testGetTotalHostingsCost() {

    }
}
