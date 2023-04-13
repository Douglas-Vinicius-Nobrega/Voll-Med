package com.med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.med.voll.api.domain.usuario.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// classe de segurança

@Component // classe/componente genérico
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private UsuarioRepository repository; 

	@Autowired
	private TokenService tokenService;

	// método que  o spring vai chamar, quando esse filtro for executado
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var tokenJWT = recuperarToken(request); // recuperamos o token do cabeçalho
		
		if(tokenJWT != null) { // se tiver cabeçalho faz a verificação
			var subject = tokenService.getSubject(tokenJWT); // validar o token
			
			var usuario = repository.findByLogin(subject); 
			
			// força uma autenticação
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());	
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		
		var authorizationHeader = request.getHeader("Authorization");

		if(authorizationHeader != null) {
			return authorizationHeader.replace("Bearer", "").trim(); // mudar a palavra por vázio
		}
		
		return null;
	}

}
