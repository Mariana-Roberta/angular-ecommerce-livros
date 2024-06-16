package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.model.Usuario;
import com.pom.spring_ecommerce_livros.service.CarrinhoService;
import com.pom.spring_ecommerce_livros.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private CarrinhoService carrinhoService;

  @PostMapping("/cadastro")
  public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
    Usuario savedUsuario = usuarioService.save(usuario);
    return ResponseEntity.ok(savedUsuario);
  }

  @GetMapping("/usuarios")
  public ResponseEntity<List<Usuario>> getAllUsuario() {
    List<Usuario> usuarios = usuarioService.findAll();
    return ResponseEntity.ok(usuarios);
  }

  @GetMapping("/usuarios/{usuarioId}")
  public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long usuarioId) {
    Usuario usuario = usuarioService.findById(usuarioId);
    if (usuario != null) {
      return ResponseEntity.ok(usuario);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

}
