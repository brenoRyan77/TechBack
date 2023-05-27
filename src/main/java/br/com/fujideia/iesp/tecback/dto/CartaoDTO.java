package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartaoDTO {

	@NotBlank(message = "Número do cartão é obrigatório.")
	@Size(max = 16, message = "O tamanho máximo para o número do cartão é de 16 dígitos.")
	private String numCartao;
	
	@NotBlank(message = "Validade do cartão é obrigatório.")
	@Pattern(regexp = "^(0[1-9]|1[0-2])\\/[0-9]{2}$", message = "A validade do cartão deve ser informada no formato mm/yy.")
	private String validadeCartao;
	
	@NotBlank(message = "CVV do cartão é obrigatório.")
	@Size(max = 3, message = "O código de segurança do cartão deve ter no máximo 3 caracteres.")
	private String codSeguranca;
	
	@NotBlank(message = "Nome do titular do cartão é obrigatório.")
	@Pattern(regexp = "^[A-Za-z\\s]+$", message = "O campo 'nome' deve conter apenas letras e espaços")
	private String titularNome;
	
	@NotBlank(message = "CPF do titular do cartão é obrigatório.")
	@Size(max = 14, message = "O tamanho máximo do CPF é de 14 dígitos.")
	private String cpf;
}
