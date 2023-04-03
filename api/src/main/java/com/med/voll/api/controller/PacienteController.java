package com.med.voll.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.voll.api.paciente.DadosAtualizacaoPaciente;
import com.med.voll.api.paciente.DadosCadastroPaciente;
import com.med.voll.api.paciente.DadosListagemPaciente;
import com.med.voll.api.paciente.Paciente;
import com.med.voll.api.paciente.PacienteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private PacienteRepository pacienteRepository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
		pacienteRepository.save(new Paciente(dados));
	}
	
	@GetMapping
	public Page<DadosListagemPaciente> listagemPaciente(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable pageable) {
		return pacienteRepository.findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);
	}
	
	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
		var paciente = pacienteRepository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void remover(@PathVariable Long id) {
		var paciente = pacienteRepository.getReferenceById(id);
		paciente.inativar();
	}
}
