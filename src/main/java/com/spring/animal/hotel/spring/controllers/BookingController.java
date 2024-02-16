package com.spring.animal.hotel.spring.controllers;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.animal.hotel.spring.models.BookingDto;
import com.spring.animal.hotel.spring.models.BookingModel;
import com.spring.animal.hotel.spring.services.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @Operation(summary = "Salva uma nova reserva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva salva com sucesso"),
        @ApiResponse(responseCode = "404", description = "Id do cliente não encontrado")
    })
    @PostMapping
    public ResponseEntity<Object> saveBooking(@RequestBody @Valid BookingDto bookingDto) {
        Object serviceResponse = bookingService.saveBooking(bookingDto);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }
    
    @Operation(summary = "Retorna todas as reservas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todas as reservas retornadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<BookingModel>> getAllBookings() {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
    }

    @Operation(summary = "Retorna uma reserva pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneBooking(@PathVariable(value = "id") UUID id) {
        Object serviceResponse = bookingService.getOneBooking(id);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .header("Content-Type", "application/json;charset=UTF-8")
                            .body(serviceResponse);
    }

    @Operation(summary = "Atualiza reserva pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookingDto bookingDto) {
        Object serviceResponse = bookingService.updateBooking(bookingDto, id);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
    }

    @Operation(summary = "Aplica desconto na reserva pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Desconto aplicado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @PatchMapping("/apply-discount/{id}")
    public ResponseEntity<Object> applyDiscountBooking(@PathVariable(value = "id") UUID id) {
        Object serviceResponse = bookingService.applyDiscountBooking(id);

        if (serviceResponse.getClass() == BookingModel.class) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .header("Content-Type", "application/json;charset=UTF-8")
                            .body(serviceResponse);
    }

    @Operation(summary = "Apagar uma reserva pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva apagada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable(value = "id") UUID id) {
        if (bookingService.deleteBooking(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleção bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro escolhido não existe.");
    }
}
