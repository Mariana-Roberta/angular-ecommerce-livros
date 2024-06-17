import { Component } from '@angular/core';
import {Button} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {BookService} from "../../services/book.service";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";

@Component({
  selector: 'app-book-register',
  standalone: true,
    imports: [
        Button,
        InputTextModule,
        ReactiveFormsModule,
        RouterLink,
        RouterLinkActive
    ],
  templateUrl: './book-register.component.html',
  styleUrl: './book-register.component.scss'
})
export class BookRegisterComponent {
  registerForm: FormGroup;
  book: any;

  constructor(private fb: FormBuilder, private bookService: BookService, private router: Router) {
    this.registerForm = this.fb.group({
      titulo: ['', Validators.required],
      autor: ['', Validators.required],
      sinopse: ['', Validators.required],
      valor: ['', Validators.required],
      quantidade: ['', Validators.required]
    });

    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras.state && navigation.extras.state['book']) {
      this.book = navigation.extras.state['book'];
    }
  }

  ngOnInit() {
    if (this.book) {
      this.registerForm.patchValue(this.book);
    }
  }

  registerBook() {
    if (this.registerForm.valid) {
      if (this.book) {
        this.bookService.updateBook(this.book.id, this.registerForm.value).subscribe(
          (response) => {
            console.log('Livro atualizado com sucesso!', response);
            this.router.navigate(['/admin/livro/lista']);
          },
          (error) => {
            console.error('Erro ao atualizar livro', error);
          }
        );
      } else {
        this.bookService.registerBook(this.registerForm.value).subscribe(
          (response) => {
            console.log('Livro registrado com sucesso!', response);
            this.router.navigate(['/admin/livro/lista']);
          },
          (error) => {
            console.error('Erro ao registrar livro', error);
          }
        );
      }
    } else {
      console.log('Formulário inválido. Por favor, corrija os campos destacados.');
    }
  }
}
