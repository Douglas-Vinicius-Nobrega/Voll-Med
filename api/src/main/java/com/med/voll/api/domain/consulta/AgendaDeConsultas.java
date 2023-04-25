package com.med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;

// classe de agendamento de consultas

import org.springframework.stereotype.Service;

import com.med.voll.api.domain.ValidacaoException;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.domain.medico.MedicoRepository;
import com.med.voll.api.domain.paciente.PacienteRepository;

@Service // Executa regras de negócio e validações da aplicação
public class AgendaDeConsultas {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacinteRepository;

	@Autowired
	private ConsultaRepository consultaRepositoy;
	
	public void agendar(DadosAgendamentoConsulta dados) {
		// aqui vamos colocar as validações
		
		if(!pacinteRepository.existsById(dados.idPaciente())) { // se n existir o id no banco de dados
			throw new ValidacaoException("Id do paciente informado não existe!");
		}
		
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) { // se o id do médico for diferente de nulo e se não existir
			throw new ValidacaoException("Id do médico informado não existe!");
		}
		
		var paciente = pacinteRepository.getReferenceById(dados.idPaciente()); // buscando id
		var medico = escolherMedicoAleatorio(dados);
		var consulta = new Consulta(null, medico, paciente, dados.data()); // objeto do tipo consulta
		consultaRepositoy.save(consulta);
	}

	private Medico escolherMedicoAleatorio(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) { // diferente de nulo
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade obrigatória quando o médico não for escolhido");
		}
		
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}
}
