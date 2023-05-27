package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {

	@NotBlank(message = "O nome é obrigatório.")
	@Size(min = 3, max = 100, message = "O nome precisa ter entre 3 a 100 caracteres.")
	@Pattern(regexp = "^[A-Za-z\\s]+$", message = "O campo 'nome' deve conter apenas letras e espaços")
    private String nomeCompleto;
	
	@NotBlank(message = "A data de nascimento é obrigatório.")
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "A data de nascimento deve ser informada no formato dd/MM/yyyy.")
    private String dataNasc;
	
	@NotBlank(message = "O email é obrigatório.")
	@Email(message = "Digite um email válido.")
    private String email;
	
	@NotBlank(message = "O login é obrigatório.")
	@Size(min = 3, max = 100, message = "O nome precisa ter entre 3 a 100 caracteres.")
	@Pattern(regexp = "^[A-Za-z\\s]+$", message = "O campo 'nome' deve conter apenas letras e espaços")
	private String login;
	
	//@NotBlank(message = "A senha é obrigatório.")
	@Size(min = 3, max = 100, message = "A senha precisa ter no mínimo 3 dígitos.")
    private String senha;
	
	@Valid
    private CartaoDTO cartao;
}
