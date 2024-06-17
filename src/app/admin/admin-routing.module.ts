import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "../admin/register/register.component";
import {BookRegisterComponent} from "./book-register/book-register.component";
import {BookListComponent} from "./book-list/book-list.component";
import {EditBookComponent} from "./edit-book/edit-book.component";

const routes: Routes = [
  { path: 'cadastro', component: RegisterComponent },
  { path: 'livro/cadastro', component: BookRegisterComponent },
  { path: 'livro/lista', component: BookListComponent },
  { path: 'livro/editar', component: EditBookComponent},
  { path: 'home', redirectTo: 'livro/lista', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
