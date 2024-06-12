import { Routes } from '@angular/router';
import {RegisterComponent} from "./user/register/register.component";
import {UserModule} from "./user/user.module";
import {AdminModule} from "./admin/admin.module";

export const routes: Routes = [
  { path: 'cadastro', component: RegisterComponent },
  {path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule)},
  {path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)},
  { path: '', redirectTo: '/cadastro', pathMatch: 'full' }, // Redireciona para 'home' por padrão
  { path: '**', component: RegisterComponent } // Página não encontrada
];
