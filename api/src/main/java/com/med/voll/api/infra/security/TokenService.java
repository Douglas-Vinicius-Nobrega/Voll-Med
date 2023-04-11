package com.med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.med.voll.api.domain.usuario.Usuario;

// classe de velidação e geração dos tokens

@Service // classe dee serviço
public class TokenService {
	
	@Value("${api.security.token.secret}") // variável de ambiente do properties
	private String secret; 

	public String gerarToken(Usuario usuario) {
		
		// criando um token jwt
		try {
		    var algoritmo = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("API Voll.med") // ferramenta responsável pela geração do token
		        .withSubject(usuario.getLogin()) // Identificando o usuário a qual esse token pertence
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritmo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token jwt", exception);
		}
		
	}

	private Instant dataExpiracao() {
	
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
