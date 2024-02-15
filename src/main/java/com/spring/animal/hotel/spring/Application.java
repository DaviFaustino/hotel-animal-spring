package com.spring.animal.hotel.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hotel API", version = "1", description = "API do Hotel Animal para ser consumida pelo front-end."))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
