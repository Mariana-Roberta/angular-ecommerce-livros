import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "../login/login.component";
import {RegisterComponent} from "../user/register/register.component";
import {HomeComponent} from "../user/home/home.component";
import {CartComponent} from "../user/cart/cart.component";
import {OrderComponent} from "./order/order.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent },
  { path: 'cadastro', component: RegisterComponent },
  { path: 'carrinho', component: CartComponent },
  {path: 'pedidos', component: OrderComponent},
  {path: 'pedidos/:id', component: OrderComponent},
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
