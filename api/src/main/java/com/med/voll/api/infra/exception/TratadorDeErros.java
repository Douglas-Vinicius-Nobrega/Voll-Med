// Classe responsável por isolar o tratamentos de erros da API

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

	// tratador de erro 404
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	// tratador de erros de dados invalidos, erro 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	// recebendo o erro como parametro
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
		// lista com cada um dos erros
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	@ExceptionHandler(ValidacaoException.class)
	// recebendo o erro como parametro
	public ResponseEntity tratarErroRegraDeNegócio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
		
		//Se houve um erro nas regras de negócio, retorna um erro 400
	}
	
	// criando um record iterno
	private record DadosErroValidacao(String campo, String menssagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}
