package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilmeDTO {

	@NotBlank(message = "O título do filme é obrigatório.")
	private String titulo;
	
	@NotBlank(message = "A sinopse é obrigatório.")
	private String sinopse;
	
	@Valid
	@NotNull
	private GeneroDTO genero;
}
