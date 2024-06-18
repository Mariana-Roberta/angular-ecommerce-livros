// src/app/login/login.component.ts

import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { LoginRequest } from '../models/login-request.model';
import { User } from '../models/user.model';
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {Button} from "primeng/button";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    FormsModule,
    Button
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  userAtual: User | null = null;

  loginRequest: LoginRequest = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router) { }

  onLogin(): void {
    this.authService.login(this.loginRequest).subscribe(
      (user: User) => {
        console.log('Login successful', user);
        this.userAtual = user;
        this.authService.setUser(user);
        const tipo = this.userAtual.tipoUsuario.toLowerCase();
        this.router.navigate([`/${tipo}/home`]);
        //${user.tipoUsuario.toLowerCase()}
      },
      error => {
        console.error('Login failed', error);
        // Exemplo de tratamento de erro
        if (error.status === 401) {
          alert('Credenciais inválidas. Por favor, verifique seu e-mail e senha.');
        } else {
          alert('Erro ao tentar fazer login. Por favor, tente novamente mais tarde.');
        }
      }
    );
  }

}
