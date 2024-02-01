package com.spring.animal.hotel.spring.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import com.spring.animal.hotel.spring.models.ClientDto;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.services.ClientService;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    private ClientDto clientDto;
    private String dtoAsJson;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() throws JsonProcessingException {
        clientDto = new ClientDto("Alfredo Neves", "(87) 8787-8787");
        dtoAsJson = objectMapper.writeValueAsString(clientDto);
    }

    @Test
    @DisplayName("Deve criar um criar um cliente com sucesso.")
    void testSaveClient() throws Exception {
        ClientModel savedClient = new ClientModel(clientDto, 1);

        when(clientService.saveClient(any())).thenReturn(savedClient);

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name_cl", is("Alfredo Neves")))
                .andExpect(jsonPath("$.phone_cl", is("(87) 8787-8787")));

        verify(clientService).saveClient(any());
        verifyNoMoreInteractions(clientService);
    }

    @Test
    @DisplayName("Deve retornar todos os clientes com sucesso.")
    void testGetAllClients() throws Exception {
        ClientModel client = new ClientModel(clientDto, 1);

        when(clientService.getAllClients()).thenReturn(Arrays.asList(client));

        mockMvc.perform(get("/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientService).getAllClients();
        verifyNoMoreInteractions(clientService);
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
