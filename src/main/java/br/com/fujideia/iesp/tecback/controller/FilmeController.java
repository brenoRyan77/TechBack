package br.com.fujideia.iesp.tecback.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fujideia.iesp.tecback.dto.EntityErrorDTO;
import br.com.fujideia.iesp.tecback.dto.FilmeDTO;
import br.com.fujideia.iesp.tecback.entities.Filme;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.service.FilmeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/filme")
@Tag(description = "Responsável por todas as operações relacionadas a Filmes", name = "Filme")
public class FilmeController {

    @Autowired
    private FilmeService service;
    
    @Autowired
    private Validator validator;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @NotNull FilmeDTO filme){
    	
    	Set<ConstraintViolation<FilmeDTO>> violationSet = validator.validate(filme);
    	
    	if (!violationSet.isEmpty()) {
            EntityErrorDTO entityErrorDTO = 
            		EntityErrorDTO.createFromValidation(violationSet);
            return entityErrorDTO.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
        }

		try {
			service.salvar(filme);
			return ResponseEntity.status(HttpStatus.CREATED).build();
			
		} catch (ApplicationServiceException e) {
			return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathParam("id") Integer id,
    		@RequestBody FilmeDTO filmeDTO, BindingResult bindingResult){
    	
    	Set<ConstraintViolation<FilmeDTO>> violationSet = validator.validate(filmeDTO);
    	
    	if (!violationSet.isEmpty()) {
            EntityErrorDTO entityErrorDTO = 
            		EntityErrorDTO.createFromValidation(violationSet);
            return entityErrorDTO.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
            
        }
        try {
			service.alterar(id, filmeDTO);
			return ResponseEntity.noContent().build();
		} catch (ApplicationServiceException e) {
			return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
    }

    @GetMapping
    public ResponseEntity<List<Filme>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> consultar(@PathVariable("id") Integer id) 
    		throws ApplicationServiceException{
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Integer id){
        try {
        	service.excluir(id);
        	return ResponseEntity.noContent().build();
        }catch (ApplicationServiceException e) {
        	return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
    }


}
