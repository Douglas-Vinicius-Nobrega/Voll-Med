package com.med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import com.med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{
	
	@Autowired
	private MedicoRepository repository;

	public void validar(DadosAgendamentoConsulta dados) {
		// escolha do medico opcional
		if(dados.idMedico() == null) {
			return;
		}
		
		var medicoAtivo = repository.findAtivoById(dados.idMedico());
		if(!medicoAtivo) { // se medico não estiver ativo
			throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
		}
	}
}
