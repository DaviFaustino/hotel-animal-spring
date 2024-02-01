package com.spring.animal.hotel.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.spring.animal.hotel.spring.models.BookingModel;
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

    ClientModel client;
    ClientDto clientDto;

    @BeforeEach
    void setup() {
        client = new ClientModel(1, "Agata Borralheira", "(00) 99999-9999");
        clientDto = new ClientDto("Agata Borralheira", "(00) 99999-9999");
    }

    @Test
    @DisplayName("Deve salvar um Client no banco de dados corretamente.")
    void testSaveClient() {
        ClientModel savedClientInRepository = new ClientModel(clientDto, 1);

        when(clientRepository.save(any())).thenReturn(savedClientInRepository);

        ClientModel savedClient = clientService.saveClient(clientDto);

        assertEquals("Agata Borralheira", savedClient.getName_cl());
        assertEquals("(00) 99999-9999", savedClient.getPhone_cl());

        verify(clientRepository).save(any(ClientModel.class));
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os clientes")
    void testGetAllClients() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));

        List<ClientModel> clientList = clientService.getAllClients();

        assertEquals(Arrays.asList(client), clientList);

        verify(clientRepository).findAll();
    }

    @Test
    @DisplayName("Deve retorna um cliente pelo id")
    void testGetOneClient() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Object result = clientService.getOneClient(1);

        assertEquals(client.getClass(), result.getClass());

        verify(clientRepository).findById(1);
    }

    @Test
    @DisplayName("Deve retornar o custo total das reservas de um cliente")
    void testGetTotalHostingsCost() {
        BookingModel booking1 = new BookingModel(UUID.nameUUIDFromBytes(new byte[1]),
                                                new Timestamp(1705953600),
                                                new Timestamp(1706953600),
                                                1000,
                                                client);
        BookingModel booking2 = new BookingModel(UUID.nameUUIDFromBytes(new byte[2]),
                                                new Timestamp(1705953600),
                                                new Timestamp(1706953600),
                                                2000,
                                                client);

        when(bookingRepository.findByClientModelId(1)).thenReturn(List.of(booking1, booking2));

        int serviceResponse = clientService.getTotalHostingsCost(1);

        assertEquals(serviceResponse, 3000);
        
        verify(bookingRepository).findByClientModelId(1);
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    @DisplayName("Deve atualizar o cliente com sucesso")
    void testUpdateClient() {
        ClientModel client = new ClientModel(clientDto, 1);

        when(clientRepository.existsById(1)).thenReturn(true);
        when(clientRepository.save(any(ClientModel.class))).thenReturn(client);

        Object serviceResponse = clientService.updateClient(1, clientDto);

        assertEquals(serviceResponse, client);

        verify(clientRepository).existsById(1);
        verify(clientRepository).save(any());
    }

    @Test
    @DisplayName("Deve deletar o cliente com sucesso")
    void testDeleteClient() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(any());

        boolean serviceResponse = clientService.deleteClient(1);

        assertEquals(serviceResponse, true);

        verify(clientRepository).findById(1);
        verify(clientRepository).delete(any());
    }
}
