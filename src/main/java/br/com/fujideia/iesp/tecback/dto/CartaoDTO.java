package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartaoDTO {

	@NotBlank(message = "Número do cartão é obrigatório.")
	@Size(max = 16, message = "O tamanho máximo para o número do cartão é de 16 dígitos.")
	private String numCartao;
	@NotBlank(message = "Validade do cartão é obrigatório.")
	private String validadeCartao;
	@NotBlank(message = "CVV do cartão é obrigatório.")
	private String codSeguranca;
	@NotBlank(message = "Nome do titular do cartão é obrigatório.")
	private String titularNome;
	@NotBlank(message = "CPF do titular do cartão é obrigatório.")
	@Size(max = 14, message = "O tamanho máximo do CPF é de 11 dígitos.")
	private String cpf;
}
