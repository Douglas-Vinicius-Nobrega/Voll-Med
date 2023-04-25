package com.med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.voll.api.domain.consulta.AgendaDeConsultas;
import com.med.voll.api.domain.consulta.ConsultaRepository;
import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import com.med.voll.api.domain.consulta.DadosDetalhamentoConsulta;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

	@Autowired
	private AgendaDeConsultas agenda;
	
	ConsultaRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity agendar(@Valid @RequestBody DadosAgendamentoConsulta dados) {
		
		agenda.agendar(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
	}
}
