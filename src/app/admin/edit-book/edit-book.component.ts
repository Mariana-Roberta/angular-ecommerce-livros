import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../services/book.service';
import {Button} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    Button,
    InputTextModule
  ],
  styleUrls: ['./edit-book.component.scss']
})
export class EditBookComponent implements OnInit {
  registerForm: FormGroup;
  book: any;

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router
  ) {
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

  ngOnInit(): void {
    if (this.book) {
      this.registerForm.patchValue(this.book);
    }
  }

  loadBookDetails(bookId: number): void {
    this.bookService.getBookById(bookId).subscribe(
      (book: any) => {
        this.registerForm.patchValue({
          titulo: book.titulo,
          autor: book.autor,
          sinopse: book.sinopse,
          valor: book.valor,
          quantidade: book.quantidade
        });
      },
      (error: any) => {
        console.error('Erro ao carregar os detalhes do livro', error);
      }
    );
  }

  updateBook(): void {
    if (this.registerForm.valid) {
      this.bookService.updateBook(this.book.id!, this.registerForm.value).subscribe(
        (response: any) => {
          console.log('Livro atualizado com sucesso !', response);
          this.router.navigate(['/admin/livro/lista']); // Navega para a lista de livros após a atualização
        },
        (error: any) => {
          console.error('Erro ao atualizar o livro', error);
        }
      );
    } else {
      console.log('Formulário inválido. Por favor, corrija os campos destacados.');
    }
  }
}
