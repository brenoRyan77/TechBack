package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FilmeDTO {

	@NotBlank(message = "O título do filme é obrigatório.")
	@Size(min = 3, max = 255, message = "O título deve ter entre 3 e 255 caracteres.")
	private String titulo;
	
	@NotBlank(message = "A sinopse é obrigatório.")
	@Size(min = 3, max = 255, message = "A sinopse deve ter entre 3 e 255 caracteres.")
	private String sinopse;
	
	@NotNull
	private Long idGenero;
}
