package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GeneroDTO {
	
	@NotBlank(message = "A descrição do gênero é obrigatório.")
	@Size(min = 3, max = 100, message = "A descrição precisa ter entre 3 a 100 caracteres.")
	private String descricao;

}
