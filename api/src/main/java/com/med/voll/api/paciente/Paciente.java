package com.med.voll.api.paciente;

import com.med.voll.api.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") // Equal e hashcode encima do ID
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	
	@Embedded
	Endereco endereco;
	
	private Boolean ativo;
	
	public Paciente(DadosCadastroPaciente dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.email = dados.email();
		this.cpf = dados.cpf();
		this.telefone = dados.telefone();
		this.endereco = new Endereco(dados.endereco());
	}

	public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dados) {
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		
		if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if(dados.endereco() != null) {
			endereco.atualizarInformacoes(dados.endereco());
		}
	}
	public void inativar() {
		this.ativo = false;
	}
}
