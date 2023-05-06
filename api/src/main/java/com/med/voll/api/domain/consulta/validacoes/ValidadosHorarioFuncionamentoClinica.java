package com.med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadosHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {
	
	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY); // verificando se a data é igual a domingo. 
		var AntesDaAberturaDaClinica = dataConsulta.getHour() < 7; 
		var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
		
		if(domingo || AntesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
		}
	}
}
