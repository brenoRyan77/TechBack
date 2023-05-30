package br.com.fujideia.iesp.tecback.dto;

import lombok.Data;

@Data
public class UsuarioDTOView {

    private String nomeCompleto;
	
    private String dataNasc;
	
    private String email;
	
	private String login;
	
    private String senha;
	
    private CartaoDTOView cartao;
}
