package com.med.voll.api.domain.paciente;

import com.med.voll.api.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;

public record DadosAtualizacaoPaciente(
		
		Long id,
		String nome,
		String telefone,
		@Valid
		DadosEndereco endereco
		) {

}
