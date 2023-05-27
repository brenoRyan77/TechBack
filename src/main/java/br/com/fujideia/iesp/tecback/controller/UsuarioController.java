package br.com.fujideia.iesp.tecback.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import br.com.fujideia.iesp.tecback.dto.Login;
import br.com.fujideia.iesp.tecback.dto.UsuarioDTO;
import br.com.fujideia.iesp.tecback.entities.Usuario;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.service.TokenService;
import br.com.fujideia.iesp.tecback.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/usuario")
@Tag(description = "Responsável por todas as operações relacionadas a Usuários", name = "Usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    
    @Autowired
    private Validator validator;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @NotNull UsuarioDTO user) 
    		throws Exception {

    	Set<ConstraintViolation<UsuarioDTO>> violationSet = validator.validate(user);
    	
    	if (!violationSet.isEmpty()) {
    		EntityErrorDTO entityErrorDTO =  EntityErrorDTO
    				.createFromValidation(violationSet);
    		return entityErrorDTO
					.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
            
        }
    	try {
			service.salvar(user);
			return ResponseEntity.noContent().build();
		} catch (ApplicationServiceException e) {
			return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable("id") Long id,
    		@RequestBody @NotNull UsuarioDTO user, BindingResult bindingResult) 
    				throws ApplicationServiceException {
    	
    	Set<ConstraintViolation<UsuarioDTO>> violationSet = validator.validate(user);
    	
    	if (!violationSet.isEmpty()) {
    		EntityErrorDTO entityErrorDTO =  EntityErrorDTO
    				.createFromValidation(violationSet);
    		return entityErrorDTO
					.withStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
            
        }
        try {
			service.alterar(user, id);
			return ResponseEntity.noContent().build();
		} catch (ApplicationServiceException e) {
			return EntityErrorDTO.createFromException(e.getMessage())
					.withStatusCode(HttpStatus.BAD_REQUEST);
		}
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> consultar (@PathVariable("id") Long id) 
    		throws ApplicationServiceException {
    	
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir (@PathVariable("id") Long id) {
    	 
    	try {
         	service.excluir(id);
         	return ResponseEntity.noContent().build();
         }catch (ApplicationServiceException e) {
         	return EntityErrorDTO.createFromException(e.getMessage())
 					.withStatusCode(HttpStatus.BAD_REQUEST);
 		}
    	
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Login login) {
   
    	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
    			new UsernamePasswordAuthenticationToken(login.login(), login.password());
    	
    	Authentication authenticate = this.authenticationManager
    			.authenticate(usernamePasswordAuthenticationToken);
    	
    	var usuario = (Usuario) authenticate.getPrincipal();
    	
    	return tokenService.gerarToken(usuario);
    }
}
