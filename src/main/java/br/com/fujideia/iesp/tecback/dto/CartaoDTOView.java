package br.com.fujideia.iesp.tecback.dto;

import lombok.Data;

@Data
public class CartaoDTOView {

	private String numCartao;
	
	private String validadeCartao;
	
	private String codSeguranca;
	
	private String titularNome;
	
	private String cpf;
}
