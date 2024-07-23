package com.api.equipo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class EquipoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipoApplication.class, args);
	}

}
