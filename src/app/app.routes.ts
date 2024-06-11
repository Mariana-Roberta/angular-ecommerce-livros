import { Routes } from '@angular/router';
import {RegisterComponent} from "./user/register/register.component";

export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/register', pathMatch: 'full' }, // Redireciona para 'home' por padrão
  { path: '**', component: RegisterComponent } // Página não encontrada
];
