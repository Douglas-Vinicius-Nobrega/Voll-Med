package com.med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.med.voll.api.domain.medico.Especialidade;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(
		Long idMedico,
		
		@NotNull
		Long idPaciente,
		
		Especialidade especialidade,
		
		@NotNull
		@Future
		LocalDateTime data
		) {

}
