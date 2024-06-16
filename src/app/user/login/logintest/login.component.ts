import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        console.log('Login bem-sucedido!');
        // Supondo que a resposta contenha os detalhes do usuário
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['/home']);
      },
      error => {
        console.error('Erro de login', error);
      }
    );
  }
}
