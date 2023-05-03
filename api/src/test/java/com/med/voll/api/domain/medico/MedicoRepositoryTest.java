package com.med.voll.api.domain.medico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest // é utilizado para testar uma interface Repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Utilizar o meu bd para test, não substituir ele.
@ActiveProfiles("test") // sinalizando que é application-test na pasta migration
class MedicoRepositoryTest {

	@Test
	void escolherMedicoAleatorioLivreNaData() {
		
	}

}
