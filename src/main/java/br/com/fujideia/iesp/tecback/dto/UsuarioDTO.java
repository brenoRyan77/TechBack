package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {

	@NotBlank(message = "O nome é obrigatório.")
	@Size(min = 3, max = 100, message = "O nome precisa ter entre 3 a 100 caracteres.")
    private String nomeCompleto;
	
	@NotBlank(message = "A data de nascimento é obrigatório.")
	@Size(min = 7, max = 8, message = "A data de nascimento deve ser mandado no formado dd/MM/yyyy.")
    private String dataNasc;
	
	@NotBlank(message = "O email é nascimento é obrigatório.")
	@Email
    private String email;
	
	@NotBlank(message = "A senha é obrigatório.")
	@Size(min = 3, max = 100, message = "A senha precisa ter no mínimo 3 dígitos.")
    private String senha;
	
	@Valid
    private CartaoDTO cartao;
}
