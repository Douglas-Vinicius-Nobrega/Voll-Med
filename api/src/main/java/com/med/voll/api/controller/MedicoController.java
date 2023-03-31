package com.med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.med.voll.api.medico.DadosCadastroMedico;
import com.med.voll.api.medico.DadosListagemMedico;
import com.med.voll.api.medico.Medico;
import com.med.voll.api.medico.MedicoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;

	@PostMapping // requisição do tipo POST
	@Transactional // metodo de salvar, atualizar ou excluir algo do db
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		medicoRepository.save(new Medico(dados));
	}
	
//	@GetMapping // devolver uma informação
//	// além de devolver a lista de dados, o Page vai devolver dados da paginação
//	// pois quenrendo listar a cada 10 médicos por vez e em ordem alfabética
//	public List<DadosListagemMedico> listar(Pageable paginacao) {
//		// convertendo de uma lista de médicos, para uma lista de DadosListagemMedico
//		// Pois estamos trazendo algumas informações de medicos e não todas.
//		return medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
//	}
	
	@GetMapping // devolver uma informação
	// além de devolver a lista de dados, o Page vai devolver dados da paginação
	// pois quenrendo listar a cada 10 médicos por vez e em ordem alfabética
	public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable paginacao) {
		// convertendo de uma lista de médicos, para uma lista de DadosListagemMedico
		// Pois estamos trazendo algumas informações de medicos e não todas.
		return medicoRepository.findAll(paginacao).map(DadosListagemMedico::new);
	}
	
}
