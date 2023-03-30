package com.med.voll.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.voll.api.medico.DadosCadastroMedico;


@RestController
@RequestMapping("/medicos")
public class MeidcoController {

	@PostMapping // requisição do tipo POST
	public void cadastrarMedico(@RequestBody DadosCadastroMedico dados) {
			System.out.println(dados);
	}
	
}
