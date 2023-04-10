package com.med.voll.api.domain.usuario;

//  a interface repository responsável pelo acesso na tabela de usuários

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// método que vai fazer a consulta do usuário no BD
	UserDetails findByLogin(String login);

}
