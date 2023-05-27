package br.com.fujideia.iesp.tecback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDTO {

	@NotBlank(message = "Proprietário da mensagem é obrigatório.")
	private String ownerRef;

	@NotBlank(message = "Remetente é obrigatório")
	@Email
	private String emailFrom;

	@NotBlank(message = "Destinatário é obrigatório")
	@Email
	private String emailTo;

	@NotBlank(message = "Assunto é obrigatório.")
	private String subject;

	@NotBlank(message = "Corpo do email é obrigatório.")
	private String text;
}
