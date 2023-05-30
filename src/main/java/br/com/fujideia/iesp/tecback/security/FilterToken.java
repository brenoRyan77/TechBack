package br.com.fujideia.iesp.tecback.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fujideia.iesp.tecback.repository.UsuarioAuthRepository;
import br.com.fujideia.iesp.tecback.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioAuthRepository usuarioRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token;
		
		var authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null  && authorizationHeader.startsWith("Bearer ")) {
			
			token = authorizationHeader.substring(7);
			
			var subject = tokenService.getSubject(token);
			
			var usuario = usuarioRepository.findByLogin(subject);
			
			var authentication = new UsernamePasswordAuthenticationToken(
					usuario, null, usuario.get().getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

}
