import {Routes} from '@angular/router';
import {RegisterComponent} from "./user/register/register.component";
import {UserModule} from "./user/user.module";
import {AdminModule} from "./admin/admin.module";
import {LoginComponent} from "./login/login.component";
import {ProdutoService} from "./services/produto.service";
import {ProdutoComponent} from "./produto/produto.component";

export const routes: Routes = [
  {path: 'cadastro', component: RegisterComponent},
  {path: 'usuario', loadChildren: () => import('./user/user.module').then(m => m.UserModule)},
  {path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)},
  {path: 'login', component: LoginComponent},
  {path: 'produtos', component: ProdutoComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'}, // Redireciona para 'home' por padrão
  {path: '**', component: RegisterComponent} // Página não encontrada
];
