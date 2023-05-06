

package com.med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.med.voll.api.domain.ValidacaoException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice // tipo de classe é para tratar error
public class TratadorDeErros {

	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity tratarErroRegraDeNegócio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
		
	}
	
	private record DadosErroValidacao(String campo, String menssagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}
