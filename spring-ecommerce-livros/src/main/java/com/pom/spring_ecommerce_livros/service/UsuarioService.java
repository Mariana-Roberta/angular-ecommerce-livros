package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.repository.UsuarioRepository;
import com.pom.spring_ecommerce_livros.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    public Usuario findById(Long id) {
      return this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario save(Usuario usuario) {
        return userRepository.save(usuario);
    }

    public void delete(Long id){
      this.userRepository.deleteById(id);
    }
}
