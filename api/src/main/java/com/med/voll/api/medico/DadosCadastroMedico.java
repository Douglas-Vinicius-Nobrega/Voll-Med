package com.med.voll.api.medico;

import com.med.voll.api.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record DadosCadastroMedico(
		
		@NotBlank // verifica se está nulo ou vázio
		String nome, 
		
		@NotBlank
		@Email
		String email,
		
		@NotBlank
		@Pattern(regexp = "\\d{4,6}") // expressão regular
		String crm, 
		
		@NotNull
		Especialidade especialidade, 
		
		@NotNull
		@Valid // validar esse objeto dentro dos meus atributos de cadastro
		DadosEndereco endereco) {}
