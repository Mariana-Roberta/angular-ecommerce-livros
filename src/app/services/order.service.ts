import { Injectable } from '@angular/core';
import {catchError, map, Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = 'http://localhost:8080/usuario/pedidos';

  constructor(private http: HttpClient) {}

  finalizeOrder(carrinhoId: number
  ): Observable<any> {
    const requestBody = { carrinhoId: carrinhoId };
    return this.http.post<any>(`${this.apiUrl}/finalizar`, requestBody)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  confirmOrder(pedidoId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/confirmar/${pedidoId}`, {})
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    return throwError('Something went wrong; please try again later.');
  }
}
