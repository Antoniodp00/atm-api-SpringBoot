package com.dam.adp.atmapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "API del Sistema de Cajeros (ATM)",
                version = "1.0.0",
                description = "Documentación interactiva de los endpoints para la gestión de averías, repuestos y transacciones de la red de cajeros."
        )
)
public class AtmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmApiApplication.class, args);
    }

}
