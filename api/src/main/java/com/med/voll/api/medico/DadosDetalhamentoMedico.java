package com.med.voll.api.medico;

import com.med.voll.api.endereco.Endereco;

//DTO
public record DadosDetalhamentoMedico(
		
		Long id,
		String nome,
		String email,
		String crm,
		String telefone,
		Especialidade especialidade,
		Endereco endereco
		
		) {

	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), 
			medico.getNome(), 
			medico.getEmail(), 
			medico.getCrm(), 
			medico.getTelefone(),
			medico.getEspecialidade(),
			medico.getEndereco());
	}
}