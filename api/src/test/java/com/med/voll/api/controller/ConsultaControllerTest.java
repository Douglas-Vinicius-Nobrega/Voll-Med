package com.med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.med.voll.api.domain.consulta.AgendaDeConsultas;
import com.med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import com.med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import com.med.voll.api.domain.medico.Especialidade;

@SpringBootTest 
@AutoConfigureMockMvc // injetando o mock
@WithMockUser // anotação usada para não ter que precisar logar para fazer a requisição para teste
@AutoConfigureJsonTesters // anotação para injetar o JacksonTester
class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc; // simula requisições para fim de teste
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson; 

	
	@MockBean
	private AgendaDeConsultas agendaDeConsultas;
	
	
	@Test
	@DisplayName("Deveria devolver codigo http 400 quand informações estão invalidas")
	void agenda_cenario1() throws Exception {
		var response = mvc.perform(post("/consultas")) 
			.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // verificando se o retorno é código 400
	}
	
	// cenario de teste 200
	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informações estão validas")
	void agenda_cenario2() throws Exception {
		var data = LocalDateTime.now().plusHours(1); 
		var especialidade = Especialidade.CARDIOLOGIA;
		
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, 5l, data);
		
		when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);
		
		var response = mvc.perform(
				post("/consultas")
				.contentType(MediaType.APPLICATION_JSON) 
				.content(dadosAgendamentoConsultaJson.write(
							new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
						).getJson()) // Convertendo para um string Json
				).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); 
		
		var jsonEsperado = dadosDetalhamentoConsultaJson.write(
				dadosDetalhamento
		).getJson(); // json esperado
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // verificando se o conteudo é igual a o Json	
		}
}
