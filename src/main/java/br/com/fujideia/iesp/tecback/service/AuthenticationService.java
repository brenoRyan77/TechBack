package br.com.fujideia.iesp.tecback.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.entities.Usuario;
import br.com.fujideia.iesp.tecback.repository.UsuarioAuthRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UsuarioAuthRepository  usuarioAuthRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<Usuario> optionalUsuario = usuarioAuthRepository.findByLogin(username);
	    Usuario usuario = optionalUsuario.orElseThrow(() 
	    		-> new UsernameNotFoundException("Usuário não encontrado: " + username));
	    return usuario;
	}
}
