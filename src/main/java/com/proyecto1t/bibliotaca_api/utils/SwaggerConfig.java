package com.proyecto1t.bibliotaca_api.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("API para una biblioteca");

        Contact myContact = new Contact();
        myContact.setName("Joaquin Castro Salas");

        Info information = new Info()
                .title("API BIBLIOTECA")
                .version("1.0")
                .description("API para una biblioteca")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
