package br.com.fujideia.iesp.tecback.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fujideia.iesp.tecback.dto.EntityErrorDTO;
import br.com.fujideia.iesp.tecback.dto.GeneroDTO;
import br.com.fujideia.iesp.tecback.entities.Genero;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.service.GeneroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

@RequestMapping("/genero")
@RestController
@Tag(description = "Responsável por todas as operações relacionadas a Gênero", name = "Gênero")
public class GeneroController {

	@Autowired
	GeneroService service;
	
	@Autowired
	private Validator validator;
	 
	@GetMapping
	public ResponseEntity<List<Genero>> listar(){
		List<Genero> lista = service.listarGenero();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
	public ResponseEntity<?> inclur(@RequestBody @NotNull GeneroDTO dto){
		
		Set<ConstraintViolation<GeneroDTO>> violationSet = validator.validate(dto);
    	
    	if (!violationSet.isEmpty()) {
            EntityErrorDTO entityErrorDTO = 
            		EntityErrorDTO.createFromValidation(violationSet);
            return entityErrorDTO.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    	
    	try {
    		service.incluirGenero(dto);
    		return ResponseEntity.status(HttpStatus.CREATED).build();
    	}catch (ApplicationServiceException e) {
    		return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable("id") Long id, @NotNull GeneroDTO dto){
		
		Set<ConstraintViolation<GeneroDTO>> violationSet = validator.validate(dto);
    	
    	if (!violationSet.isEmpty()) {
            EntityErrorDTO entityErrorDTO = 
            		EntityErrorDTO.createFromValidation(violationSet);
            return entityErrorDTO.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    	
    	try {
    		service.alterarGenero(id, dto);
    		return ResponseEntity.noContent().build();
    	}catch (ApplicationServiceException e) {
    		return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id")
	@NotNull(message = "Parâmetro obrigatório") Long id){
		
		Set<ConstraintViolation<Long>> violationSet = validator.validate(id);
		
		if (!violationSet.isEmpty()) {
			EntityErrorDTO entityErrorDTO = 
					EntityErrorDTO.createFromValidation(violationSet);
			return entityErrorDTO.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
	    	
			service.deletarGenero(id);
			return ResponseEntity.noContent().build();
		}catch (ApplicationServiceException e) {
			return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
	}
}
