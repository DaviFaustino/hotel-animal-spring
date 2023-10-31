package com.spring.animal.hotel.spring.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record BookingDto(@NotNull @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Timestamp date_check_in_bo, 
                        @NotNull @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Timestamp date_check_out_bo, 
                        @NotNull int cost_bo, 
                        @NotNull int id_client_bo) {
    
}
