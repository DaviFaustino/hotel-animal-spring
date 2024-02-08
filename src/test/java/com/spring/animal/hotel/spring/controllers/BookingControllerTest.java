package com.spring.animal.hotel.spring.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Timestamp;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.animal.hotel.spring.models.BookingModel;
import com.spring.animal.hotel.spring.models.ClientModel;
import com.spring.animal.hotel.spring.services.BookingService;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private BookingModel booking;
    private ClientModel client;

    @BeforeEach
    void setup() {
        client = new ClientModel(1, "Davi Calazans", "(66) 77777-7777");

        booking = new BookingModel(UUID.nameUUIDFromBytes(new byte[1]),
                                    new Timestamp(1705953600),
                                    new Timestamp(1706953600),
                                    1000,
                                    client);
    }

    @Test
    @DisplayName("Deve retornar uma reserva com sucesso")
    void testGetOneBookingCase1() throws Exception {
        when(bookingService.getOneBooking(any(UUID.class))).thenReturn(booking);
        
        mockMvc.perform(get("/bookings/{id}", UUID.nameUUIDFromBytes(new byte[1]).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_bo", is(booking.getId_bo().toString())))
                .andExpect(jsonPath("$.date_check_in_bo", is("1970-01-20T17:52:33.600+00:00")))
                .andExpect(jsonPath("$.date_check_out_bo", is("1970-01-20T18:09:13.600+00:00")))
                .andExpect(jsonPath("$.cost_bo", is(booking.getCost_bo())));

        verify(bookingService).getOneBooking(any());
        verifyNoMoreInteractions(bookingService);
    }

    @Test
    @DisplayName("Deve falhar retornando mensagem de erro")
    void testGetOneBookingCase2() throws Exception {
        when(bookingService.getOneBooking(any(UUID.class))).thenReturn("O registro não foi encontrado.");
        
        mockMvc.perform(get("/bookings/{id}", UUID.nameUUIDFromBytes(new byte[1]).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().string("O registro não foi encontrado."));

        verify(bookingService).getOneBooking(any());
        verifyNoMoreInteractions(bookingService);
    }

    @Test
    @DisplayName("Deve aplicar desconto na reserva com sucesso")
    void testApplyDiscountBookingCase1() throws Exception {
        when(bookingService.applyDiscountBooking(any())).thenReturn(booking);

        mockMvc.perform(patch("/bookings/apply-discount/{id}", UUID.nameUUIDFromBytes(new byte[1]).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_bo", is(booking.getId_bo().toString())))
                .andExpect(jsonPath("$.date_check_in_bo", is("1970-01-20T17:52:33.600+00:00")))
                .andExpect(jsonPath("$.date_check_out_bo", is("1970-01-20T18:09:13.600+00:00")))
                .andExpect(jsonPath("$.cost_bo", is(booking.getCost_bo())));
                
        verify(bookingService).applyDiscountBooking(any());
        verifyNoMoreInteractions(bookingService);
    }

}
