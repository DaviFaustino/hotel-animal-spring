package com.spring.animal.hotel.spring.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

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
    @DisplayName("Deve retornar um cliente com sucesso a partir de um path variable id")
    void testGetOneClient() throws Exception {
        when(clientService.getOneClient(1)).thenReturn(new ClientModel(clientDto, 1));

        mockMvc.perform(get("/clients/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name_cl", is("Alfredo Neves")))
                .andExpect(jsonPath("$.phone_cl", is("(87) 8787-8787")));

        verify(clientService).getOneClient(1);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    @DisplayName("Deve retornar com sucesso o total das reservas que o cliente possui")
    void testGetTotalHostingsCost() throws Exception {
        when(clientService.getTotalHostingsCost(1)).thenReturn(2000);

        mockMvc.perform(get("/clients/total-hostings-cost/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2000"));

        verify(clientService).getTotalHostingsCost(1);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    @DisplayName("Deve atualizar com sucesso os dados de um cliente")
    void testUpdateCliente() throws Exception {
        when(clientService.updateClient(1, clientDto)).thenReturn(new ClientModel(clientDto, 1));

        mockMvc.perform(put("/clients/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name_cl", is("Alfredo Neves")))
                .andExpect(jsonPath("$.phone_cl", is("(87) 8787-8787")));
        
        verify(clientService).updateClient(1, clientDto);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    @DisplayName("Deve deletar um cliente com sucesso pelo id")
    void testDeleteClient() throws Exception {
        when(clientService.deleteClient(1)).thenReturn(true);

        mockMvc.perform(delete("/clients/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleção bem sucedida."));
        
        verify(clientService).deleteClient(1);
        verifyNoMoreInteractions(clientService);
    }
}
