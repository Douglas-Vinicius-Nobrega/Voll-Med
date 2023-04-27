package com.med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.ConsultaRepository;
import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import com.med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {
	
	@Autowired
	private ConsultaRepository repository;

	public void validar(DadosAgendamentoConsulta dados) {
		var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
		
		if(medicoPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
		}
	}
}
