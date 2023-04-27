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

@Service // Executa regras de negócio e validações da aplicação
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
		// aqui vamos colocar as validações
		
		if(!pacinteRepository.existsById(dados.idPaciente())) { // se n existir o id no banco de dados
			throw new ValidacaoException("Id do paciente informado não existe!");
		}
		
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) { // se o id do médico for diferente de nulo e se não existir
			throw new ValidacaoException("Id do médico informado não existe!");
		}
		
		// padrão de projeto chamado Designer Patters > Strategy 
		validadores.forEach(v -> v.validar(dados)); // vai percorrer todos os validadores e fazer as verificações
		// SOLID - estamos utilizando 3 principios SOLID
		// s - Single Responsability principle - cada classe tem uma única responsabilidade
		// o - Open-Cliosed-Principle - Consigo criar novos validadores sem mexer aqui
		// d - Dependency Inversion Principle - Minha classe não depende dos validadores
		
		var paciente = pacinteRepository.getReferenceById(dados.idPaciente()); // buscando id
		var medico = escolherMedicoAleatorio(dados);
		if(medico == null) {
			throw new ValidacaoException("Não existe médico disponível nessa data");
		}
		
		var consulta = new Consulta(null, medico, paciente, dados.data()); // objeto do tipo consulta
		consultaRepositoy.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta); // retornando esse constructor de DTO
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
