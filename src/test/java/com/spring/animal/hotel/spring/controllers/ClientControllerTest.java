package com.spring.animal.hotel.spring.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.services.ClientService;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc MockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("Deve criar um criar um cliente com sucesso.")
    void testSaveClient() {
        ClientDto clientDto = new ClientDto("Nunes", "(88) 88888888");
        ClientModel savedClient = new ClientModel(clientDto, 1);

        when(clientService.saveClient(any())).thenReturn(savedClient);
    }

    @Test
    void testGetAllClients() {

    }

    @Test
    void testGetOneClient() {

    }

    @Test
    void testGetTotalHostingsCost() {

    }

    @Test
    void testUpdateCliente() {

    }

    @Test
    void testDeleteClient() {

    }
}
