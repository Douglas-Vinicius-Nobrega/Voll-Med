package com.med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

// classe de agendamento de consultas

import org.springframework.stereotype.Service;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.domain.medico.MedicoRepository;
import com.med.voll.api.domain.paciente.PacienteRepository;

@Service 
public class AgendaDeConsultas {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacinteRepository;

	@Autowired
	private ConsultaRepository consultaRepositoy;
	
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		
		if(!pacinteRepository.existsById(dados.idPaciente())) { 
			throw new ValidacaoException("Id do paciente informado não existe!");
		}
		
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) { 
			throw new ValidacaoException("Id do médico informado não existe!");
		}
		
		validadores.forEach(v -> v.validar(dados)); 
		
		var paciente = pacinteRepository.getReferenceById(dados.idPaciente());
		var medico = escolherMedicoAleatorio(dados);
		if(medico == null) {
			throw new ValidacaoException("Não existe médico disponível nessa data");
		}
		
		var consulta = new Consulta(null, medico, paciente, dados.data()); 
		consultaRepositoy.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta); 
	}

	private Medico escolherMedicoAleatorio(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) { 
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade obrigatória quando o médico não for escolhido");
		}
		
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}
}
