package br.com.fujideia.iesp.tecback.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fujideia.iesp.tecback.entities.Usuario;

@Service
public class TokenService {

	public String gerarToken(Usuario usuario) {
		return JWT.create()
				.withIssuer("Filme")
				.withSubject(usuario.getLogin())
				.withClaim("id", usuario.getId())
				.withExpiresAt(LocalDateTime.now()
						.plusMinutes(10)
						.toInstant(ZoneOffset.of("-03:00"))
						).sign(Algorithm.HMAC256("tecback"));
	}

}
