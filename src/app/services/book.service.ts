import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiUrl = 'http://localhost:8080/livro';

  constructor(private http: HttpClient) {}

  getBooks(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/lista`)
      .pipe(
        catchError(this.handleError<any[]>('getBooks', []))
      );
  }

  registerBook(book: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/cadastro`, book, { headers })
      .pipe(
        catchError(this.handleError<any>('registerBook'))
      );
  }

  getBookById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }


  updateBook(id: number, book: any): Observable<any> {

    return this.http.put<any>(`${this.apiUrl}/editar/${id}`, book)
      .pipe(
        catchError(this.handleError<any>('updateBook'))
      );
  }

  deleteBook(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchBooks(term: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/search?term=${term}`)
      .pipe(
        catchError(this.handleError<any[]>('searchBooks', []))
      );
  }

  findBookByName(nome: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/findByName?nome=${nome}`)
      .pipe(
        catchError(this.handleError<any>('findBookByName'))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
