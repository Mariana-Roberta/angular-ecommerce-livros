package com.pom.spring_ecommerce_livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pom.spring_ecommerce_livros.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

}
