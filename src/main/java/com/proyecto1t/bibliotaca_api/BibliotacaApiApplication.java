package com.proyecto1t.bibliotaca_api;

import com.proyecto1t.bibliotaca_api.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BibliotacaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotacaApiApplication.class, args);
	}

}
