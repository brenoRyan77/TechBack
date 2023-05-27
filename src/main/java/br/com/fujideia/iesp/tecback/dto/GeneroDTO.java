package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GeneroDTO {
	
	@NotBlank(message = "A descrição do gênero é obrigatório.")
	private String descricao;

}
