package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {

	@NotBlank(message = "O nome é obrigatório.")
    private String nomeCompleto;
	@NotBlank(message = "A data de nascimento é obrigatório.")
    private String dataNasc;
	@NotBlank(message = "O email é nascimento é obrigatório.")
    private String email;
	@NotBlank(message = "O Login é obrigatório.")
	private String login;
	@NotBlank(message = "A senha é obrigatório.")
    private String senha;
	@Valid
    private CartaoDTO cartao;
}
