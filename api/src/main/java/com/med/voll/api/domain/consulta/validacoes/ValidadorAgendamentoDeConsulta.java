package com.med.voll.api.domain.consulta.validacoes;

import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

	void validar(DadosAgendamentoConsulta dados);
	
	//Aqui não precisa do public porque é implícito que todos os métodos de uma interface são públicos.
}
