// src/main/java/com/pom/spring_ecommerce_livros/service/UsuarioService.java

package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.model.Usuario;
import com.pom.spring_ecommerce_livros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  public Usuario save(Usuario usuario) {return this.usuarioRepository.save(usuario);}

  public List<Usuario> findAll() {return this.usuarioRepository.findAll();}

  public Usuario findById(Long usuarioId) {
    Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(usuarioId);
    if (usuarioOptional.isPresent()) {
      return usuarioOptional.get();
    } else {
      // Tratar o caso em que o usuário não é encontrado
      throw new NoSuchElementException("Usuário não encontrado com ID: " + usuarioId);
    }
  }

  public Usuario authenticate(String email, String password) {
    Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
    if (optionalUsuario.isPresent()) {
      Usuario usuario = optionalUsuario.get();
      // Verifique a senha aqui (idealmente usando hash e salt para senhas)
      if (usuario.getSenha().equals(password)) {
        return usuario;
      }
    }
    return null;
  }
}
