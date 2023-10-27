package com.spring.animal.hotel.spring.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientDto(@NotBlank String name_cl, @NotNull String phone_cl) {
    
}
