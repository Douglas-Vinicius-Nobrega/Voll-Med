package com.med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;

// classe de configuração

import org.springframework.context.annotation.Bean;

//classe de configurações de segurança

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration // classe de configuração
@EnableWebSecurity // informarmos ao Spring que vamos personalizar as configurações de segurança.
public class SecurityConfigurations {
	
	@Autowired
	private SecurityFilter securityFilter;

	@Bean // que serve exibir o retorno desse método, que estamos devolvendo um objeto
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable() // desabilitando o csrf
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // configuramos a confi para ser STATELES
				.and().authorizeHttpRequests()
				.requestMatchers(HttpMethod.POST, "/login").permitAll() // pode disparar requisição, se estar logado 
				.anyRequest().authenticated() // qualuqer outra requisição sem ser /login, precisa estar autenticado
				.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // chamando nosso filtro
				.build(); // habilitando o stateless
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	// algoritmo de hash de senha
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // classe do próprio spring
	}
	
}
