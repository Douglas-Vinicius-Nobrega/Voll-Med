package com.med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.med.voll.api.domain.usuario.Usuario;

@Service // classe dee serviço
public class TokenService {
	
	@Value("${api.security.token.secret}") 
	private String secret; 

	public String gerarToken(Usuario usuario) {
		
		
		try {
		    var algoritmo = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("API Voll.med") 
		        .withSubject(usuario.getLogin())
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritmo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token jwt", exception);
		}
		
	}
	
	// validação do token
	public String getSubject(String tokenJWT) {
        try {
                var algoritmo = Algorithm.HMAC256(secret);
                return JWT.require(algoritmo)
                	.withIssuer("API Voll.med")
                	.build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
        	throw new RuntimeException("Token JWT inválido ou expirado!" + exception.getMessage());
        }
}

	private Instant dataExpiracao() {
	
		return LocalDateTime.now().plusHours(100).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
