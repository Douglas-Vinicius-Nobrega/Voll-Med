package com.med.voll.api.domain.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import java.time.Duration;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component 
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {
	
	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		
		var agora = LocalDateTime.now(); 
		
		var diferenteEmMinutos = Duration.between(agora, dataConsulta).toMinutes(); // duração em minutos
		
		if(diferenteEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser agendado com antecedencia minima de 30 mntos");
		}
		
	}

}
