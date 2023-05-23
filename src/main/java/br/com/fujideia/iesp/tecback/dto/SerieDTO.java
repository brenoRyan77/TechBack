package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SerieDTO {
	@NotBlank(message = "O título da série é obrigatório.")
	private String titulo;

	@NotBlank(message = "O número da temporada é obrigatório.")
	private String temporada;

	@NotBlank(message = "O número do episódio é obrigatório.")
	private String episódio;

	@NotBlank(message = "A sinopse é obrigatório.")
	private String sinopse;

	@NotNull
	private Long genero;
	
	@NotBlank(message = "Classificação indicativa é obrigatório")
	private String classificacaoIndicativa;
}
