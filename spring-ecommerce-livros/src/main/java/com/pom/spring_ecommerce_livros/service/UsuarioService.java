package com.pom.spring_ecommerce_livros.service;

import com.pom.spring_ecommerce_livros.repository.UsuarioRepository;
import com.pom.spring_ecommerce_livros.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new org.springframework.security.core.userdetails.User(
            usuario.getNome(),
            usuario.getSenha(),
            usuario.getRoles().stream()
                .map(TipoUsuario -> new SimpleGrantedAuthority(TipoUsuario.getNome()))
                .collect(Collectors.toList())
        );
    }

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
