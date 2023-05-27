package br.com.fujideia.iesp.tecback.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolation;

public class EntityErrorDTO {

	public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

	private String message;
	private Collection<EntityFieldErroDTO> errors;

	public EntityErrorDTO(String message, Collection<EntityFieldErroDTO> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<EntityFieldErroDTO> getErrors() {
		return errors;
	}

	public void setErrors(Collection<EntityFieldErroDTO> errors) {
		this.errors = errors;
	}

	public static int getUnprocessableEntityStatus() {
		return UNPROCESSABLE_ENTITY_STATUS;
	}

	public static <T> EntityErrorDTO createFromValidation(Set<ConstraintViolation<T>> violations) {
		List<EntityFieldErroDTO> errors = violations.stream().map(
				violation -> new EntityFieldErroDTO(violation.getPropertyPath().toString(), violation.getMessage()))
				.collect(Collectors.toList());

		String message;
		if (errors.isEmpty()) {
			message = "Erro de Validação";
		} else {
			message = errors.get(0).getMessage();
		}

		return new EntityErrorDTO(message, errors);
	}

	public static EntityErrorDTO createFromException(String message) {
		EntityFieldErroDTO error = new EntityFieldErroDTO("field", message);
		Collection<EntityFieldErroDTO> errors = new ArrayList<>();
		errors.add(error);

		return new EntityErrorDTO(message, errors);
	}

	public ResponseEntity<EntityErrorDTO> withStatusCode(HttpStatus status) {
		return ResponseEntity.status(status).body(this);
	}
}
