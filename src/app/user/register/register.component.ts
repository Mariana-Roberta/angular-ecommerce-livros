import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import { ButtonModule } from "primeng/button";
import { CommonModule } from '@angular/common';
import {InputTextModule} from "primeng/inputtext";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FloatLabelModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ButtonModule,
    InputTextModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  user: any[] = [];
  items: any[] = [
    { label: 'Home', routerLink: '/' },
    { label: 'Perfil', routerLink: '/profile' },
    { label: 'Carrinho', routerLink: '/settings' }
  ];

  constructor(private fb: FormBuilder, private service: UserService) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  registerUser() {
    const formData = this.registerForm.value;

    this.service.registerUser(formData).subscribe(
      (response: any) => {
        console.log('User registered successfully!', response);
        this.user.push(response);
      },
      (error: any) => {
        console.error('Error registering user', error);
      }
    );
  }
}
