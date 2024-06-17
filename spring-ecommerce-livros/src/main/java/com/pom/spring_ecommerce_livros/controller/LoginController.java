package com.pom.spring_ecommerce_livros.controller;

import com.pom.spring_ecommerce_livros.model.Usuario;
import com.pom.spring_ecommerce_livros.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pom.spring_ecommerce_livros.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {
  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("")
  public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
    Usuario usuario = usuarioService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    if (usuario != null) {
      return ResponseEntity.ok(usuario);
    } else {
      return ResponseEntity.status(401).build(); // Retorna 401 Unauthorized se as credenciais não forem válidas
    }
  }
}
