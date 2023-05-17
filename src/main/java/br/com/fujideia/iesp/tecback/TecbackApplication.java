package br.com.fujideia.iesp.tecback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Sistema TechBack - UNIESP",
		version = "1.0.0"))
public class TecbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TecbackApplication.class, args);
	}

}
