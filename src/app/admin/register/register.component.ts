import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { UserService } from '../../services/user.service';
import {FloatLabelModule} from "primeng/floatlabel";
import {Button, ButtonModule} from "primeng/button";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputTextModule} from "primeng/inputtext";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [
    FloatLabelModule,
    ButtonModule,
    ReactiveFormsModule,
    RadioButtonModule,
    InputTextModule
  ],
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  user: any[] = [];

  constructor(private fb: FormBuilder, private userService: UserService) {
    this.registerForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', Validators.required], // Adicionei validação básica de required
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', Validators.required], // Adicionei validação básica de required
      endereco: ['', Validators.required],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      tipoUsuario: ['ADMIN', Validators.required]
    });
  }

  registerUser() {
    if (this.registerForm.valid) {
      this.userService.registerUser(this.registerForm.value).subscribe(
        (response: any) => {
          console.log('Usuário registrado com sucesso!', response);
          this.user.push(response);
          this.registerForm.reset();
        },
        (error: any) => {
          console.error('Erro ao registrar usuário', error);
        }
      );
    } else {
      console.log('Formulário inválido. Por favor, corrija os campos destacados.');
    }
  }
}
